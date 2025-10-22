/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.common.FieldGatherType
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.common.FieldValueType
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.nr.datascheme.api.type.DataFieldApplyType
 *  com.jiuqi.nr.datascheme.api.type.DataFieldType
 */
package com.jiuqi.nr.definition.internal.dao;

import com.jiuqi.np.definition.common.FieldGatherType;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.common.FieldValueType;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.nr.datascheme.api.type.DataFieldApplyType;
import com.jiuqi.nr.datascheme.api.type.DataFieldType;
import com.jiuqi.nr.definition.internal.impl.DesignFieldDefineImpl;

class DataFieldDO {
    private String key;
    private String code;
    private String title;
    private String dataTableKey;
    private String defaultValue;
    private String refDataEntityKey;
    private String refDataFieldKey;
    private String measureUnit;
    private String fieldName;
    private String tableName;
    private int dataType;
    private int applyType;
    private int aggrType;
    private int nullable;

    DataFieldDO() {
    }

    public static FieldDefine toFieldDefine(DataFieldDO dataFieldDO) {
        DesignFieldDefineImpl fieldDefine = new DesignFieldDefineImpl();
        fieldDefine.setKey(dataFieldDO.getKey());
        fieldDefine.setCode(dataFieldDO.getCode());
        fieldDefine.setTitle(dataFieldDO.getTitle());
        fieldDefine.setOwnerTableKey(dataFieldDO.getDataTableKey());
        fieldDefine.setDefaultValue(dataFieldDO.getDefaultValue());
        fieldDefine.setReferFieldKey(dataFieldDO.getRefDataFieldKey());
        fieldDefine.setEntityKey(dataFieldDO.getRefDataEntityKey());
        fieldDefine.setMeasureUnit(dataFieldDO.getMeasureUnit());
        fieldDefine.setFieldName(dataFieldDO.getFieldName());
        fieldDefine.setTableName(dataFieldDO.getTableName());
        fieldDefine.setType(DataFieldDO.valueOf(DataFieldType.valueOf((int)dataFieldDO.getDataType())));
        fieldDefine.setValueType(DataFieldDO.valueOf(DataFieldApplyType.valueOf((int)dataFieldDO.getApplyType())));
        fieldDefine.setGatherType(FieldGatherType.forValue((int)dataFieldDO.getAggrType()));
        fieldDefine.setNullable(dataFieldDO.getNullable() == 1);
        return fieldDefine;
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

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDataTableKey() {
        return this.dataTableKey;
    }

    public void setDataTableKey(String dataTableKey) {
        this.dataTableKey = dataTableKey;
    }

    public String getDefaultValue() {
        return this.defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getRefDataFieldKey() {
        return this.refDataFieldKey;
    }

    public void setRefDataFieldKey(String refDataFieldKey) {
        this.refDataFieldKey = refDataFieldKey;
    }

    public String getMeasureUnit() {
        return this.measureUnit;
    }

    public void setMeasureUnit(String measureUnit) {
        this.measureUnit = measureUnit;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getTableName() {
        return this.tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getRefDataEntityKey() {
        return this.refDataEntityKey;
    }

    public void setRefDataEntityKey(String refDataEntityKey) {
        this.refDataEntityKey = refDataEntityKey;
    }

    public int getDataType() {
        return this.dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getApplyType() {
        return this.applyType;
    }

    public void setApplyType(int applyType) {
        this.applyType = applyType;
    }

    public int getAggrType() {
        return this.aggrType;
    }

    public void setAggrType(int aggrType) {
        this.aggrType = aggrType;
    }

    public int getNullable() {
        return this.nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }
}

