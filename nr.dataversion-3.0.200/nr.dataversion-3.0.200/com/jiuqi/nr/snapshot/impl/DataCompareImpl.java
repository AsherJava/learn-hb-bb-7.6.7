/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.definition.common.FormType
 *  com.jiuqi.nr.definition.controller.IRunTimeViewController
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 *  com.jiuqi.nr.definition.facade.FormDefine
 *  com.jiuqi.nr.io.params.base.RegionData
 *  com.jiuqi.nr.io.params.base.TableContext
 */
package com.jiuqi.nr.snapshot.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.definition.common.FormType;
import com.jiuqi.nr.definition.controller.IRunTimeViewController;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.io.params.base.RegionData;
import com.jiuqi.nr.io.params.base.TableContext;
import com.jiuqi.nr.snapshot.IDataCompare;
import com.jiuqi.nr.snapshot.IRegionCompareDifference;
import com.jiuqi.nr.snapshot.bean.CompareDifferenceItem;
import com.jiuqi.nr.snapshot.bean.FixedRegionCompareDifference;
import com.jiuqi.nr.snapshot.bean.FloatRegionCompareDifference;
import com.jiuqi.nr.snapshot.bean.FloatUniqueKeyRegionCompareDifference;
import com.jiuqi.nr.snapshot.bean.FormCompareDifference;
import com.jiuqi.nr.snapshot.compare.AbstractCompareStrategy;
import com.jiuqi.nr.snapshot.compare.RegionDataCompareFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataCompareImpl
implements IDataCompare {
    private static final Logger logger = LoggerFactory.getLogger(DataCompareImpl.class);
    @Autowired
    private IRunTimeViewController runtimeView;

    @Override
    public List<FormCompareDifference> comparePeriodData(Map<String, DimensionValue> dimensionSet, String formSchemeKey, List<String> formKeys, String initialDataPeriod, String compareDataPeriod) {
        ArrayList<FormCompareDifference> formDifferences = new ArrayList<FormCompareDifference>();
        for (String formKey : formKeys) {
            FormCompareDifference one;
            FormDefine queryFormById = this.runtimeView.queryFormById(formKey);
            if (queryFormById.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || (one = this.compareFormVersionData(dimensionSet, formSchemeKey, formKey, initialDataPeriod, compareDataPeriod, "DATATIME")).getUpdateRegions() == null || one.getUpdateRegions().size() <= 0) continue;
            formDifferences.add(one);
        }
        return formDifferences;
    }

    @Override
    public List<FormCompareDifference> compareDWData(Map<String, DimensionValue> dimensionSet, String formSchemeKey, List<String> formKeys, String initialDataDW, String compareDataDW, String dimensionName) {
        ArrayList<FormCompareDifference> formDifferences = new ArrayList<FormCompareDifference>();
        for (String formKey : formKeys) {
            FormCompareDifference one;
            FormDefine queryFormById = this.runtimeView.queryFormById(formKey);
            if (queryFormById.getFormType().equals((Object)FormType.FORM_TYPE_NEWFMDM) || (one = this.compareFormVersionData(dimensionSet, formSchemeKey, formKey, initialDataDW, compareDataDW, dimensionName)).getUpdateRegions() == null || one.getUpdateRegions().size() <= 0) continue;
            formDifferences.add(one);
        }
        return formDifferences;
    }

    private FormCompareDifference compareFormVersionData(Map<String, DimensionValue> dimensionSetMap, String formSchemeKey, String formKey, String initialDimension, String compareDimension, String dimensionName) {
        FormCompareDifference formCompareDifference = new FormCompareDifference();
        FormDefine form = this.runtimeView.queryFormById(formKey);
        formCompareDifference.setFormKey(formKey);
        formCompareDifference.setFormCode(form.getFormCode());
        formCompareDifference.setFormName(form.getTitle());
        ArrayList<IRegionCompareDifference> regionCompareDifferences = new ArrayList<IRegionCompareDifference>();
        formCompareDifference.setUpdateRegions(regionCompareDifferences);
        List<RegionData> regions = this.getRegions(formKey);
        TableContext tableContext = new TableContext();
        tableContext.setFormSchemeKey(formSchemeKey);
        tableContext.setFormKey(formKey);
        DimensionValueSet dimensionSet = new DimensionValueSet();
        if (null != dimensionSetMap) {
            for (String key : dimensionSetMap.keySet()) {
                dimensionSet.setValue(key, (Object)dimensionSetMap.get(key).getValue());
            }
        }
        tableContext.setDimensionSet(dimensionSet);
        RegionDataCompareFactory regionDataCompareFactory = new RegionDataCompareFactory();
        for (RegionData region : regions) {
            AbstractCompareStrategy regionDataVersionCompareStrategy;
            IRegionCompareDifference regionCompareDifference;
            DataRegionDefine drd = this.runtimeView.queryDataRegionDefine(region.getKey());
            boolean isDuplicate = false;
            if (drd.getBizKeyFields() != null && !drd.getBizKeyFields().equals("")) {
                isDuplicate = true;
            }
            if (null == (regionCompareDifference = (regionDataVersionCompareStrategy = regionDataCompareFactory.getRegionDataVersionCompareStrategy(region, isDuplicate)).compareRegionVersionData(region, tableContext, initialDimension, compareDimension, dimensionName))) continue;
            if (regionCompareDifference instanceof FixedRegionCompareDifference) {
                FixedRegionCompareDifference fixedRegionCompareDifference = (FixedRegionCompareDifference)regionCompareDifference;
                List<CompareDifferenceItem> updateItems = fixedRegionCompareDifference.getUpdateItems();
                if (null == updateItems || updateItems.size() <= 0) continue;
                regionCompareDifferences.add(regionCompareDifference);
                continue;
            }
            if (regionCompareDifference instanceof FloatRegionCompareDifference) {
                regionCompareDifferences.add(regionCompareDifference);
                continue;
            }
            FloatUniqueKeyRegionCompareDifference floatUniqueKeyRegionCompareDifference = (FloatUniqueKeyRegionCompareDifference)regionCompareDifference;
            if (null == floatUniqueKeyRegionCompareDifference.getNatures() || floatUniqueKeyRegionCompareDifference.getNatures().size() <= 0) continue;
            regionCompareDifferences.add(regionCompareDifference);
        }
        return formCompareDifference;
    }

    private List<RegionData> getRegions(String formKey) {
        ArrayList<RegionData> regions = new ArrayList<RegionData>();
        List allRegionDefines = this.runtimeView.getAllRegionsInForm(formKey);
        for (DataRegionDefine dataRegionDefine : allRegionDefines) {
            RegionData regionData = new RegionData();
            regionData.initialize(dataRegionDefine);
            regions.add(regionData);
        }
        return regions;
    }
}

