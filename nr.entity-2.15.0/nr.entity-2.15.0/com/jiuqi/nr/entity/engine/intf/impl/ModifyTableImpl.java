/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.entity.engine.intf.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.entity.adapter.IEntityAdapter;
import com.jiuqi.nr.entity.engine.common.RowState;
import com.jiuqi.nr.entity.engine.exception.EntityCheckException;
import com.jiuqi.nr.entity.engine.exception.EntityUpdateException;
import com.jiuqi.nr.entity.engine.executors.QueryContext;
import com.jiuqi.nr.entity.engine.executors.QueryInfo;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.entity.engine.intf.IModifyRow;
import com.jiuqi.nr.entity.engine.intf.IModifyTable;
import com.jiuqi.nr.entity.engine.intf.impl.DataLoader;
import com.jiuqi.nr.entity.engine.intf.impl.EntityRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.ModifyRowImpl;
import com.jiuqi.nr.entity.engine.intf.impl.QueryEntityTableImpl;
import com.jiuqi.nr.entity.engine.result.EntityCheckResult;
import com.jiuqi.nr.entity.engine.result.EntityDataRow;
import com.jiuqi.nr.entity.engine.result.EntityUpdateResult;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.entity.param.impl.EntityDeleteParam;
import com.jiuqi.nr.entity.param.impl.EntityQueryParam;
import com.jiuqi.nr.entity.param.impl.EntityUpdateParam;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

public class ModifyTableImpl
extends QueryEntityTableImpl
implements IModifyTable {
    private final List<ModifyRowImpl> modifyRows = new ArrayList<ModifyRowImpl>();
    private boolean needCheck;
    private boolean needCommit;
    private boolean batchUpdateModel;
    private boolean ignoreCheckErrorData;
    private final DataLoader dataLoader;

    public ModifyTableImpl(QueryContext queryContext, QueryInfo queryInfo, DataLoader dataLoader) {
        super(queryContext, queryInfo, dataLoader);
        this.dataLoader = dataLoader;
    }

    @Override
    public IModifyRow appendNewRow() {
        this.queryContext.getLogger().trace("appendNewRow");
        ModifyRowImpl entityRow = this.addModifyRow(this.masterKeys);
        entityRow.setRowState(RowState.ADD);
        return entityRow;
    }

    @Override
    public IModifyRow appendModifyRow(DimensionValueSet masterKeys) {
        if (masterKeys == null) {
            masterKeys = this.masterKeys;
        }
        this.queryContext.getLogger().trace("appendModifyRow, masterKeys: {}", masterKeys);
        ModifyRowImpl entityRow = this.addModifyRow(masterKeys);
        entityRow.setRowState(RowState.MODIFIED);
        return entityRow;
    }

    @Override
    public void deleteAll() {
        if (this.masterKeys == null) {
            return;
        }
        this.queryContext.getLogger().trace("deleteAll, masterKeys: {}", this.masterKeys);
        EntityQueryParam entityQueryParam = this.queryInfo.getQueryParam();
        List<String> masterKey = entityQueryParam.getMasterKey();
        if (CollectionUtils.isEmpty(masterKey)) {
            return;
        }
        Map<String, IEntityRow> findRows = this.quickFindByEntityKeys(new HashSet<String>(masterKey));
        if (findRows == null) {
            return;
        }
        Collection<IEntityRow> allRows = findRows.values();
        this.queryContext.getLogger().trace("allRowsSize: {}", allRows.size());
        IEntityModel entityModel = this.queryInfo.getTableRunInfo().getEntityModel();
        IEntityAttribute recordKeyField = entityModel.getRecordKeyField();
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        IEntityAttribute codeField = entityModel.getCodeField();
        String recordKey = recordKeyField.getCode();
        for (IEntityRow entityRow : allRows) {
            ModifyRowImpl modifyRow = this.addModifyRow(this.masterKeys);
            modifyRow.setRowState(RowState.DELETE);
            modifyRow.setValue(recordKey, entityRow.getValue(recordKey).getAsString());
            modifyRow.setValue(bizKeyField.getCode(), entityRow.getEntityKeyData());
            modifyRow.setValue(codeField.getCode(), entityRow.getCode());
            modifyRow.buildRow();
        }
    }

    @Override
    public EntityCheckResult checkData() {
        EntityCheckResult checkResult;
        this.needCheck = true;
        this.needCommit = false;
        try {
            checkResult = this.executeCommit().getCheckResult();
        }
        catch (EntityUpdateException e) {
            throw new EntityCheckException(e);
        }
        return checkResult;
    }

    public ModifyRowImpl addModifyRow(DimensionValueSet rowKeys) {
        HashMap<String, Object> rowDatas = new HashMap<String, Object>();
        ModifyRowImpl modifyRow = new ModifyRowImpl(this, rowKeys, rowDatas);
        this.modifyRows.add(modifyRow);
        return modifyRow;
    }

    @Override
    public EntityUpdateResult commitChange() throws EntityUpdateException {
        this.needCheck = true;
        this.needCommit = true;
        this.queryContext.getLogger().trace("commitChange");
        return this.executeCommit();
    }

    @Override
    public EntityUpdateResult commitChangeWithOutCheck() throws EntityUpdateException {
        this.needCheck = false;
        this.needCommit = true;
        this.queryContext.getLogger().trace("commitChangeWithOutCheck");
        return this.executeCommit();
    }

    public EntityUpdateResult executeCommit() throws EntityUpdateException {
        List<ModifyRowImpl> deleteRows;
        List<ModifyRowImpl> modifyRows;
        EntityUpdateResult commitResult = new EntityUpdateResult();
        Map<RowState, List<ModifyRowImpl>> stateMap = this.modifyRows.stream().collect(Collectors.groupingBy(EntityRowImpl::getRowState));
        List<ModifyRowImpl> addRows = stateMap.get((Object)RowState.ADD);
        if (!CollectionUtils.isEmpty(addRows)) {
            commitResult.addUpdateResult(this.executeAddRows(addRows));
        }
        if (!CollectionUtils.isEmpty(modifyRows = stateMap.get((Object)RowState.MODIFIED))) {
            commitResult.addUpdateResult(this.executeUpdateRows(modifyRows));
        }
        if (!CollectionUtils.isEmpty(deleteRows = stateMap.get((Object)RowState.DELETE))) {
            commitResult.addUpdateResult(this.executeDeleteRows(deleteRows));
        }
        return commitResult;
    }

    private EntityUpdateResult executeDeleteRows(List<ModifyRowImpl> deleteRows) throws EntityUpdateException {
        EntityDeleteParam deleteParam = this.buildDeleteParam(deleteRows);
        IEntityAdapter entityAdapter = this.dataLoader.getEntityAdapter();
        return entityAdapter.deleteRows(deleteParam);
    }

    private EntityDeleteParam buildDeleteParam(List<ModifyRowImpl> deleteRows) {
        EntityQueryParam entityQueryParam = this.queryInfo.getQueryParam();
        EntityDeleteParam deleteParam = new EntityDeleteParam();
        BeanUtils.copyProperties(entityQueryParam, deleteParam);
        ArrayList<EntityDataRow> deleteList = new ArrayList<EntityDataRow>(deleteRows.size());
        IEntityModel entityModel = this.queryInfo.getTableRunInfo().getEntityModel();
        IEntityAttribute recordKeyField = entityModel.getRecordKeyField();
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        IEntityAttribute codeField = entityModel.getCodeField();
        deleteRows.forEach(e -> {
            EntityDataRow dataRow = new EntityDataRow();
            Object recordValue = e.getModifyValue(recordKeyField.getCode());
            Object bizValue = e.getModifyValue(bizKeyField.getCode());
            Object codeValue = e.getModifyValue(codeField.getCode());
            if (recordValue != null) {
                dataRow.setRecKey(recordValue.toString());
            }
            if (bizValue != null) {
                dataRow.setEntityKeyData(bizValue.toString());
            }
            if (codeValue != null) {
                dataRow.putRowData(codeField.getCode(), codeValue);
            }
            deleteList.add(dataRow);
        });
        deleteParam.setDeleteRows(deleteList);
        deleteParam.setVersionDate(entityQueryParam.getVersionDate());
        return deleteParam;
    }

    private EntityUpdateResult executeUpdateRows(List<ModifyRowImpl> modifyRows) throws EntityUpdateException {
        EntityCheckResult checkResult;
        EntityUpdateResult updateResult = new EntityUpdateResult();
        EntityUpdateParam updateParam = this.buildUpdateParam(modifyRows);
        IEntityAdapter entityAdapter = this.dataLoader.getEntityAdapter();
        if (this.needCheck && (checkResult = entityAdapter.rowsCheck(updateParam, false)) != null && !CollectionUtils.isEmpty(checkResult.getFailInfos())) {
            updateResult.setCheckResult(checkResult);
            if (this.ignoreCheckErrorData) {
                this.resetUpdateRows(updateParam);
            } else {
                return updateResult;
            }
        }
        if (this.needCommit) {
            EntityUpdateResult modifyResult = entityAdapter.updateRows(updateParam);
            updateResult.addUpdateResult(modifyResult);
        }
        return updateResult;
    }

    private EntityUpdateResult executeAddRows(List<ModifyRowImpl> addRows) throws EntityUpdateException {
        EntityCheckResult checkResult;
        EntityUpdateResult addResult = new EntityUpdateResult();
        EntityUpdateParam updateParam = this.buildUpdateParam(addRows);
        IEntityAdapter entityAdapter = this.dataLoader.getEntityAdapter();
        if (this.needCheck && (checkResult = entityAdapter.rowsCheck(updateParam, true)) != null && !CollectionUtils.isEmpty(checkResult.getFailInfos())) {
            addResult.setCheckResult(checkResult);
            if (this.ignoreCheckErrorData) {
                this.resetUpdateRows(updateParam);
            } else {
                return addResult;
            }
        }
        if (this.needCommit) {
            EntityUpdateResult insertResult = entityAdapter.insertRows(updateParam);
            addResult.addUpdateResult(insertResult);
        }
        return addResult;
    }

    private void resetUpdateRows(EntityUpdateParam updateParam) {
        List<EntityDataRow> modifyRows = updateParam.getModifyRows();
        List<EntityDataRow> saveRows = modifyRows.stream().filter(e -> !e.isCheckFailed()).collect(Collectors.toList());
        updateParam.setModifyRows(saveRows);
    }

    private EntityUpdateParam buildUpdateParam(List<ModifyRowImpl> addRows) {
        EntityQueryParam entityQueryParam = this.queryInfo.getQueryParam();
        EntityUpdateParam updateParam = new EntityUpdateParam();
        BeanUtils.copyProperties(entityQueryParam, updateParam);
        List<EntityDataRow> entityAddRows = this.buildQueryRowData(addRows);
        updateParam.setModifyRows(entityAddRows);
        updateParam.setVersionDate(entityQueryParam.getVersionDate());
        updateParam.setBatchUpdateModel(this.isBatchUpdateModel());
        return updateParam;
    }

    private List<EntityDataRow> buildQueryRowData(List<ModifyRowImpl> modifyRows) {
        ArrayList<EntityDataRow> updateRows = new ArrayList<EntityDataRow>();
        IEntityModel entityModel = this.queryInfo.getTableRunInfo().getEntityModel();
        IEntityAttribute bizKeyField = entityModel.getBizKeyField();
        IEntityAttribute recordKeyField = entityModel.getRecordKeyField();
        IEntityAttribute nameField = entityModel.getNameField();
        IEntityAttribute parentField = entityModel.getParentField();
        String dimensionName = this.queryInfo.getTableRunInfo().getDimensionName();
        for (ModifyRowImpl modifyRow : modifyRows) {
            Object parentValue;
            Object titleValue;
            EntityDataRow dataRow = new EntityDataRow();
            Map<String, Object> modifyData = modifyRow.getModifiedData();
            Object bizValue = modifyData.get(bizKeyField.getCode().toLowerCase(Locale.ROOT));
            if (bizValue != null) {
                dataRow.setEntityKeyData(String.valueOf(bizValue));
            } else {
                DimensionValueSet rowKeys = modifyRow.getRowKeys();
                Object masterKeysValue = rowKeys.getValue(dimensionName);
                if (masterKeysValue != null) {
                    dataRow.setEntityKeyData(String.valueOf(masterKeysValue));
                }
            }
            Object recValue = modifyData.get(recordKeyField.getCode().toLowerCase(Locale.ROOT));
            if (recValue != null) {
                dataRow.setRecKey(String.valueOf(recValue));
            }
            if ((titleValue = modifyData.get(nameField.getCode().toLowerCase(Locale.ROOT))) != null) {
                dataRow.setTitle(String.valueOf(titleValue));
            }
            if ((parentValue = modifyData.get(parentField.getCode().toLowerCase(Locale.ROOT))) != null) {
                dataRow.setParentId(String.valueOf(parentValue));
            }
            dataRow.setRowData(modifyData);
            dataRow.setTempId(modifyRow.getTempId());
            dataRow.setNeedSync(modifyRow.isNeedSync());
            dataRow.setIgnoreCodeCheck(modifyRow.isIgnoreCodeCheck());
            updateRows.add(dataRow);
        }
        return updateRows;
    }

    public boolean isBatchUpdateModel() {
        return this.batchUpdateModel;
    }

    public void setBatchUpdateModel(boolean batchUpdateModel) {
        this.batchUpdateModel = batchUpdateModel;
    }

    public boolean isIgnoreCheckErrorData() {
        return this.ignoreCheckErrorData;
    }

    public void setIgnoreCheckErrorData(boolean ignoreCheckErrorData) {
        this.ignoreCheckErrorData = ignoreCheckErrorData;
    }
}

