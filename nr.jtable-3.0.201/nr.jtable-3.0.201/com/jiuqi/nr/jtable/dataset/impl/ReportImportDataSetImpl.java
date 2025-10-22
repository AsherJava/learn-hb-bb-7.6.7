/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.IRegionImportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportImportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.RegionImportDataSetImpl;
import com.jiuqi.nr.jtable.filter.RegionTabFilter;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.List;

public class ReportImportDataSetImpl
implements IReportImportDataSet {
    private JtableContext jtableContext;
    private IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
    private IJtableContextService jtableContextService = (IJtableContextService)BeanUtil.getBean(IJtableContextService.class);

    public ReportImportDataSetImpl(JtableContext jtableContext) {
        this.jtableContext = jtableContext;
    }

    @Override
    public FormData getFormData() {
        return this.jtableParamService.getReport(this.jtableContext.getFormKey(), this.jtableContext.getFormSchemeKey());
    }

    @Override
    public List<RegionData> getDataRegionList() {
        List<RegionData> regions = this.jtableParamService.getRegions(this.jtableContext.getFormKey());
        DimensionValueSet dimensionValueSet = DimensionValueSetUtil.getDimensionValueSet(this.jtableContext);
        RegionTabFilter regionTabSettingFilter = new RegionTabFilter(this.jtableContext, dimensionValueSet);
        for (RegionData dataRegionDefine : regions) {
            List<RegionTab> regionTabs = this.jtableParamService.getRegionTabs(dataRegionDefine.getKey());
            if (null == regionTabs) continue;
            for (RegionTab regionTab : regionTabs) {
                if (!regionTabSettingFilter.accept(regionTab)) continue;
                dataRegionDefine.getTabs().add(regionTab);
            }
        }
        return regions;
    }

    @Override
    public IRegionImportDataSet getRegionImportDataSet(RegionData regionData) {
        return new RegionImportDataSetImpl(this.jtableContext, regionData);
    }
}

