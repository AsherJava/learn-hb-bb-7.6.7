/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DataTypesConvert
 *  com.jiuqi.np.definition.common.FieldType
 *  com.jiuqi.np.definition.facade.FieldDefine
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.np.dataengine.common.DataTypesConvert;
import com.jiuqi.np.definition.common.FieldType;
import com.jiuqi.np.definition.facade.FieldDefine;

public class NrWrtiebackFieldInfo {
    private FieldDefine fieldDefine;
    private int columnIndex = -1;
    private int fieldIndex = -1;
    private int dataType;
    private boolean isFloatOrder;
    private boolean isBizkeyOrder;
    private boolean isDataTime;
    private String dimensionName;

    public NrWrtiebackFieldInfo(FieldDefine fieldDefine) {
        this.fieldDefine = fieldDefine;
        this.dataType = DataTypesConvert.fieldTypeToDataType((FieldType)fieldDefine.getType());
        this.isBizkeyOrder = "BIZKEYORDER".equals(fieldDefine.getCode());
        this.isFloatOrder = "FLOATORDER".equals(fieldDefine.getCode());
        this.isDataTime = "DATATIME".equals(fieldDefine.getCode());
    }

    public FieldDefine getFieldDefine() {
        return this.fieldDefine;
    }

    public int getColumnIndex() {
        return this.columnIndex;
    }

    public int getFieldIndex() {
        return this.fieldIndex;
    }

    public int getDataType() {
        return this.dataType;
    }

    public boolean isFloatOrder() {
        return this.isFloatOrder;
    }

    public boolean isBizkeyOrder() {
        return this.isBizkeyOrder;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public void setFieldIndex(int fieldIndex) {
        this.fieldIndex = fieldIndex;
    }

    public String getDimensionName() {
        return this.dimensionName;
    }

    public void setDimensionName(String dimensionName) {
        this.dimensionName = dimensionName;
    }

    public boolean isDataTime() {
        return this.isDataTime;
    }
}

