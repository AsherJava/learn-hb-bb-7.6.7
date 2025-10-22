/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.data.AbstractData
 *  com.jiuqi.np.definition.facade.IBaseMetaItem
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.common.util.DataEngineAdapter
 *  com.jiuqi.nr.common.util.DimensionChanger
 *  com.jiuqi.nr.definition.controller.IFormulaRunTimeController
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 *  com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider
 *  com.jiuqi.nvwa.dataengine.INvwaDataRow
 *  com.jiuqi.nvwa.dataengine.INvwaDataUpdator
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess
 *  com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet
 *  com.jiuqi.nvwa.dataengine.common.DataAccessContext
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn
 *  com.jiuqi.nvwa.dataengine.model.NvwaQueryModel
 *  com.jiuqi.nvwa.definition.common.NrdbHelper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 *  org.jetbrains.annotations.NotNull
 *  org.springframework.jdbc.core.JdbcTemplate
 *  org.springframework.jdbc.object.BatchSqlUpdate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.nr.data.logic.internal.service.impl;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.data.AbstractData;
import com.jiuqi.np.definition.facade.IBaseMetaItem;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.common.util.DataEngineAdapter;
import com.jiuqi.nr.common.util.DimensionChanger;
import com.jiuqi.nr.data.logic.exeception.LogicCheckedException;
import com.jiuqi.nr.data.logic.exeception.LogicRuntimeException;
import com.jiuqi.nr.data.logic.facade.param.input.CheckDesObj;
import com.jiuqi.nr.data.logic.internal.obj.EntityData;
import com.jiuqi.nr.data.logic.internal.service.IReviseCKDRECIDService;
import com.jiuqi.nr.data.logic.internal.util.CheckTableNameUtil;
import com.jiuqi.nr.data.logic.internal.util.DimensionUtil;
import com.jiuqi.nr.data.logic.internal.util.entity.EntityUtil;
import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nvwa.dataengine.INvwaDataAccessProvider;
import com.jiuqi.nvwa.dataengine.INvwaDataRow;
import com.jiuqi.nvwa.dataengine.INvwaDataUpdator;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataAccess;
import com.jiuqi.nvwa.dataengine.INvwaUpdatableDataSet;
import com.jiuqi.nvwa.dataengine.common.DataAccessContext;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryColumn;
import com.jiuqi.nvwa.dataengine.model.NvwaQueryModel;
import com.jiuqi.nvwa.definition.common.NrdbHelper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class ReviseCKDRECIDServiceImpl
implements IReviseCKDRECIDService {
    private static final Logger logger = LoggerFactory.getLogger(ReviseCKDRECIDServiceImpl.class);
    private static final String MSG_REVISE_ERROR_PREFIX = "\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u51fa\u9519\uff1a";
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IFormulaRunTimeController formulaRunTimeController;
    @Autowired
    private EntityUtil entityUtil;
    @Autowired
    private DataEngineAdapter dataEngineAdapter;
    @Autowired
    private INvwaDataAccessProvider dataAccessProvider;
    @Autowired
    private DataModelService dataModelService;
    @Autowired
    private NrdbHelper nrdbHelper;

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void revise(FormSchemeDefine formScheme) throws Exception {
        if (this.nrdbHelper.isEnableNrdb()) {
            this.reviseByNvwa(formScheme);
            return;
        }
        String tableName = CheckTableNameUtil.getCKDTableName(formScheme.getFormSchemeCode());
        List<EntityData> dimEntities = this.entityUtil.getDimEntities(formScheme);
        EntityData dwEntity = this.entityUtil.getEntity(formScheme.getDw());
        EntityData periodEntity = this.entityUtil.getPeriodEntity(formScheme.getDateTime());
        List<CKDFixObj> ckdFixObjs = this.getCkdFixObjs(tableName, dimEntities, dwEntity, periodEntity);
        HashMap<String, List<CKDFixObj>> mapByKey = new HashMap<String, List<CKDFixObj>>();
        HashMap<String, String> recidUpMap = new HashMap<String, String>();
        Set<String> formulaSchemeKeys = this.getFormulaSchemeKeys(formScheme);
        for (CKDFixObj ckdFixObj : ckdFixObjs) {
            if (!formulaSchemeKeys.contains(ckdFixObj.getFormulaSchemeKey())) continue;
            try {
                this.iDToCodeTransfer(ckdFixObj, mapByKey, recidUpMap);
            }
            catch (Exception exception) {
                logger.error(String.format("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u5347\u7ea7\u5f02\u5e38-recid-%s-\u539f\u56e0-%s", ckdFixObj.getDbRecordId(), exception.getMessage()), exception);
            }
        }
        try {
            List<String> deleteRowKeys = this.removeDuplicate(mapByKey);
            if (!CollectionUtils.isEmpty(deleteRowKeys)) {
                BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(Objects.requireNonNull(this.jdbcTemplate.getDataSource()), String.format("delete from %s where %s=?", tableName, "CKD_RECID"));
                batchSqlUpdate.setTypes(new int[]{12});
                for (String deleteRowKey : deleteRowKeys) {
                    recidUpMap.remove(deleteRowKey);
                    batchSqlUpdate.update(deleteRowKey);
                    logger.info("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u53bb\u91cd\u65f6\u5220\u9664\u7684recid-{}-{}", (Object)tableName, (Object)deleteRowKey);
                }
                batchSqlUpdate.flush();
                logger.info("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u53bb\u91cd\u5b8c\u6210{}", (Object)tableName);
            }
        }
        catch (Exception e) {
            throw new LogicCheckedException("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u4fee\u6b63\u65f6\u53bb\u91cd\u5f02\u5e38\uff1a" + tableName + ":" + e.getMessage(), e);
        }
        try {
            BatchSqlUpdate batchSqlUpdate = new BatchSqlUpdate(Objects.requireNonNull(this.jdbcTemplate.getDataSource()), String.format("update %s set %s=? where %s=?", tableName, "CKD_RECID", "CKD_RECID"));
            batchSqlUpdate.setTypes(new int[]{12, 12});
            for (Map.Entry entry : recidUpMap.entrySet()) {
                batchSqlUpdate.update((String)entry.getValue(), (String)entry.getKey());
            }
            batchSqlUpdate.flush();
            logger.info("\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u5b8c\u6210{}", (Object)tableName);
        }
        catch (Exception e) {
            throw new LogicCheckedException(MSG_REVISE_ERROR_PREFIX + tableName + "\u6570\u636e\u4fdd\u5b58\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    @Transactional(rollbackFor={Exception.class})
    public void reviseByNvwa(FormSchemeDefine formScheme) throws Exception {
        HashMap<String, Integer> columnIndexMap = new HashMap<String, Integer>();
        INvwaUpdatableDataSet checkResultDataSet = this.getCheckDesTable(formScheme, columnIndexMap);
        if (checkResultDataSet == null) {
            return;
        }
        String tableName = CheckTableNameUtil.getCKDTableName(formScheme.getFormSchemeCode());
        DimensionChanger dimensionChanger = this.dataEngineAdapter.getDimensionChanger(tableName);
        List rowKeyColumns = checkResultDataSet.getRowKeyColumns();
        HashMap<String, List<INvwaDataRow>> mapByKey = new HashMap<String, List<INvwaDataRow>>();
        Integer recidIndex = (Integer)columnIndexMap.get("CKD_RECID");
        Integer fmlSchemeKeyIndex = (Integer)columnIndexMap.get("CKD_FORMULASCHEMEKEY");
        Set<String> formulaSchemeKeys = this.getFormulaSchemeKeys(formScheme);
        if (!CollectionUtils.isEmpty(formulaSchemeKeys)) {
            for (int i = 0; i < checkResultDataSet.size(); ++i) {
                INvwaDataRow dataRow = checkResultDataSet.getRow(i);
                DimensionValueSet rowDim = this.getRowDim(dataRow, rowKeyColumns, dimensionChanger);
                String fmlSchemeKey = String.valueOf(dataRow.getValue(fmlSchemeKeyIndex.intValue()));
                if (!formulaSchemeKeys.contains(fmlSchemeKey)) continue;
                try {
                    this.iDToCodeTransfer(dataRow, rowDim, columnIndexMap, mapByKey);
                    continue;
                }
                catch (Exception e) {
                    logger.error(String.format("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u5347\u7ea7\u5f02\u5e38-recid-%s-\u539f\u56e0-%s", dataRow.getValue(recidIndex.intValue()), e.getMessage()), e);
                }
            }
        }
        try {
            List<String> deleteRowKeys;
            ColumnModelDefine recidCol = null;
            for (Object rowKeyColumn : rowKeyColumns) {
                if (!"CKD_RECID".equals(rowKeyColumn.getCode())) continue;
                recidCol = rowKeyColumn;
                break;
            }
            if (!CollectionUtils.isEmpty(deleteRowKeys = this.removeDuplicate(columnIndexMap, mapByKey, recidCol))) {
                for (String deleteRowKey : deleteRowKeys) {
                    logger.info("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u53bb\u91cd\u65f6\u5220\u9664\u7684recid-{}-{}", (Object)tableName, (Object)deleteRowKey);
                }
                NvwaQueryModel delQueryModel = new NvwaQueryModel();
                assert (recidCol != null) : "CKD_RECID\u5b57\u6bb5\u6a21\u578b\u4e3a\u7a7a";
                delQueryModel.getColumns().add(new NvwaQueryColumn(recidCol));
                delQueryModel.getColumnFilters().put(recidCol, deleteRowKeys);
                INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(delQueryModel);
                DataAccessContext delContext = new DataAccessContext(this.dataModelService);
                INvwaDataUpdator dataUpdator = updatableDataAccess.openForUpdate(delContext);
                dataUpdator.deleteAll();
                dataUpdator.commitChanges(delContext);
                logger.info("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u53bb\u91cd\u5b8c\u6210{}", (Object)tableName);
            }
        }
        catch (Exception e) {
            throw new LogicCheckedException("\u5ba1\u6838\u9519\u8bef\u8bf4\u660e\u4fee\u6b63\u65f6\u53bb\u91cd\u5f02\u5e38\uff1a" + tableName + ":" + e.getMessage(), e);
        }
        try {
            checkResultDataSet.commitChanges(new DataAccessContext(this.dataModelService));
            logger.info("\u5ba1\u6838\u9519\u8bef\u8bf4\u660eKey\u4fee\u6b63\u5b8c\u6210{}", (Object)tableName);
        }
        catch (Exception e) {
            throw new LogicCheckedException(MSG_REVISE_ERROR_PREFIX + tableName + "\u6570\u636e\u4fdd\u5b58\u51fa\u9519\uff1a" + e.getMessage(), e);
        }
    }

    private List<String> removeDuplicate(Map<String, Integer> columnIndexMap, Map<String, List<INvwaDataRow>> mapByKey, ColumnModelDefine recid) {
        ArrayList<String> result = new ArrayList<String>();
        for (List<INvwaDataRow> dataRows : mapByKey.values()) {
            int i;
            if (dataRows.size() <= 1) continue;
            long maxTime = 0L;
            int maxTimeIndex = 0;
            for (i = 0; i < dataRows.size(); ++i) {
                long updateTime = AbstractData.valueOf((Object)dataRows.get(i).getValue(columnIndexMap.get("CKD_UPDATETIME").intValue()), (int)2).getAsDateTime();
                if (updateTime <= maxTime) continue;
                maxTime = updateTime;
                maxTimeIndex = i;
            }
            for (i = 0; i < dataRows.size(); ++i) {
                if (i == maxTimeIndex) continue;
                result.add(AbstractData.valueOf((Object)dataRows.get(i).getKeyValue(recid), (int)6).getAsString());
            }
        }
        return result;
    }

    private INvwaUpdatableDataSet getCheckDesTable(FormSchemeDefine formScheme, Map<String, Integer> columnIndexMap) {
        String tableName = CheckTableNameUtil.getCKDTableName(formScheme.getFormSchemeCode());
        TableModelDefine tableModel = this.dataModelService.getTableModelDefineByName(tableName);
        if (tableModel == null) {
            logger.error(tableName + "\u4e0d\u5b58\u5728\uff01");
            return null;
        }
        String tableId = tableModel.getID();
        List columns = this.dataModelService.getColumnModelDefinesByTable(tableId);
        NvwaQueryModel queryModel = new NvwaQueryModel();
        for (int i = 0; i < columns.size(); ++i) {
            ColumnModelDefine columnModelDefine = (ColumnModelDefine)columns.get(i);
            columnIndexMap.put(columnModelDefine.getCode(), i);
            queryModel.getColumns().add(new NvwaQueryColumn(columnModelDefine));
        }
        DataAccessContext dataAccessContext = new DataAccessContext(this.dataModelService);
        INvwaUpdatableDataAccess updatableDataAccess = this.dataAccessProvider.createUpdatableDataAccess(queryModel);
        try {
            return updatableDataAccess.executeQueryForUpdate(dataAccessContext);
        }
        catch (Exception e) {
            logger.error(MSG_REVISE_ERROR_PREFIX + tableName + "\u6570\u636e\u67e5\u8be2\u51fa\u9519" + e.getMessage(), e);
            return null;
        }
    }

    private CheckDesObj iDToCodeTransfer(INvwaDataRow dataRow, DimensionValueSet rowDim, Map<String, Integer> columnIndexMap, Map<String, List<INvwaDataRow>> mapByKey) {
        List<Object> dataRows;
        CheckDesObj checkDes = new CheckDesObj();
        rowDim.clearValue("CKD_RECID");
        String dbDeskey = "";
        for (Map.Entry<String, Integer> e : columnIndexMap.entrySet()) {
            String value;
            String columnCode = e.getKey();
            Integer columnIndex = e.getValue();
            Object colValue = dataRow.getValue(columnIndex.intValue());
            if (!(colValue instanceof String) || StringUtils.isEmpty((String)(value = (String)colValue))) continue;
            switch (columnCode) {
                case "CKD_RECID": {
                    dbDeskey = value;
                    break;
                }
                case "CKD_FORMULASCHEMEKEY": {
                    checkDes.setFormulaSchemeKey(value);
                    break;
                }
                case "CKD_FORMKEY": {
                    checkDes.setFormKey(value);
                    break;
                }
                case "CKD_FORMULACODE": {
                    checkDes.setFormulaExpressionKey(value);
                    break;
                }
                case "CKD_GLOBROW": {
                    checkDes.setGlobRow(Integer.parseInt(value));
                    break;
                }
                case "CKD_GLOBCOL": {
                    checkDes.setGlobCol(Integer.parseInt(value));
                    break;
                }
                case "CKD_DIMSTR": {
                    ReviseCKDRECIDServiceImpl.setDimStr(rowDim, value, checkDes);
                    break;
                }
                case "CKD_DESCRIPTION": {
                    checkDes.getCheckDescription().setDescription(value);
                    break;
                }
            }
        }
        checkDes.setDimensionSet(DimensionUtil.getDimensionSet(rowDim));
        if (!dbDeskey.equals(checkDes.getRecordId())) {
            dataRow.setValue(columnIndexMap.get("CKD_RECID").intValue(), (Object)checkDes.getRecordId());
        }
        if (mapByKey.containsKey(checkDes.getRecordId())) {
            dataRows = mapByKey.get(checkDes.getRecordId());
            dataRows.add(dataRow);
        } else {
            dataRows = new ArrayList<INvwaDataRow>();
            dataRows.add(dataRow);
            mapByKey.put(checkDes.getRecordId(), dataRows);
        }
        return checkDes;
    }

    private static void setDimStr(DimensionValueSet rowDim, String value, CheckDesObj checkDes) {
        if (StringUtils.isNotEmpty((String)value)) {
            String[] dims;
            checkDes.setFloatId(value);
            for (String dim : dims = value.split(";")) {
                String[] dimValues = dim.split(":");
                if (dimValues.length != 2) continue;
                rowDim.setValue(dimValues[0], (Object)dimValues[1]);
            }
        }
    }

    @NotNull
    private List<CKDFixObj> getCkdFixObjs(String tableName, List<EntityData> dimEntities, EntityData dwEntity, EntityData periodEntity) {
        try {
            StringBuilder sql = new StringBuilder();
            sql.append("SELECT ").append("MDCODE").append(",").append("PERIOD");
            for (EntityData dimEntity : dimEntities) {
                sql.append(",").append(dimEntity.getDimensionName());
            }
            sql.append(",").append("CKD_RECID");
            sql.append(",").append("CKD_FORMULASCHEMEKEY");
            sql.append(",").append("CKD_FORMKEY");
            sql.append(",").append("CKD_FORMULACODE");
            sql.append(",").append("CKD_GLOBROW");
            sql.append(",").append("CKD_GLOBCOL");
            sql.append(",").append("CKD_DIMSTR");
            sql.append(",").append("CKD_UPDATETIME");
            sql.append(" FROM ").append(tableName);
            return this.jdbcTemplate.query(sql.toString(), (rs, rowNum) -> {
                CKDFixObj ckdFixObj = new CKDFixObj();
                ckdFixObj.setDbRecordId(rs.getString("CKD_RECID"));
                ckdFixObj.setFormulaSchemeKey(rs.getString("CKD_FORMULASCHEMEKEY"));
                ckdFixObj.setFormKey(rs.getString("CKD_FORMKEY"));
                ckdFixObj.setFormulaExpressionKey(rs.getString("CKD_FORMULACODE"));
                ckdFixObj.setGlobRow(Integer.parseInt(rs.getString("CKD_GLOBROW")));
                ckdFixObj.setGlobCol(Integer.parseInt(rs.getString("CKD_GLOBCOL")));
                ckdFixObj.setFloatId(rs.getString("CKD_DIMSTR"));
                ckdFixObj.setUpdateTime(rs.getTimestamp("CKD_UPDATETIME"));
                DimensionValue dw = new DimensionValue();
                dw.setName(dwEntity.getDimensionName());
                dw.setValue(rs.getString("MDCODE"));
                ckdFixObj.getDimensionSet().put(dwEntity.getDimensionName(), dw);
                DimensionValue period = new DimensionValue();
                period.setName(periodEntity.getDimensionName());
                period.setValue(rs.getString("PERIOD"));
                ckdFixObj.getDimensionSet().put(periodEntity.getDimensionName(), period);
                for (EntityData dimEntity : dimEntities) {
                    DimensionValue dim = new DimensionValue();
                    dim.setName(dimEntity.getDimensionName());
                    dim.setValue(rs.getString(dimEntity.getDimensionName()));
                    ckdFixObj.getDimensionSet().put(dimEntity.getDimensionName(), dim);
                }
                if (StringUtils.isNotEmpty((String)ckdFixObj.getFloatId())) {
                    String[] dims;
                    for (String dim : dims = ckdFixObj.getFloatId().split(";")) {
                        String[] dimValues = dim.split(":");
                        if (dimValues.length != 2) continue;
                        DimensionValue dimensionValue = new DimensionValue();
                        dimensionValue.setName(dimValues[0]);
                        dimensionValue.setValue(dimValues[1]);
                        ckdFixObj.getDimensionSet().put(dimValues[0], dimensionValue);
                    }
                }
                return ckdFixObj;
            });
        }
        catch (Exception e) {
            throw new LogicRuntimeException(MSG_REVISE_ERROR_PREFIX + tableName + "\u6570\u636e\u67e5\u8be2\u51fa\u9519" + e.getMessage(), e);
        }
    }

    private Set<String> getFormulaSchemeKeys(FormSchemeDefine formScheme) {
        List allFormulaSchemeDefinesByFormScheme = this.formulaRunTimeController.getAllFormulaSchemeDefinesByFormScheme(formScheme.getKey());
        Set<String> formulaSchemeKeys = CollectionUtils.isEmpty(allFormulaSchemeDefinesByFormScheme) ? Collections.emptySet() : allFormulaSchemeDefinesByFormScheme.stream().map(IBaseMetaItem::getKey).collect(Collectors.toSet());
        return formulaSchemeKeys;
    }

    private List<String> removeDuplicate(Map<String, List<CKDFixObj>> mapByKey) {
        ArrayList<String> result = new ArrayList<String>();
        for (List<CKDFixObj> ckdFixObjs : mapByKey.values()) {
            int i;
            if (ckdFixObjs.size() <= 1) continue;
            long maxTime = 0L;
            int maxTimeIndex = 0;
            for (i = 0; i < ckdFixObjs.size(); ++i) {
                long updateTime = AbstractData.valueOf((Object)ckdFixObjs.get(i).getUpdateTime(), (int)2).getAsDateTime();
                if (updateTime <= maxTime) continue;
                maxTime = updateTime;
                maxTimeIndex = i;
            }
            for (i = 0; i < ckdFixObjs.size(); ++i) {
                if (i == maxTimeIndex) continue;
                result.add(ckdFixObjs.get(i).getDbRecordId());
            }
        }
        return result;
    }

    private void iDToCodeTransfer(CKDFixObj ckdFixObj, Map<String, List<CKDFixObj>> mapByKey, Map<String, String> recidUpMap) {
        if (!ckdFixObj.getDbRecordId().equals(ckdFixObj.getRecordId())) {
            recidUpMap.put(ckdFixObj.getDbRecordId(), ckdFixObj.getRecordId());
        }
        if (mapByKey.containsKey(ckdFixObj.getRecordId())) {
            List<CKDFixObj> ckdFixObjs = mapByKey.get(ckdFixObj.getRecordId());
            ckdFixObjs.add(ckdFixObj);
        } else {
            ArrayList<CKDFixObj> ckdFixObjs = new ArrayList<CKDFixObj>();
            ckdFixObjs.add(ckdFixObj);
            mapByKey.put(ckdFixObj.getRecordId(), ckdFixObjs);
        }
    }

    private DimensionValueSet getRowDim(INvwaDataRow dataRow, List<ColumnModelDefine> rowKeyColumns, DimensionChanger dimensionChanger) {
        DimensionValueSet valueSet = new DimensionValueSet();
        for (ColumnModelDefine rowKeyColumn : rowKeyColumns) {
            String code = rowKeyColumn.getCode();
            String keyValue = (String)dataRow.getKeyValue(rowKeyColumn);
            if ("CKD_RECID".equals(code)) {
                valueSet.setValue("CKD_RECID", (Object)keyValue);
                continue;
            }
            if ("VERSIONID".equals(code)) {
                valueSet.setValue("VERSIONID", (Object)keyValue);
                continue;
            }
            String dimName = dimensionChanger.getDimensionName(code);
            valueSet.setValue(dimName, (Object)keyValue);
        }
        return valueSet;
    }

    private static class CKDFixObj
    extends CheckDesObj {
        private String dbRecordId;
        private Timestamp updateTime;

        private CKDFixObj() {
        }

        public String getDbRecordId() {
            return this.dbRecordId;
        }

        public void setDbRecordId(String dbRecordId) {
            this.dbRecordId = dbRecordId;
        }

        public Timestamp getUpdateTime() {
            return this.updateTime;
        }

        public void setUpdateTime(Timestamp updateTime) {
            this.updateTime = updateTime;
        }
    }
}

