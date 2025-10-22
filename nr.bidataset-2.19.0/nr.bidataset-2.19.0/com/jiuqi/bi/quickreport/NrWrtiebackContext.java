/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.Column
 *  com.jiuqi.bi.dataset.DataRow
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.bi.quickreport.writeback.IWritebackContext
 *  com.jiuqi.bi.quickreport.writeback.WritebackException
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache
 *  com.jiuqi.np.dataengine.definitions.TableModelRunInfo
 *  com.jiuqi.np.dataengine.executors.ExecutorContext
 *  com.jiuqi.np.dataengine.intf.IDataAssist
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nr.common.util.TimeDimUtils
 *  com.jiuqi.nr.datascheme.api.DataDimension
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldKind
 *  com.jiuqi.nr.datascheme.api.type.DimensionType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.bi.dataset.Column;
import com.jiuqi.bi.dataset.DataRow;
import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.NrWritebackParam;
import com.jiuqi.bi.quickreport.NrWrtiebackDataRegion;
import com.jiuqi.bi.quickreport.NrWrtiebackFieldInfo;
import com.jiuqi.bi.quickreport.writeback.IWritebackContext;
import com.jiuqi.bi.quickreport.writeback.WritebackException;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.definitions.DataModelDefinitionsCache;
import com.jiuqi.np.dataengine.definitions.TableModelRunInfo;
import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.intf.IDataAssist;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.common.util.TimeDimUtils;
import com.jiuqi.nr.datascheme.api.DataDimension;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldKind;
import com.jiuqi.nr.datascheme.api.type.DimensionType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NrWrtiebackContext {
    private IWritebackContext context;
    private MemoryDataSet<?> dataSet;
    private DataTable table;
    private boolean isFloat = false;
    private List<NrWrtiebackFieldInfo> rowKeyFieldInfos = new ArrayList<NrWrtiebackFieldInfo>();
    private List<NrWrtiebackFieldInfo> dataFieldInfos = new ArrayList<NrWrtiebackFieldInfo>();
    private Map<DimensionValueSet, NrWrtiebackDataRegion> regions = new HashMap<DimensionValueSet, NrWrtiebackDataRegion>();
    private ExecutorContext executorContext = null;
    private TableModelRunInfo tableModelRunInfo = null;
    private PeriodType periodType;

    public NrWrtiebackContext(IWritebackContext context, MemoryDataSet<?> dataSet, DataTable table) {
        this.context = context;
        this.dataSet = dataSet;
        this.table = table;
    }

    public void doInit(NrWritebackParam param) throws Exception {
        Column column;
        this.executorContext = new ExecutorContext(param.dataDefinitionRuntimeController);
        DataModelDefinitionsCache dataModelDefinitionsCache = this.executorContext.getCache().getDataModelDefinitionsCache();
        this.tableModelRunInfo = this.getTableModelRunInfoByDataTable(param, dataModelDefinitionsCache);
        if (this.tableModelRunInfo.getBizOrderField() != null) {
            this.isFloat = true;
        }
        List schemeKeyFields = param.dataSchemeService.getDataFieldByTableCodeAndKind(this.table.getCode(), new DataFieldKind[]{DataFieldKind.PUBLIC_FIELD_DIM});
        List periodDimensions = param.dataSchemeService.getDataSchemeDimension(this.table.getDataSchemeKey(), DimensionType.PERIOD);
        if (periodDimensions != null && periodDimensions.size() > 0) {
            this.periodType = ((DataDimension)periodDimensions.get(0)).getPeriodType();
        }
        IDataAssist dataAssist = param.dataAccessProvider.newDataAssist(this.executorContext);
        ArrayList<NrWrtiebackFieldInfo> masterKeyFieldInfos = new ArrayList<NrWrtiebackFieldInfo>(schemeKeyFields.size());
        for (DataField schemeKeyField : schemeKeyFields) {
            column = this.dataSet.getMetadata().find(schemeKeyField.getCode());
            if (column == null) {
                throw new WritebackException(this.table.getCode() + "\u8868\u56de\u5199\u65f6\u7f3a\u5931\u4e86\u7ef4\u5ea6\u5b57\u6bb5" + schemeKeyField.getCode());
            }
            FieldDefine fieldDefine = param.dataDefinitionRuntimeController.queryFieldDefine(schemeKeyField.getKey());
            NrWrtiebackFieldInfo fieldInfo = new NrWrtiebackFieldInfo(fieldDefine);
            fieldInfo.setColumnIndex(column.getIndex());
            fieldInfo.setDimensionName(dataAssist.getDimensionName(fieldDefine));
            masterKeyFieldInfos.add(fieldInfo);
        }
        for (Column column2 : this.dataSet.getMetadata()) {
            FieldDefine fieldDefine = param.dataDefinitionRuntimeController.queryFieldByCodeInTable(column2.getName(), this.table.getKey());
            NrWrtiebackFieldInfo fieldInfo = new NrWrtiebackFieldInfo(fieldDefine);
            fieldInfo.setColumnIndex(column2.getIndex());
            if (this.tableModelRunInfo.isKeyField(fieldDefine.getCode())) {
                fieldInfo.setDimensionName(dataAssist.getDimensionName(fieldDefine));
                this.rowKeyFieldInfos.add(fieldInfo);
                continue;
            }
            this.dataFieldInfos.add(fieldInfo);
        }
        if (this.isFloat) {
            FieldDefine fieldDefine = param.dataDefinitionRuntimeController.queryFieldByCodeInTable("FLOATORDER", this.table.getKey());
            NrWrtiebackFieldInfo fieldInfo = new NrWrtiebackFieldInfo(fieldDefine);
            this.dataFieldInfos.add(fieldInfo);
            if (this.tableModelRunInfo.getDimensions().contains("RECORDKEY")) {
                fieldDefine = param.dataDefinitionRuntimeController.queryFieldByCodeInTable("BIZKEYORDER", this.table.getKey());
                fieldInfo = new NrWrtiebackFieldInfo(fieldDefine);
                fieldInfo.setDimensionName("RECORDKEY");
                this.rowKeyFieldInfos.add(fieldInfo);
            }
        }
        for (ColumnModelDefine keyColumn : this.tableModelRunInfo.getDimFields()) {
            if (keyColumn.getCode().equals("BIZKEYORDER") || (column = this.dataSet.getMetadata().find(keyColumn.getCode())) != null) continue;
            throw new WritebackException(this.table.getCode() + "\u8868\u56de\u5199\u65f6\u7f3a\u5931\u4e86\u7ef4\u5ea6\u5b57\u6bb5" + keyColumn.getCode());
        }
        int rowIndex = 0;
        for (DataRow row : this.dataSet) {
            DimensionValueSet masterKeys = this.createMasterKeys(rowIndex + 1, row, masterKeyFieldInfos);
            NrWrtiebackDataRegion region = this.regions.get(masterKeys);
            if (region == null) {
                region = new NrWrtiebackDataRegion(masterKeys);
                this.regions.put(masterKeys, region);
            }
            region.getResultRowIndexes().add(rowIndex);
            ++rowIndex;
        }
    }

    private TableModelRunInfo getTableModelRunInfoByDataTable(NrWritebackParam param, DataModelDefinitionsCache dataDefinitionsCache) {
        List deployInfos;
        TableModelRunInfo tableRunInfo = dataDefinitionsCache.getTableInfoByCode(this.table.getCode());
        if (tableRunInfo == null && (deployInfos = param.dataSchemeService.getDeployInfoByDataTableKey(this.table.getKey())).size() > 0) {
            tableRunInfo = dataDefinitionsCache.getTableInfoByCode(((DataFieldDeployInfo)deployInfos.get(0)).getTableName());
        }
        return tableRunInfo;
    }

    private DimensionValueSet createMasterKeys(int rowIndex, DataRow row, List<NrWrtiebackFieldInfo> masterKeyFieldInfos) throws WritebackException {
        DimensionValueSet rowKeys = new DimensionValueSet();
        for (NrWrtiebackFieldInfo keyFieldInfo : masterKeyFieldInfos) {
            Object keyValue = row.getValue(keyFieldInfo.getColumnIndex());
            if (keyFieldInfo.isDataTime()) {
                keyValue = TimeDimUtils.timeKeyToPeriod((String)((String)keyValue), (PeriodType)this.periodType);
            }
            if (keyValue == null) {
                throw new WritebackException("\u7b2c" + rowIndex + "\u884c" + (keyFieldInfo.getColumnIndex() + 1) + "\u5217\uff0c\u4e3b\u952e\u5b57\u6bb5\u503c\u4e3a\u7a7a");
            }
            rowKeys.setValue(keyFieldInfo.getDimensionName(), keyValue);
        }
        return rowKeys;
    }

    public DataTable getTable() {
        return this.table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public boolean isFloat() {
        return this.isFloat;
    }

    public Map<DimensionValueSet, NrWrtiebackDataRegion> getRegions() {
        return this.regions;
    }

    public ExecutorContext getExecutorContext() {
        return this.executorContext;
    }

    public void setFloat(boolean isFloat) {
        this.isFloat = isFloat;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public List<NrWrtiebackFieldInfo> getRowKeyFieldInfos() {
        return this.rowKeyFieldInfos;
    }

    public List<NrWrtiebackFieldInfo> getDataFieldInfos() {
        return this.dataFieldInfos;
    }

    public TableModelRunInfo getTableModelRunInfo() {
        return this.tableModelRunInfo;
    }

    public void setTableModelRunInfo(TableModelRunInfo tableModelRunInfo) {
        this.tableModelRunInfo = tableModelRunInfo;
    }
}

