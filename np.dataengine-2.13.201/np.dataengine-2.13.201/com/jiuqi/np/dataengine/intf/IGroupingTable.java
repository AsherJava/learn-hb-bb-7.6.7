/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import java.util.List;

public interface IGroupingTable
extends IReadonlyTable {
    public IDataRow findGroupingRow(DimensionValueSet var1);

    public List<DimensionValueSet> getGroupingDimensionValues();

    public List<IDataRow> findDetailRowsByGroupKey(DimensionValueSet var1);

    public List<IDataRow> findDetailRowsByGroupKeyByFirstDimension(DimensionValueSet var1, String var2);
}

