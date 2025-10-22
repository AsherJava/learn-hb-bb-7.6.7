/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 *  com.jiuqi.nr.common.params.DimensionValue
 *  com.jiuqi.nr.datacrud.IRegionDataSet
 *  com.jiuqi.nr.datacrud.Measure
 *  com.jiuqi.nr.datacrud.PageInfo
 *  com.jiuqi.nr.datacrud.QueryInfoBuilder
 *  com.jiuqi.nr.datacrud.api.IDataQueryService
 *  com.jiuqi.nr.datacrud.spi.RowFilter
 *  com.jiuqi.nr.datacrud.spi.filter.FormulaFilter
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder
 *  com.jiuqi.nr.definition.facade.DataRegionDefine
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.common.params.DimensionValue;
import com.jiuqi.nr.data.excel.param.DataExportParam;
import com.jiuqi.nr.data.excel.param.RegionFilterListInfo;
import com.jiuqi.nr.data.excel.param.query.RegionQuery;
import com.jiuqi.nr.datacrud.IRegionDataSet;
import com.jiuqi.nr.datacrud.Measure;
import com.jiuqi.nr.datacrud.PageInfo;
import com.jiuqi.nr.datacrud.QueryInfoBuilder;
import com.jiuqi.nr.datacrud.api.IDataQueryService;
import com.jiuqi.nr.datacrud.spi.RowFilter;
import com.jiuqi.nr.datacrud.spi.filter.FormulaFilter;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombinationBuilder;
import com.jiuqi.nr.definition.facade.DataRegionDefine;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegionQueryProvider {
    @Autowired
    private IDataQueryService dataQueryService;

    public RegionQuery getRegionQuery(DataExportParam info, DataRegionDefine region) {
        Map<String, DimensionValue> dimensionSet = info.getContext().getDimensionSet();
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(this.getDimensionValueSet(dimensionSet));
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)region.getKey(), (DimensionCombination)builder.getCombination());
        Measure measure = new Measure();
        measure.setKey("9493b4eb-6516-48a8-a878-25a63a23e63a;".substring(0, "9493b4eb-6516-48a8-a878-25a63a23e63a;".length() - 1));
        measure.setCode("WANYUAN");
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        return new RegionQuery(iRegionDataSet, queryInfoBuilder);
    }

    public RegionQuery getRegionQuery(DataExportParam info, DataRegionDefine region, PageInfo pageInfo) {
        Map<String, DimensionValue> dimensionSet = info.getContext().getDimensionSet();
        DimensionCombinationBuilder builder = new DimensionCombinationBuilder(this.getDimensionValueSet(dimensionSet));
        QueryInfoBuilder queryInfoBuilder = QueryInfoBuilder.create((String)region.getKey(), (DimensionCombination)builder.getCombination());
        List<RegionFilterListInfo> regionFilterListInfo = info.getRegionFilterListInfo();
        List<Object> filterList = new ArrayList();
        if (regionFilterListInfo != null && !regionFilterListInfo.isEmpty()) {
            for (RegionFilterListInfo filterListInfo : regionFilterListInfo) {
                if (!filterListInfo.getAreaKey().equals(region.getKey())) continue;
                filterList = filterListInfo.getFilterConditions();
            }
        }
        if (!filterList.isEmpty()) {
            for (String string : filterList) {
                FormulaFilter filter = new FormulaFilter(string);
                queryInfoBuilder.where((RowFilter)filter);
            }
        }
        Measure measure = new Measure();
        measure.setKey("9493b4eb-6516-48a8-a878-25a63a23e63a;".substring(0, "9493b4eb-6516-48a8-a878-25a63a23e63a;".length() - 1));
        measure.setCode("WANYUAN");
        if (pageInfo != null) {
            queryInfoBuilder.setPage(pageInfo);
        }
        IRegionDataSet iRegionDataSet = this.dataQueryService.queryRegionData(queryInfoBuilder.build());
        return new RegionQuery(iRegionDataSet, queryInfoBuilder);
    }

    private DimensionValueSet getDimensionValueSet(Map<String, DimensionValue> dimensionSet) {
        DimensionValueSet dimensionValueSet = new DimensionValueSet();
        if (dimensionSet == null) {
            return dimensionValueSet;
        }
        for (DimensionValue value : dimensionSet.values()) {
            String[] values = value.getValue().split(";");
            if (values.length == 1 || values.length == 0) {
                dimensionValueSet.setValue(value.getName(), (Object)value.getValue());
                continue;
            }
            List<String> valueList = Arrays.asList(values);
            dimensionValueSet.setValue(value.getName(), valueList);
        }
        return dimensionValueSet;
    }
}

