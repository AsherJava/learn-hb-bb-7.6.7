/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 *  com.jiuqi.nvwa.dataengine.common.Convert
 */
package com.jiuqi.nr.bql.dataengine.impl;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.dataengine.IFieldsInfo;
import com.jiuqi.nr.bql.dataengine.impl.ReadonlyTableImpl;
import com.jiuqi.nvwa.dataengine.common.Convert;
import java.util.Date;

public class DataRowImpl
implements IDataRow {
    protected ReadonlyTableImpl tableImpl;
    protected DimensionValueSet rowKeys;
    protected Object[] rowDatas;

    public DataRowImpl(ReadonlyTableImpl table, DimensionValueSet rowKeys, Object[] rowDatas) {
        this.tableImpl = table;
        this.rowKeys = rowKeys;
        this.rowDatas = rowDatas;
    }

    public DataRowImpl(DimensionValueSet rowKeys) {
        this.rowKeys = rowKeys;
    }

    @Override
    public IFieldsInfo getFieldsInfo() {
        return this.tableImpl.getFieldsInfo();
    }

    @Override
    public DimensionValueSet getMasterKeys() {
        return this.tableImpl.getMasterKeys();
    }

    @Override
    public DimensionSet getMasterDimensions() {
        return this.tableImpl.getMasterDimensions();
    }

    @Override
    public DimensionSet getRowDimensions() {
        return this.tableImpl.getRowDimensions();
    }

    @Override
    public DimensionValueSet getRowKeys() {
        return this.rowKeys;
    }

    @Override
    public Object getValue(int fieldIndex, int returnDataType) throws DataTypeException {
        Object resultValue = this.rowDatas == null || this.rowDatas.length <= fieldIndex ? null : this.rowDatas[fieldIndex];
        return this.convertData(resultValue, returnDataType);
    }

    private Object convertData(Object value, int dataType) throws DataTypeException {
        if (value == null) {
            return null;
        }
        try {
            switch (dataType) {
                case 1: {
                    return Convert.toBoolean((Object)value);
                }
                case 10: {
                    return Convert.toBigDecimal((Object)value);
                }
                case 2: {
                    return new Date(Convert.toDate((Object)value));
                }
                case 6: 
                case 12: {
                    return Convert.toString((Object)value);
                }
                case 3: {
                    return Convert.toDouble((Object)value);
                }
                case 5: {
                    return Convert.toInt((Object)value);
                }
                case 33: {
                    return Convert.toUUID((Object)value);
                }
            }
            return value;
        }
        catch (Exception e) {
            throw new DataTypeException(e);
        }
    }

    public Object[] getRowDatas() {
        return this.rowDatas;
    }

    public void setRowDatas(Object[] rowDatas) {
        this.rowDatas = rowDatas;
    }

    public String toString() {
        return "DataRowImpl [rowKeys=" + this.rowKeys + ", rowDatas=" + this.rowDatas + "]";
    }
}

