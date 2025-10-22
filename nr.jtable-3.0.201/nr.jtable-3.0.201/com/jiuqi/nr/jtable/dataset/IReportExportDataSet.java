/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.params.PagerInfo
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.jtable.dataset.IRegionExportDataSet;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import java.util.List;

public interface IReportExportDataSet {
    public FormData getFormData();

    public List<RegionData> getDataRegionList();

    public IRegionExportDataSet getRegionExportDataSet(RegionData var1);

    public IRegionExportDataSet getRegionExportDataSet(RegionData var1, boolean var2);

    public IRegionExportDataSet getRegionExportDataSet(RegionData var1, boolean var2, PagerInfo var3, boolean var4);

    public IRegionExportDataSet getRegionExportDataSet(RegionData var1, boolean var2, boolean var3);
}

