/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 *  com.jiuqi.nr.common.importdata.ImportResultRegionObject
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.nr.common.importdata.ImportResultRegionObject;
import com.jiuqi.nr.jtable.params.base.FieldData;
import com.jiuqi.nr.jtable.params.base.LinkData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import java.util.List;

public interface IRegionImportDataSet {
    public RegionData getRegionData();

    public List<LinkData> getLinkDataList();

    public boolean isFloatRegion();

    public FieldData getFieldDataByDataLink(LinkData var1);

    public List<FieldData> getFieldDataList();

    public ImportResultRegionObject importDataRowSet(MemoryDataSet<Object> var1);

    public void commitRangeData();

    public List<RegionTab> getRegionTabs();

    public void setRegionTab(RegionTab var1);

    public void setSaveFileGroupKey(boolean var1);
}

