/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.excel.service.impl;

import com.jiuqi.nr.data.excel.obj.DimensionData;
import com.jiuqi.nr.data.excel.service.internal.IDimensionDataAdapter;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class AdjustDataAdapterImpl
implements IDimensionDataAdapter {
    private final Map<String, DimensionData> cache = new HashMap<String, DimensionData>();
    private final Map<String, List<DimensionData>> titleCache = new HashMap<String, List<DimensionData>>();

    public AdjustDataAdapterImpl(String dataSchemeKey, String period) {
        IAdjustPeriodService adjustPeriodService = (IAdjustPeriodService)BeanUtil.getBean(IAdjustPeriodService.class);
        List adjustPeriods = adjustPeriodService.queryAdjustPeriods(dataSchemeKey, period);
        if (!CollectionUtils.isEmpty(adjustPeriods)) {
            adjustPeriods.forEach(o -> {
                DimensionData dimensionData = new DimensionData(o.getCode(), o.getCode(), o.getTitle());
                this.cache.put(o.getCode(), dimensionData);
                if (this.titleCache.containsKey(o.getTitle())) {
                    this.titleCache.get(o.getTitle()).add(dimensionData);
                } else {
                    ArrayList<DimensionData> list = new ArrayList<DimensionData>();
                    list.add(dimensionData);
                    this.titleCache.put(o.getTitle(), list);
                }
            });
        }
    }

    @Override
    public DimensionData getDimensionData(String dimValue) {
        return this.cache.get(dimValue);
    }

    @Override
    public List<DimensionData> getByTitle(String dimValueTitle) {
        return this.titleCache.get(dimValueTitle);
    }

    @Override
    public String getCode(String dimValue) {
        DimensionData dimensionData = this.cache.get(dimValue);
        if (dimensionData != null) {
            return dimensionData.getCode();
        }
        return null;
    }

    @Override
    public String getTitle(String dimValue) {
        DimensionData dimensionData = this.cache.get(dimValue);
        if (dimensionData != null) {
            return dimensionData.getTitle();
        }
        return null;
    }
}

