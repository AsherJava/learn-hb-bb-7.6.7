/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.jtable.dataset.impl.RegionNumberManager;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.params.input.RegionQueryInfo;
import java.util.Iterator;
import java.util.List;

public interface IRegionExportDataSet
extends Iterator<MemoryDataSet<Object>> {
    public RegionQueryInfo getRegionQueryInfo();

    public RegionData getRegionData();

    public List<LinkData> getLinkDataList();

    public boolean isFloatRegion();

    public FieldData getFieldDataByDataLink(LinkData var1);

    public LinkData getLinkDataByField(FieldData var1);

    public List<FieldData> getFieldDataList();

    public int getTotalCount();

    public RegionNumberManager getNumberManager();

    public List<RegionTab> getRegionTabs();

    public void setRegionTab(RegionTab var1, int var2);

    public void setRegionFilter(List<String> var1);
}

