/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.jtable.dataset;

import com.jiuqi.nr.jtable.dataset.IRegionImportDataSet;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.RegionData;
import java.util.List;

public interface IReportImportDataSet {
    public FormData getFormData();

    public List<RegionData> getDataRegionList();

    public IRegionImportDataSet getRegionImportDataSet(RegionData var1);
}

