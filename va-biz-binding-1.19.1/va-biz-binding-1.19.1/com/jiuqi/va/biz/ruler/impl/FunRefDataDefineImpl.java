/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.datamodel.DataModelColumn
 *  com.jiuqi.va.domain.datamodel.DataModelType$ColumnType
 *  com.jiuqi.va.formula.intf.FunRefDataDefine
 */
package com.jiuqi.va.biz.ruler.impl;

import com.jiuqi.va.domain.datamodel.DataModelColumn;
import com.jiuqi.va.domain.datamodel.DataModelType;
import com.jiuqi.va.formula.intf.FunRefDataDefine;
import java.util.Map;

public class FunRefDataDefineImpl
implements FunRefDataDefine {
    private String tableName;
    private String fieldName;
    private int dataType;
    private Integer mappingType;

    public FunRefDataDefineImpl(String tableName, String fieldName, DataModelType.ColumnType dataType, Integer mappingType) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.mappingType = mappingType;
        this.setDataType(dataType);
    }

    public FunRefDataDefineImpl(String tableName, String fieldName, DataModelType.ColumnType dataType, DataModelColumn column) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        this.setDataType(dataType, column);
    }

    public FunRefDataDefineImpl(String tableName, String fieldName, DataModelType.ColumnType dataType, DataModelColumn column, Map<String, Object> fieldProp) {
        this.tableName = tableName;
        this.fieldName = fieldName;
        if (fieldProp == null) {
            this.setDataType(dataType, column);
        } else {
            this.setDataType(dataType, column, fieldProp);
        }
    }

    private void setDataType(DataModelType.ColumnType dataType, DataModelColumn column, Map<String, Object> fieldProp) {
        switch (dataType) {
            case UUID: {
                this.dataType = 33;
                break;
            }
            case NVARCHAR: {
                if (fieldProp.get("multiple") != null && ((Boolean)fieldProp.get("multiple")).booleanValue()) {
                    this.dataType = 11;
                    break;
                }
                this.dataType = 6;
                break;
            }
            case INTEGER: {
                if (column.getMappingType() != null && column.getMappingType() == 0) {
                    this.dataType = 1;
                    break;
                }
                this.dataType = 3;
                break;
            }
            case NUMERIC: {
                this.dataType = 3;
                break;
            }
            case DATE: {
                this.dataType = 2;
                break;
            }
            case TIMESTAMP: {
                this.dataType = 2;
                break;
            }
            case CLOB: {
                this.dataType = 11;
                break;
            }
            default: {
                this.dataType = -1;
            }
        }
    }

    private void setDataType(DataModelType.ColumnType dataType) {
        switch (dataType) {
            case UUID: {
                this.dataType = 33;
                break;
            }
            case NVARCHAR: {
                this.dataType = 6;
                break;
            }
            case INTEGER: {
                if (this.mappingType != null && this.mappingType == 0) {
                    this.dataType = 1;
                    break;
                }
                this.dataType = 3;
                break;
            }
            case NUMERIC: {
                this.dataType = 3;
                break;
            }
            case DATE: {
                this.dataType = 2;
                break;
            }
            case TIMESTAMP: {
                this.dataType = 2;
                break;
            }
            case CLOB: {
                this.dataType = 11;
                break;
            }
            default: {
                this.dataType = -1;
            }
        }
    }

    private void setDataType(DataModelType.ColumnType dataType, DataModelColumn column) {
        switch (dataType) {
            case UUID: {
                this.dataType = 33;
                break;
            }
            case NVARCHAR: {
                this.dataType = 6;
                break;
            }
            case INTEGER: {
                if (column.getMappingType() != null && column.getMappingType() == 0) {
                    this.dataType = 1;
                    break;
                }
                this.dataType = 3;
                break;
            }
            case NUMERIC: {
                this.dataType = 3;
                break;
            }
            case DATE: {
                this.dataType = 2;
                break;
            }
            case TIMESTAMP: {
                this.dataType = 2;
                break;
            }
            case CLOB: {
                this.dataType = 11;
                break;
            }
            default: {
                this.dataType = -1;
            }
        }
    }

    public String getTableName() {
        return this.tableName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public int getDataType() {
        return this.dataType;
    }

    public Integer getMappingType() {
        return this.mappingType;
    }
}

