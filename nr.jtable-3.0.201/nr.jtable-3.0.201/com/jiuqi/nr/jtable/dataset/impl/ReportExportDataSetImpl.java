/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.PagerInfo
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.jtable.dataset.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.PagerInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCollectionBuilder;
import com.jiuqi.nr.definition.internal.BeanUtil;
import com.jiuqi.nr.jtable.dataset.IRegionExportDataSet;
import com.jiuqi.nr.jtable.dataset.IReportExportDataSet;
import com.jiuqi.nr.jtable.dataset.impl.RegionExportDataSetImpl;
import com.jiuqi.nr.jtable.filter.RegionTabFilter;
import com.jiuqi.nr.jtable.params.base.EntityViewData;
import com.jiuqi.nr.jtable.params.base.FormData;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import com.jiuqi.nr.jtable.params.base.RegionData;
import com.jiuqi.nr.jtable.params.base.RegionTab;
import com.jiuqi.nr.jtable.service.IJtableContextService;
import com.jiuqi.nr.jtable.service.IJtableParamService;
import com.jiuqi.nr.jtable.util.DimensionValueSetUtil;
import java.util.List;

public class ReportExportDataSetImpl
implements IReportExportDataSet {
    private JtableContext jtableContext;
    private IJtableParamService jtableParamService = (IJtableParamService)BeanUtil.getBean(IJtableParamService.class);
    private IJtableContextService jtableContextService = (IJtableContextService)BeanUtil.getBean(IJtableContextService.class);

    public ReportExportDataSetImpl(JtableContext jtableContext) {
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
    public IRegionExportDataSet getRegionExportDataSet(RegionData regionData) {
        this.setCollectionDim(this.jtableContext);
        return new RegionExportDataSetImpl(this.jtableContext, regionData);
    }

    @Override
    public IRegionExportDataSet getRegionExportDataSet(RegionData regionData, boolean haveId, PagerInfo pagerInfo, boolean noSumData) {
        this.setCollectionDim(this.jtableContext);
        return new RegionExportDataSetImpl(this.jtableContext, regionData, haveId, pagerInfo, noSumData);
    }

    @Override
    public IRegionExportDataSet getRegionExportDataSet(RegionData regionData, boolean haveId) {
        this.setCollectionDim(this.jtableContext);
        return new RegionExportDataSetImpl(this.jtableContext, regionData, haveId);
    }

    @Override
    public IRegionExportDataSet getRegionExportDataSet(RegionData regionData, boolean haveId, boolean noSumData) {
        this.setCollectionDim(this.jtableContext);
        return new RegionExportDataSetImpl(this.jtableContext, regionData, haveId, noSumData);
    }

    private void setCollectionDim(JtableContext jtableContext) {
        DimensionValueSet dimensionSet = DimensionValueSetUtil.getDimensionValueSet(jtableContext);
        DimensionCollectionBuilder builder = new DimensionCollectionBuilder();
        List<EntityViewData> entityViewDataList = this.jtableParamService.getEntityList(jtableContext.getFormSchemeKey());
        for (int j = 0; j < dimensionSet.size(); ++j) {
            boolean flag = false;
            for (EntityViewData entityViewData : entityViewDataList) {
                if (!entityViewData.getDimensionName().equals(dimensionSet.getName(j))) continue;
                builder.setEntityValue(dimensionSet.getName(j), entityViewData.getKey(), new Object[]{dimensionSet.getValue(j)});
                flag = true;
            }
            if (flag) continue;
            builder.setValue(dimensionSet.getName(j), new Object[]{dimensionSet.getValue(j)});
        }
        DimensionValueSet varDim = builder.getCollection().combineWithoutVarDim();
        jtableContext.setDimensionSet(DimensionValueSetUtil.getDimensionSet(varDim));
    }
}

