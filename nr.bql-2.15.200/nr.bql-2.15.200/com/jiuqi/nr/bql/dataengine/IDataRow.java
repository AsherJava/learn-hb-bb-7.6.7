/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.np.dataengine.exception.DataTypeException
 */
package com.jiuqi.nr.bql.dataengine;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.DataTypeException;
import com.jiuqi.nr.bql.dataengine.IFieldsInfo;

public interface IDataRow {
    public IFieldsInfo getFieldsInfo();

    public DimensionValueSet getMasterKeys();

    public DimensionSet getMasterDimensions();

    public DimensionSet getRowDimensions();

    public DimensionValueSet getRowKeys();

    public Object getValue(int var1, int var2) throws DataTypeException;
}

