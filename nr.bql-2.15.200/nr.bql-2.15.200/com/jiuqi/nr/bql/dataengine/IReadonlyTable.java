/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionSet
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.bql.dataengine;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.bql.dataengine.IDataRow;
import com.jiuqi.nr.bql.dataengine.IFieldsInfo;

public interface IReadonlyTable {
    public IFieldsInfo getFieldsInfo();

    public DimensionValueSet getMasterKeys();

    public DimensionSet getMasterDimensions();

    public DimensionSet getRowDimensions();

    public int getCount();

    public IDataRow getItem(int var1);
}

