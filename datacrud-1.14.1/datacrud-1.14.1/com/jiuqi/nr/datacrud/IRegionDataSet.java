/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.datacrud;

import com.jiuqi.nr.datacrud.IDataValue;
import com.jiuqi.nr.datacrud.IMetaData;
import com.jiuqi.nr.datacrud.IRowData;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;

public interface IRegionDataSet {
    public String getRegionKey();

    public DimensionCombination getMasterDimension();

    public List<IMetaData> getMetaData();

    public int getPage();

    public int getRowCount();

    public int getTotalCount();

    public List<IRowData> getRowData();

    public List<IDataValue> getDataValuesByLink(String var1);

    @Deprecated
    public boolean supportTreeGroup();
}

