/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.adhoc.ext.model.DSV
 *  com.jiuqi.bi.adhoc.model.AggregationType
 *  com.jiuqi.bi.adhoc.model.ApplyType
 *  com.jiuqi.bi.adhoc.model.FieldInfo
 *  com.jiuqi.bi.adhoc.model.FieldType
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.types.DataTypes
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO
 *  com.jiuqi.nr.entity.model.IEntityAttribute
 *  com.jiuqi.nr.entity.model.IEntityModel
 *  com.jiuqi.nr.period.common.utils.PeriodTableColumn
 *  com.jiuqi.nvwa.definition.common.AggrType
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.nvwa.definition.service.DataModelService
 */
package com.jiuqi.nr.bql.dsv.adapter;

import com.jiuqi.bi.adhoc.ext.model.DSV;
import com.jiuqi.bi.adhoc.model.AggregationType;
import com.jiuqi.bi.adhoc.model.ApplyType;
import com.jiuqi.bi.adhoc.model.FieldInfo;
import com.jiuqi.bi.adhoc.model.FieldType;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.types.DataTypes;
import com.jiuqi.nr.bql.dsv.adapter.DSVAdapter;
import com.jiuqi.nr.bql.dsv.adapter.DSVContext;
import com.jiuqi.nr.bql.extend.model.QueryColumnModel;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.internal.entity.DataTableMapDO;
import com.jiuqi.nr.entity.model.IEntityAttribute;
import com.jiuqi.nr.entity.model.IEntityModel;
import com.jiuqi.nr.period.common.utils.PeriodTableColumn;
import com.jiuqi.nvwa.definition.common.AggrType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.nvwa.definition.service.DataModelService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FieldInfoAdapter {
    private static final ILogger logger = DSVAdapter.getLogger();
    @Autowired
    private DataModelService dataModelService;
    private static final Set<String> UNVISIABLE_FIELDS = new HashSet<String>(Arrays.asList("VER", "STOPFLAG", "RECOVERYFLAG", "CREATEUSER", "CREATETIME", "PARENTS", "BIZKEYORDER", "FLOATORDER", "VALIDTIME", "INVALIDTIME"));

    public List<FieldInfo> getFieldInfosByTable(DSVContext context, DSV dsv, DataTable dataTable, DataTableMapDO tableMap) {
        ArrayList<FieldInfo> fieldInfos = new ArrayList<FieldInfo>();
        List<DataField> dataFields = context.getTableFields(dataTable.getKey());
        String[] bizkeys = dataTable.getBizKeys();
        HashSet<Object> bizKeySet = new HashSet<Object>();
        if (bizkeys != null) {
            for (String keyFieldId : bizkeys) {
                bizKeySet.add(keyFieldId);
                DataField dataField = context.getDataField(keyFieldId);
                try {
                    FieldInfo fieldInfo = this.getFieldInfoByDataField(context, dsv, dataField, dataTable, tableMap);
                    this.setDimFieldInfo(fieldInfo);
                    if (!dataField.getVisible().booleanValue()) {
                        fieldInfo.setVisible(false);
                    }
                    fieldInfos.add(fieldInfo);
                }
                catch (Exception e) {
                    logger.error("\u6570\u636e\u65b9\u6848\uff1a" + context.getDataScheme().getCode() + "    \u6307\u6807/\u5b57\u6bb5 \u3010 " + dataTable.getCode() + "." + dataField.getCode() + "\u3011 \u9002\u914d dsv\u6a21\u578b\u51fa\u9519\uff01" + e.getMessage());
                }
            }
        }
        for (DataField dataField : dataFields) {
            if (bizKeySet.contains(dataField.getKey())) continue;
            try {
                FieldInfo fieldInfo = this.getFieldInfoByDataField(context, dsv, dataField, dataTable, tableMap);
                if (fieldInfo == null) continue;
                if (fieldInfo.getName().equals("BIZKEYORDER")) {
                    this.setDimFieldInfo(fieldInfo);
                }
                if (!dataField.getVisible().booleanValue()) {
                    fieldInfo.setVisible(false);
                }
                fieldInfos.add(fieldInfo);
            }
            catch (Exception e) {
                logger.error("\u6570\u636e\u65b9\u6848\uff1a" + context.getDataScheme().getCode() + "    \u6307\u6807/\u5b57\u6bb5 \u3010 " + dataTable.getCode() + "." + dataField.getCode() + "\u3011 \u9002\u914d dsv\u6a21\u578b\u51fa\u9519\uff01" + e.getMessage());
            }
        }
        return fieldInfos;
    }

    protected void setDimFieldInfo(FieldInfo fieldInfo) {
        fieldInfo.setKeyField(fieldInfo.getName());
        fieldInfo.setNameField(fieldInfo.getName());
        fieldInfo.setFieldType(FieldType.GENERAL_DIM);
    }

    public List<FieldInfo> getDimFieldInfosByTableModel(DSVContext context, DSV dsv, String tableCode, TableModelDefine tableModel, IEntityModel entityModel, List<FieldInfo> fieldInfos, HashSet<String> nameSet) {
        List columnModels = this.dataModelService.getColumnModelDefinesByTable(tableModel.getID());
        IEntityAttribute keyField = entityModel.getBizKeyField();
        IEntityAttribute recordKeyField = null;
        if (tableModel.getDictType() == TableDictType.ZIPPER) {
            recordKeyField = entityModel.getRecordKeyField();
        }
        String keyFieldName = keyField.getCode();
        IEntityAttribute nameField = entityModel.getNameField();
        for (ColumnModelDefine columnModel : columnModels) {
            FieldInfo fieldInfo;
            if (nameSet != null) {
                if (nameSet.contains(columnModel.getCode())) continue;
                nameSet.add(columnModel.getCode());
            }
            if ((fieldInfo = this.getFieldInfoByColumnModel(context, dsv, columnModel, tableCode, true, context.isCustomPeriod())).getName().equals(keyField.getCode())) {
                fieldInfo.setName(keyFieldName);
                fieldInfo.setKey(true);
            } else if (recordKeyField != null && fieldInfo.getName().equals(recordKeyField.getCode())) {
                fieldInfo.setName("SID");
                fieldInfo.setVisible(false);
            } else if (entityModel.getOrderField() != null && fieldInfo.getName().equals(entityModel.getOrderField().getCode())) {
                fieldInfo.setName("H_ORDER");
                fieldInfo.setVisible(false);
            } else if (entityModel.getBeginDateField() != null && fieldInfo.getName().equals(entityModel.getBeginDateField().getCode())) {
                fieldInfo.setName("SYS_STARTDATE");
            } else if (entityModel.getEndDateField() != null & fieldInfo.getName().equals(entityModel.getEndDateField().getCode())) {
                fieldInfo.setName("SYS_ENDDATE");
            }
            if (fieldInfo.getFieldType() == FieldType.GENERAL_DIM) {
                if (keyFieldName.equals(fieldInfo.getName()) || nameField.getName().equals(fieldInfo.getName())) {
                    fieldInfo.setKeyField(keyFieldName);
                    fieldInfo.setNameField(nameField.getCode());
                } else if ("SHORTNAME".equals(fieldInfo.getName())) {
                    fieldInfo.setKeyField(keyFieldName);
                    fieldInfo.setNameField(fieldInfo.getName());
                } else {
                    fieldInfo.setKeyField(fieldInfo.getName());
                    fieldInfo.setNameField(fieldInfo.getName());
                }
            }
            fieldInfos.add(fieldInfo);
        }
        return fieldInfos;
    }

    public FieldInfo getFieldInfoByDataField(DSVContext context, DSV dsv, DataField dataField, DataTable dataTable, DataTableMapDO tableMap) throws Exception {
        DataFieldDeployInfo deployInfo = context.getDataFieldDeployInfo(dataField.getKey());
        if (deployInfo != null) {
            Object columnModel = null;
            columnModel = tableMap == null ? this.dataModelService.getColumnModelDefineByID(deployInfo.getColumnModelKey()) : new QueryColumnModel(dataField, deployInfo);
            if (columnModel != null) {
                FieldInfo fieldInfo = this.getFieldInfoByColumnModel(context, dsv, (ColumnModelDefine)columnModel, dataTable.getCode(), false, context.isCustomPeriod());
                fieldInfo.setTableName(dataTable.getCode());
                if (columnModel.getColumnType() == ColumnModelType.STRING && tableMap != null && tableMap.getSrcType().equals("nvwaDataSource")) {
                    fieldInfo.setFieldType(FieldType.GENERAL_DIM);
                }
                if (fieldInfo.getFieldType() == FieldType.GENERAL_DIM || fieldInfo.getFieldType() == FieldType.TIME_DIM) {
                    fieldInfo.setKeyField(fieldInfo.getName());
                    fieldInfo.setNameField(fieldInfo.getName());
                }
                fieldInfo.setDesensitization(dataField.getDataMaskCode());
                fieldInfo.getPropMap().put("nullable", dataField.getNullable());
                if (dataField.getDefaultValue() != null) {
                    fieldInfo.getPropMap().put("defaultValue", dataField.getDefaultValue());
                }
                if (dataField.getDesc() != null) {
                    fieldInfo.getPropMap().put("description", dataField.getDesc());
                }
                fieldInfo.getPropMap().put("isOnlyLeaf", dataField.isOnlyLeaf());
                if (dataField.getAllowUndefinedCode().booleanValue()) {
                    fieldInfo.getPropMap().put("allowUndefinedCode", true);
                }
                String fieldDimName = context.getColumnDimensionName(this.dataModelService, dataTable, (ColumnModelDefine)columnModel);
                context.getFieldDimNameMap().put(dataField.getKey(), fieldDimName);
                return fieldInfo;
            }
        } else {
            logger.error("\u6570\u636e\u65b9\u6848\uff1a" + context.getDataScheme().getCode() + "    \u6307\u6807/\u5b57\u6bb5 \u3010 " + dataTable.getCode() + "." + dataField.getCode() + "\u3011 \u9002\u914d dsv\u6a21\u578b\u51fa\u9519\uff01\u672a\u627e\u5230\u53d1\u5e03\u4fe1\u606f");
        }
        return null;
    }

    public FieldInfo getFieldInfoByColumnModel(DSVContext context, DSV dsv, ColumnModelDefine columnModel, String tableCode, boolean isDimTable, boolean isCustomPeriod) {
        FieldInfo fieldInfo = new FieldInfo();
        String fieldName = columnModel.getCode();
        String fieldTitle = columnModel.getTitle();
        if (fieldName.equals("DATATIME") && !isCustomPeriod) {
            fieldName = PeriodTableColumn.TIMEKEY.name();
            fieldTitle = "\u65f6\u671f";
        }
        fieldInfo.setName(fieldName);
        fieldInfo.setTitle(fieldTitle);
        String physicalName = columnModel.getCode();
        if (tableCode != null && !isDimTable) {
            physicalName = tableCode + "[" + columnModel.getCode() + "]";
        }
        fieldInfo.setPhysicalName(physicalName);
        fieldInfo.setDsvName(dsv.getName());
        if (isDimTable) {
            fieldInfo.setFieldType(this.transDimFieldType(columnModel.getColumnType(), fieldName));
        } else {
            fieldInfo.setFieldType(this.transFieldType(columnModel.getColumnType(), fieldName));
        }
        fieldInfo.setTableName(tableCode);
        int dataType = this.getFieldDataType(columnModel);
        fieldInfo.setDataType(dataType);
        ApplyType applyType = this.transApplyType(context, columnModel.getApplyType());
        fieldInfo.setApplyType(applyType);
        fieldInfo.setEncryption(columnModel.getSceneId());
        fieldInfo.setAggregationType(this.transAggregationType(fieldInfo.getFieldType(), columnModel.getAggrType(), fieldInfo.getDataType()));
        if (DataTypes.isNumber((int)fieldInfo.getDataType())) {
            fieldInfo.setShowPattern(this.getShowPattern(columnModel.getShowFormat(), columnModel.getDecimal()));
        }
        if (UNVISIABLE_FIELDS.contains(fieldInfo.getName())) {
            fieldInfo.setVisible(false);
        }
        fieldInfo.setPrecision(columnModel.getPrecision());
        fieldInfo.setScale(columnModel.getDecimal());
        return fieldInfo;
    }

    private int getFieldDataType(ColumnModelDefine columnModel) {
        int dataType = columnModel.getColumnType().getValue();
        if (dataType == ColumnModelType.CLOB.getValue() || dataType == ColumnModelType.ATTACHMENT.getValue()) {
            dataType = ColumnModelType.STRING.getValue();
        }
        return dataType;
    }

    public FieldInfo getDimFieldInfo(DSVContext context, DSV dsv, String fieldCode, String fieldTitle, String tableCode) {
        FieldInfo fieldInfo = new FieldInfo();
        fieldInfo.setName(fieldCode);
        fieldInfo.setTitle(fieldTitle);
        String physicalName = fieldCode;
        fieldInfo.setPhysicalName(physicalName);
        fieldInfo.setDsvName(dsv.getName());
        fieldInfo.setFieldType(FieldType.GENERAL_DIM);
        fieldInfo.setTableName(tableCode);
        fieldInfo.setDataType(6);
        fieldInfo.setApplyType(ApplyType.PERIOD_NUMBER);
        fieldInfo.setAggregationType(AggregationType.MIN);
        return fieldInfo;
    }

    private String getShowPattern(String showFormat, int fractionDigits) {
        StringBuilder pattern;
        if (showFormat == null) {
            showFormat = "@100";
        }
        switch (showFormat) {
            case "@100": {
                pattern = new StringBuilder("#,##0");
                break;
            }
            case "@101": {
                pattern = new StringBuilder("0");
                break;
            }
            case "@102": {
                pattern = new StringBuilder("\u00a50");
                break;
            }
            case "@103": {
                pattern = new StringBuilder("\u00a5#,##0");
                break;
            }
            case "@104": {
                pattern = new StringBuilder("$0");
                break;
            }
            case "@105": {
                pattern = new StringBuilder("$#,##0");
                break;
            }
            case "@199": {
                pattern = new StringBuilder("0");
                break;
            }
            default: {
                return showFormat;
            }
        }
        StringBuilder digits = new StringBuilder(".");
        for (int i = 0; i < fractionDigits; ++i) {
            digits.append("0");
        }
        if (digits.length() > 1) {
            pattern.append((CharSequence)digits);
        }
        if ("@199".equals(showFormat)) {
            pattern.append("%");
        }
        return pattern.toString();
    }

    private AggregationType transAggregationType(FieldType fieldType, AggrType aggrType, int dataType) {
        if (aggrType != null) {
            switch (aggrType) {
                case SUM: {
                    return AggregationType.SUM;
                }
                case COUNT: {
                    return AggregationType.COUNT;
                }
                case MIN: {
                    return AggregationType.MIN;
                }
                case MAX: {
                    return AggregationType.MAX;
                }
                case AVERAGE: {
                    return AggregationType.AVG;
                }
                case NONE: {
                    if (fieldType == FieldType.DESCRIPTION) {
                        return null;
                    }
                    if (!DataTypes.isNumber((int)dataType)) {
                        return AggregationType.MIN;
                    }
                    return AggregationType.SUM;
                }
            }
        }
        if (!DataTypes.isNumber((int)dataType)) {
            return AggregationType.MIN;
        }
        return AggregationType.SUM;
    }

    private FieldType transFieldType(ColumnModelType columnDataType, String columnName) {
        if (columnDataType == ColumnModelType.BIGDECIMAL || columnDataType == ColumnModelType.INTEGER || columnDataType == ColumnModelType.DOUBLE) {
            return FieldType.MEASURE;
        }
        return FieldType.DESCRIPTION;
    }

    private FieldType transDimFieldType(ColumnModelType columnDataType, String columnName) {
        if (columnDataType == ColumnModelType.BLOB) {
            return FieldType.DESCRIPTION;
        }
        return FieldType.GENERAL_DIM;
    }

    private ApplyType transApplyType(DSVContext context, com.jiuqi.nvwa.definition.common.ApplyType applyType) {
        return ApplyType.PERIOD_NUMBER;
    }
}

