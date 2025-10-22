/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface IRowData {
    public DimensionCombination getMasterDimension();

    public DimensionCombination getDimension();

    public String getRecKey();

    public List<IDataValue> getLinkDataValues();

    public IDataValue getDataValueByLink(String var1);

    public IDataValue getDataValueByField(String var1);

    public boolean isFilledRow();

    public int getDetailSeqNum();

    @Deprecated
    public int getGroupingFlag();

    public int getGroupTreeDeep();

    @Deprecated
    default public int getParentLevel() {
        return -1;
    }
}

