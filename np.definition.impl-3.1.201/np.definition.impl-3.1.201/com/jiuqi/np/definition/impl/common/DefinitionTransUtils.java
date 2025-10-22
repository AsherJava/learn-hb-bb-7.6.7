/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.common.TableGatherType
 *  com.jiuqi.np.definition.common.TableKind
 *  com.jiuqi.np.definition.facade.DesignFieldDefine
 *  com.jiuqi.np.definition.facade.DesignTableDefine
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.facade.TableDefine
 *  com.jiuqi.np.definition.internal.impl.FormatProperties
 *  com.jiuqi.nr.datascheme.api.DataField
 *  com.jiuqi.nr.datascheme.api.DataTable
 *  com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 *  com.jiuqi.nr.datascheme.api.type.DataTableGatherType
 *  com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl
 *  com.jiuqi.nr.definition.internal.impl.DesignTableDefineImpl
 *  com.jiuqi.nvwa.definition.common.ApplyType
 *  com.jiuqi.nvwa.definition.common.ColumnModelType
 *  com.jiuqi.nvwa.definition.common.TableDictType
 *  com.jiuqi.nvwa.definition.common.TableModelType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 *  com.jiuqi.xlib.utils.StringUtils
 */
package com.jiuqi.np.definition.impl.common;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.common.TableGatherType;
import com.jiuqi.np.definition.common.TableKind;
import com.jiuqi.np.definition.facade.DesignFieldDefine;
import com.jiuqi.np.definition.facade.DesignTableDefine;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.facade.TableDefine;
import com.jiuqi.np.definition.impl.internal.FieldDefineImpl;
import com.jiuqi.np.definition.impl.internal.TableDefineImpl;
import com.jiuqi.np.definition.impl.internal.service.DefinitionImplHelper;
import com.jiuqi.np.definition.internal.impl.FormatProperties;
import com.jiuqi.nr.datascheme.api.DataField;
import com.jiuqi.nr.datascheme.api.DataTable;
import com.jiuqi.nr.datascheme.api.core.DataFieldDeployInfo;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.datascheme.api.type.DataTableGatherType;
import com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl;
import com.jiuqi.nr.definition.internal.impl.DesignTableDefineImpl;
import com.jiuqi.nvwa.definition.common.ApplyType;
import com.jiuqi.nvwa.definition.common.ColumnModelType;
import com.jiuqi.nvwa.definition.common.TableDictType;
import com.jiuqi.nvwa.definition.common.TableModelType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import com.jiuqi.xlib.utils.StringUtils;
import java.util.Date;

public class DefinitionTransUtils {
    private DefinitionTransUtils() {
    }

    public static DesignTableDefine toDesignTableDefine(DataTable dataTable) {
        if (null == dataTable) {
            return null;
        }
        TableDefineImpl tableDefine = new TableDefineImpl(dataTable);
        tableDefine.setKey(dataTable.getKey());
        tableDefine.setCode(dataTable.getCode());
        tableDefine.setTableName(dataTable.getCode());
        tableDefine.setTitle(dataTable.getTitle());
        tableDefine.setDescription(dataTable.getDesc());
        tableDefine.setGatherType(DefinitionTransUtils.valueOf(dataTable.getDataTableGatherType()));
        tableDefine.setKind(TableKind.TABLE_KIND_BIZDATA);
        tableDefine.setBizKeyFields(dataTable.getBizKeys());
        tableDefine.setOwnerGroupID(dataTable.getDataGroupKey());
        tableDefine.setOwnerLevelAndId(dataTable.getLevel());
        tableDefine.setOrder(dataTable.getOrder());
        tableDefine.setVersion(dataTable.getVersion());
        tableDefine.setUpdateTime(Date.from(dataTable.getUpdateTime()));
        tableDefine.setHugeRecord(false);
        tableDefine.setIsAuTo(false);
        tableDefine.setNeedSynchronize(false);
        tableDefine.setSupportDatedVersion(false);
        return tableDefine;
    }

    public static DesignFieldDefine toDesignFieldDefine(DataField dataField, DataFieldDeployInfo info) {
        if (null == dataField) {
            return null;
        }
        FieldDefineImpl fieldDefine = new FieldDefineImpl(dataField.getKey(), dataField, info);
        fieldDefine.setKey(dataField.getKey());
        fieldDefine.setCode(dataField.getCode());
        fieldDefine.setTitle(dataField.getTitle());
        fieldDefine.setDescription(dataField.getDesc());
        fieldDefine.setAlias(dataField.getAlias());
        if (null != dataField.getDataFieldGatherType()) {
            fieldDefine.setGatherType(FieldGatherType.forValue((int)dataField.getDataFieldGatherType().getValue()));
        }
        fieldDefine.setType(DefinitionTransUtils.valueOf(dataField.getDataFieldType()));
        fieldDefine.setOwnerTableKey(dataField.getDataTableKey());
        fieldDefine.setSize(null == dataField.getPrecision() ? 0 : dataField.getPrecision());
        fieldDefine.setFractionDigits(null == dataField.getDecimal() ? 0 : dataField.getDecimal());
        fieldDefine.setDefaultValue(dataField.getDefaultValue());
        fieldDefine.setMeasureUnit(dataField.getMeasureUnit());
        fieldDefine.setNullable(dataField.isNullable());
        fieldDefine.setAllowUndefinedCode(null != dataField.getAllowUndefinedCode() && dataField.getAllowUndefinedCode() != false);
        fieldDefine.setEntityKey(dataField.getRefDataEntityKey());
        fieldDefine.setReferFieldKey(dataField.getRefDataFieldKey());
        fieldDefine.setAllowMultipleSelect(dataField.isAllowMultipleSelect());
        fieldDefine.setSecretLevel(null == dataField.getSecretLevel() ? 0 : dataField.getSecretLevel());
        FormatProperties properties = dataField.getFormatProperties();
        if (null != properties) {
            FormatProperties formatProperties = new FormatProperties();
            formatProperties.setFormatType(properties.getFormatType());
            formatProperties.setPattern(properties.getPattern());
            formatProperties.setProperties(properties.getProperties());
            fieldDefine.setFormatProperties(formatProperties);
        }
        fieldDefine.setUseAuthority(dataField.isUseAuthority());
        fieldDefine.setOwnerLevelAndId(dataField.getLevel());
        fieldDefine.setVersion(dataField.getVersion());
        fieldDefine.setOrder(dataField.getOrder());
        fieldDefine.setUpdateTime(Date.from(dataField.getUpdateTime()));
        if (dataField.getCode().equals("BIZKEYORDER")) {
            fieldDefine.setValueType(FieldValueType.FIELD_VALUE_BIZKEY_ORDER);
        } else if (dataField.getCode().equals("FLOATORDER")) {
            fieldDefine.setValueType(FieldValueType.FIELD_VALUE_INPUT_ORDER);
        }
        if (null != info) {
            fieldDefine.setFieldName(info.getFieldName());
            fieldDefine.setTableName(info.getTableName());
        }
        return fieldDefine;
    }

    public static DesignFieldDefine toDesignFieldDefine(DataFieldDeployInfo info) {
        return null;
    }

    private static TableGatherType valueOf(DataTableGatherType getherType) {
        if (null == getherType) {
            return TableGatherType.TABLE_GATHER_NONE;
        }
        switch (getherType) {
            case CLASSIFY: {
                return TableGatherType.TABLE_GATHER_CLASSIFY;
            }
            case LIST: {
                return TableGatherType.TABLE_GATHER_LIST;
            }
        }
        return TableGatherType.TABLE_GATHER_NONE;
    }

    public static FieldType valueOf(DataFieldType dataFieldType) {
        switch (dataFieldType) {
            case STRING: {
                return FieldType.FIELD_TYPE_STRING;
            }
            case INTEGER: {
                return FieldType.FIELD_TYPE_INTEGER;
            }
            case BOOLEAN: {
                return FieldType.FIELD_TYPE_LOGIC;
            }
            case DATE: {
                return FieldType.FIELD_TYPE_DATE;
            }
            case DATE_TIME: {
                return FieldType.FIELD_TYPE_DATE_TIME;
            }
            case UUID: {
                return FieldType.FIELD_TYPE_UUID;
            }
            case BIGDECIMAL: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case CLOB: {
                return FieldType.FIELD_TYPE_TEXT;
            }
            case PICTURE: {
                return FieldType.FIELD_TYPE_PICTURE;
            }
            case FILE: {
                return FieldType.FIELD_TYPE_FILE;
            }
        }
        return FieldType.FIELD_TYPE_GENERAL;
    }

    public static FieldType valueOf(ColumnModelType dataFieldType) {
        switch (dataFieldType) {
            case DOUBLE: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case STRING: {
                return FieldType.FIELD_TYPE_STRING;
            }
            case INTEGER: {
                return FieldType.FIELD_TYPE_INTEGER;
            }
            case BOOLEAN: {
                return FieldType.FIELD_TYPE_LOGIC;
            }
            case DATETIME: {
                return FieldType.FIELD_TYPE_DATE;
            }
            case UUID: {
                return FieldType.FIELD_TYPE_UUID;
            }
            case BIGDECIMAL: {
                return FieldType.FIELD_TYPE_DECIMAL;
            }
            case CLOB: {
                return FieldType.FIELD_TYPE_TEXT;
            }
            case BLOB: {
                return FieldType.FIELD_TYPE_BINARY;
            }
            case ATTACHMENT: {
                return FieldType.FIELD_TYPE_FILE;
            }
        }
        return FieldType.FIELD_TYPE_GENERAL;
    }

    private static FieldValueType valueOf(DataFieldApplyType applyType) {
        if (null == applyType) {
            return FieldValueType.FIELD_VALUE_DEFALUT;
        }
        switch (applyType) {
            case PERIODIC: {
                return FieldValueType.FIELD_VALUE_PERIODIC;
            }
            case TIME_POINT: {
                return FieldValueType.FIELD_VALUE_TIME_POINT;
            }
        }
        return FieldValueType.FIELD_VALUE_DEFALUT;
    }

    public static FieldValueType valueOf(ApplyType applyType) {
        if (null == applyType) {
            return FieldValueType.FIELD_VALUE_DEFALUT;
        }
        switch (applyType) {
            case PERIODIC: {
                return FieldValueType.FIELD_VALUE_PERIODIC;
            }
            case TIME_POINT: {
                return FieldValueType.FIELD_VALUE_TIME_POINT;
            }
        }
        return FieldValueType.FIELD_VALUE_DEFALUT;
    }

    public static TableDefine toTableDefine(DataTable dataTable) {
        return DefinitionTransUtils.toDesignTableDefine(dataTable);
    }

    public static FieldDefine toFieldDefine(DataField dataField, DataFieldDeployInfo info) {
        return DefinitionTransUtils.toDesignFieldDefine(dataField, info);
    }

    public static DesignFieldDefine toDesignFieldDefine(ColumnModelDefine columnModel) {
        DesignFieldDefineImpl fieldDefine = new DesignFieldDefineImpl();
        fieldDefine.setKey(columnModel.getID());
        fieldDefine.setCode(columnModel.getCode());
        fieldDefine.setTitle(columnModel.getTitle());
        fieldDefine.setFieldName(columnModel.getName());
        fieldDefine.setOwnerTableKey(columnModel.getTableID());
        fieldDefine.setDescription(columnModel.getDesc());
        fieldDefine.setType(DefinitionTransUtils.valueOf(columnModel.getColumnType()));
        fieldDefine.setSize(columnModel.getPrecision());
        fieldDefine.setFractionDigits(columnModel.getDecimal());
        fieldDefine.setNullable(columnModel.isNullAble());
        fieldDefine.setDBDefaultValue(columnModel.getDefaultValue());
        fieldDefine.setReferFieldKey(columnModel.getReferColumnID());
        if (StringUtils.hasLength((String)columnModel.getReferColumnID())) {
            fieldDefine.setEntityKey(DefinitionImplHelper.getEntityIdByColumn(columnModel.getReferColumnID()));
        }
        fieldDefine.setAllowMultipleSelect(columnModel.isMultival());
        fieldDefine.setGatherType(columnModel.getAggrType() == null ? FieldGatherType.FIELD_GATHER_NONE : FieldGatherType.forValue((int)columnModel.getAggrType().getValue()));
        fieldDefine.setValueType(DefinitionTransUtils.valueOf(columnModel.getApplyType()));
        fieldDefine.setShowFormat(columnModel.getShowFormat());
        fieldDefine.setMeasureUnit(columnModel.getMeasureUnit());
        return fieldDefine;
    }

    public static DesignTableDefine toDesignTableDefine(TableModelDefine tableModel) {
        DesignTableDefineImpl tableDefine = new DesignTableDefineImpl();
        tableDefine.setKey(tableModel.getID());
        tableDefine.setCode(tableModel.getCode());
        tableDefine.setTitle(tableModel.getTitle());
        tableDefine.setTableName(tableModel.getName());
        tableDefine.setDescription(tableModel.getDesc());
        tableDefine.setOwnerGroupID(tableModel.getCatalogID());
        tableDefine.setBizKeyFields(tableModel.getBizKeys());
        tableDefine.setOwnerLevelAndId(tableModel.getOwner());
        tableDefine.setUpdateTime(tableModel.getUpdateTime());
        if (tableModel.getType() == TableModelType.ENUM || tableModel.getType() == TableModelType.ENUM_NOSUMMARY) {
            tableDefine.setKind(TableKind.TABLE_KIND_DICTIONARY);
        } else if (tableModel.getType() == TableModelType.DATA) {
            tableDefine.setKind(TableKind.TABLE_KIND_BIZDATA);
        } else {
            tableDefine.setKind(TableKind.TABLE_KIND_SYSTEM);
        }
        tableDefine.setGatherType(TableGatherType.TABLE_GATHER_NONE);
        tableDefine.setSupportDatedVersion(TableDictType.ZIPPER == tableModel.getDictType());
        return tableDefine;
    }

    public static FieldDefine toFieldDefine(ColumnModelDefine columnModel, String tableName) {
        DesignFieldDefine designFieldDefine = DefinitionTransUtils.toDesignFieldDefine(columnModel);
        designFieldDefine.setTableName(tableName);
        if (columnModel.getCode().equals("BIZKEYORDER")) {
            designFieldDefine.setValueType(FieldValueType.FIELD_VALUE_BIZKEY_ORDER);
        } else if (columnModel.getCode().equals("FLOATORDER")) {
            designFieldDefine.setValueType(FieldValueType.FIELD_VALUE_INPUT_ORDER);
        }
        return designFieldDefine;
    }

    public static TableDefine toTableDefine(TableModelDefine tableModel) {
        return DefinitionTransUtils.toDesignTableDefine(tableModel);
    }
}

