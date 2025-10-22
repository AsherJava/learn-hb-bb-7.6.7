/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionSet;
import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.setting.IFieldsInfo;
import java.util.List;

public interface IReadonlyTable {
    public IFieldsInfo getFieldsInfo();

    public DimensionValueSet getMasterKeys();

    public DimensionSet getMasterDimensions();

    public DimensionSet getRowDimensions();

    public int getCount();

    public int getTotalCount();

    public IDataRow getItem(int var1);

    public IDataRow findRow(DimensionValueSet var1);

    public List<IDataRow> findFuzzyRows(DimensionValueSet var1);

    default public boolean supportTreeGroup() {
        return true;
    }
}

