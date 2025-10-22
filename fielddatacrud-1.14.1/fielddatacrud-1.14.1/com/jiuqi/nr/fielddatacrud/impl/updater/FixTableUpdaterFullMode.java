/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.dataengine.exception.IncorrectQueryException
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataQuery
 *  com.jiuqi.np.dataengine.intf.IDataRow
 *  com.jiuqi.np.dataengine.intf.IDataTable
 *  com.jiuqi.np.dataengine.var.Variable
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datacrud.IMetaData
 *  com.jiuqi.nr.datacrud.ParamRelation
 *  com.jiuqi.nr.datacrud.ReturnRes
 *  com.jiuqi.nr.datacrud.SaveResItem
 *  com.jiuqi.nr.datacrud.SaveRowData
 *  com.jiuqi.nr.datacrud.impl.out.CrudOperateException
 *  com.jiuqi.nr.datacrud.impl.out.CrudSaveException
 *  com.jiuqi.nr.datacrud.impl.out.ReturnResInstance
 *  com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO
 */
package com.jiuqi.nr.fielddatacrud.impl.updater;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataQuery;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.var.Variable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.ParamRelation;
import com.jiuqi.nr.datacrud.ReturnRes;
import com.jiuqi.nr.datacrud.SaveResItem;
import com.jiuqi.nr.datacrud.SaveRowData;
import com.jiuqi.nr.datacrud.impl.out.CrudOperateException;
import com.jiuqi.nr.datacrud.impl.out.CrudSaveException;
import com.jiuqi.nr.datacrud.impl.out.ReturnResInstance;
import com.jiuqi.nr.datascheme.internal.dto.DataFieldDTO;
import com.jiuqi.nr.fielddatacrud.FieldRelation;
import com.jiuqi.nr.fielddatacrud.FieldSaveInfo;
import com.jiuqi.nr.fielddatacrud.SaveRes;
import com.jiuqi.nr.fielddatacrud.TableUpdater;
import com.jiuqi.nr.fielddatacrud.impl.dto.DimField;
import com.jiuqi.nr.fielddatacrud.impl.dto.SaveResDTO;
import com.jiuqi.nr.fielddatacrud.impl.updater.BaseTableUpdater;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

public class FixTableUpdaterFullMode
extends BaseTableUpdater
implements TableUpdater {
    private static final Logger logger = LoggerFactory.getLogger(FixTableUpdaterFullMode.class);
    protected final Set<String> failDw = new LinkedHashSet<String>();
    protected final Set<String> processDw = new LinkedHashSet<String>();
    protected final Set<String> saveDw = new LinkedHashSet<String>();
    protected final Map<String, ReturnRes> failRes = new HashMap<String, ReturnRes>();
    protected Set<DimensionValueSet> currentBatch = new HashSet<DimensionValueSet>();
    protected final List<SaveRowData> currentRows = new ArrayList<SaveRowData>();
    protected IDataTable currentTable = null;
    protected int importCount = 0;

    public FixTableUpdaterFullMode(FieldSaveInfo saveInfo, FieldRelation fieldRelation) {
        super(saveInfo, fieldRelation);
    }

    public ReturnRes addRow(DimensionValueSet masterKeys, DimensionValueSet rowKeys) throws CrudOperateException {
        String dw = masterKeys.getValue(this.tableDimSet.getDwDimName()).toString();
        int batchSize = this.fieldDataProperties.getBatchSize() * 10;
        if (this.accessMasterKeys.remove(masterKeys)) {
            this.logAddRowWithPermission(masterKeys);
            this.currRow = new SaveRowData();
            this.currRow.setRowKeys(rowKeys);
            this.currRow.setLinkValues(new AbstractData[this.saveInfo.getFields().size()]);
            if (this.currentBatch.size() >= batchSize) {
                this.currentBatchCommit();
            }
            this.currentBatch.add(masterKeys);
            this.currentRows.add(this.currRow);
            this.processDw.add(dw);
            return ReturnRes.success();
        }
        Set<DimensionValueSet> noAccessMasterKeys = this.accessDTO.getNoAccessMasterKeys();
        if (noAccessMasterKeys.contains(masterKeys)) {
            this.logAddRowWithoutPermission(masterKeys);
            this.noPermissionDw.add(dw);
            return ReturnRes.build((int)1101);
        }
        this.logAddRowWithOutOfRange(masterKeys);
        return ReturnRes.build((int)1103);
    }

    @Override
    public ReturnRes addRow(List<Object> values) throws CrudOperateException {
        return this.addRow(values.toArray());
    }

    @Override
    public ReturnRes addRow(Object[] values) throws CrudOperateException {
        DimensionValueSet masterKeys = new DimensionValueSet();
        for (DimField dimField : this.dimFields) {
            int index = dimField.getIndex();
            if (values.length <= index) {
                throw new CrudOperateException(1202, "\u6570\u636e\u884c\u4e2d\u7f3a\u5c11\u7ef4\u5ea6\u6570\u636e");
            }
            Object value = values[index];
            if (dimField.getType() != DimField.P_DIM) continue;
            masterKeys.setValue(dimField.getDimName(), value);
        }
        ReturnRes returnRes = this.addRow(masterKeys, masterKeys);
        if (returnRes.getCode() != 0) {
            return returnRes;
        }
        for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
            ReturnRes res = this.setData(i, values[i]);
            if (res.getCode() == 0) continue;
            String dw = masterKeys.getValue(this.tableDimSet.getDwDimName()).toString();
            this.failDw.add(dw);
            this.failRes.put(dw, res);
            return res;
        }
        return ReturnResInstance.success();
    }

    protected void currentBatchCommit() throws CrudOperateException {
        this.initCurrentTable();
        this.setData();
        this.doCommit();
        this.importCount += this.currentRows.size();
        this.currentFileMark();
        this.saveDw.addAll(this.processDw);
        this.processDw.clear();
        this.currentBatch.clear();
        this.currentRows.clear();
    }

    protected void setData() throws CrudOperateException {
        for (SaveRowData row : this.currentRows) {
            DimensionValueSet key = row.getRowKeys();
            try {
                IDataRow finRow = this.currentTable.findRow(key);
                if (finRow == null) {
                    finRow = this.currentTable.appendRow(key);
                } else {
                    this.addFileMark(finRow);
                }
                for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
                    IMetaData metaData = this.saveInfo.getFields().get(i);
                    AbstractData value = null;
                    if (row.getLinkValues().length > i) {
                        value = row.getLinkValues()[i];
                    }
                    finRow.setValue(metaData.getIndex(), (Object)value);
                }
            }
            catch (IncorrectQueryException e) {
                this.failDw.addAll(this.processDw);
                logger.error("\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
                List<SaveResItem> saveResItems = this.exceptionErrors((Exception)((Object)e));
                if (!CollectionUtils.isEmpty(saveResItems)) {
                    String message = saveResItems.get(0).getMessage();
                    if (!StringUtils.hasLength(message)) {
                        message = "\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u6570\u636e\u53d1\u751f\u9519\u8bef" + e.getMessage();
                    }
                    for (String pdw : this.processDw) {
                        this.failRes.put(pdw, ReturnRes.build((int)1900, (String)message));
                    }
                }
                throw new CrudSaveException(saveResItems);
            }
            catch (Exception e) {
                this.failDw.addAll(this.processDw);
                String msg = "\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u6570\u636e\u53d1\u751f\u9519\u8bef\uff1a" + e.getMessage();
                for (String pdw : this.processDw) {
                    this.failRes.put(pdw, ReturnRes.build((int)1900, (String)msg));
                }
                logger.error("\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
                throw new CrudOperateException(4122, "\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u6570\u636e\u53d1\u751f\u9519\u8bef", (Throwable)e);
            }
        }
    }

    protected void doCommit() throws CrudSaveException {
        try {
            this.currentTable.commitChanges(true);
        }
        catch (Exception e) {
            this.failDw.addAll(this.processDw);
            for (String pdw : this.processDw) {
                this.failRes.put(pdw, ReturnRes.build((int)1900, (String)"\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u6570\u636e\u53d1\u751f\u9519\u8bef"));
            }
            this.processDw.clear();
            logger.error("\u56fa\u5b9a\u533a\u57df\u4fdd\u5b58\u6570\u636e\u53d1\u751f\u9519\u8bef", e);
            List<SaveResItem> saveResItems = this.exceptionErrors(e);
            throw new CrudSaveException(saveResItems);
        }
    }

    protected void initCurrentTable() throws CrudOperateException {
        try {
            IDataQuery dataQuery = this.dataEngineService.getDataQuery();
            List<IMetaData> fields = this.saveInfo.getFields();
            for (IMetaData field : fields) {
                int column = dataQuery.addColumn((FieldDefine)((DataFieldDTO)field.getDataField()));
                field.setIndex(column);
            }
            dataQuery.setMasterKeys(FixTableUpdaterFullMode.mergeDimensionValueSet(this.currentBatch));
            Iterator<Variable> varItr = null;
            if (this.saveInfo.getVariables() != null) {
                varItr = this.saveInfo.getVariables().iterator();
            }
            ExecutorContext context = this.executorContextFactory.getExecutorContext((ParamRelation)this.fieldRelation, (DimensionValueSet)null, varItr);
            this.currentTable = dataQuery.executeQuery(context);
        }
        catch (Exception e) {
            throw new CrudOperateException(4111, "\u66f4\u65b0\u6570\u636e\u5931\u8d25", (Throwable)e);
        }
    }

    @Override
    public void commit() throws CrudOperateException {
        if (!this.currentBatch.isEmpty()) {
            this.currentBatchCommit();
        }
        if (!this.accessMasterKeys.isEmpty()) {
            if (logger.isTraceEnabled()) {
                logger.trace("\u65e0\u6570\u636e\u5355\u4f4d\u8fdb\u884c\u6570\u636e\u5220\u9664\u64cd\u4f5c\uff1a{}", (Object)this.accessMasterKeys);
            }
            this.clearData();
            if (logger.isTraceEnabled()) {
                logger.trace("\u65e0\u6570\u636e\u5355\u4f4d\u8fdb\u884c\u6570\u636e\u5220\u9664\u64cd\u4f5c\u6210\u529f");
            }
        }
    }

    protected void clearData() throws CrudOperateException {
        int batchSize = this.fieldDataProperties.getBatchSize() * 10;
        for (DimensionValueSet accessMasterKey : this.accessMasterKeys) {
            String dw = accessMasterKey.getValue(this.tableDimSet.getDwDimName()).toString();
            this.processDw.add(dw);
            this.currentBatch.add(accessMasterKey);
            if (this.currentBatch.size() < batchSize) continue;
            this.updateNull();
        }
        if (!this.currentBatch.isEmpty()) {
            this.updateNull();
        }
    }

    protected void updateNull() throws CrudOperateException {
        this.initCurrentTable();
        for (DimensionValueSet batch : this.currentBatch) {
            IDataRow row = this.currentTable.findRow(batch);
            if (row == null) continue;
            this.addFileMark(row);
            for (int i = 0; i < this.saveInfo.getFields().size(); ++i) {
                IMetaData metaData = this.saveInfo.getFields().get(i);
                row.setValue(metaData.getIndex(), null);
            }
        }
        this.doCommit();
        this.currentFileMark();
        this.currentBatch.clear();
        this.saveDw.addAll(this.processDw);
        this.processDw.clear();
    }

    @Override
    public SaveRes getSaveRes() {
        SaveResDTO saveRes = new SaveResDTO();
        saveRes.setSaveDw(this.saveDw);
        saveRes.setFailDw(this.failDw);
        saveRes.setFailMessage(this.failRes);
        saveRes.setNoPermissionDw(this.noPermissionDw);
        saveRes.setCount(this.importCount);
        return saveRes;
    }

    public static DimensionValueSet mergeDimensionValueSet(Collection<DimensionValueSet> dimensions) {
        if (CollectionUtils.isEmpty(dimensions)) {
            return null;
        }
        DimensionValueSet dimensionSet = new DimensionValueSet();
        HashMap<String, Set> dimValues = new HashMap<String, Set>();
        for (DimensionValueSet dimensionValueSet : dimensions) {
            int size = dimensionValueSet.size();
            for (int i = 0; i < size; ++i) {
                String key = dimensionValueSet.getName(i);
                Object value = dimensionValueSet.getValue(i);
                Set values = dimValues.computeIfAbsent(key, k -> new LinkedHashSet());
                if (value instanceof Collection) {
                    values.addAll((Collection)value);
                    continue;
                }
                values.add(value);
            }
        }
        for (Map.Entry entry : dimValues.entrySet()) {
            Set value = (Set)entry.getValue();
            if (value.size() == 1) {
                Optional first = value.stream().findFirst();
                Object o = first.get();
                dimensionSet.setValue((String)entry.getKey(), o);
                continue;
            }
            dimensionSet.setValue((String)entry.getKey(), new ArrayList(value));
        }
        return dimensionSet;
    }
}

