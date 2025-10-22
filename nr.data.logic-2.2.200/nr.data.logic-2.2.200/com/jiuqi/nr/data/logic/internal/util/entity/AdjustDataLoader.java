/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService
 *  com.jiuqi.nr.definition.internal.BeanUtil
 */
package com.jiuqi.nr.data.logic.internal.util.entity;

import com.jiuqi.nr.data.logic.internal.util.entity.IDimDataLoader;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.datascheme.api.service.IAdjustPeriodService;
import com.jiuqi.nr.definition.internal.BeanUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.util.CollectionUtils;

public class AdjustDataLoader
implements IDimDataLoader {
    private final Map<String, AdjustPeriod> cache = new HashMap<String, AdjustPeriod>();

    public AdjustDataLoader(String dataSchemeKey, String period) {
        IAdjustPeriodService adjustPeriodService = (IAdjustPeriodService)BeanUtil.getBean(IAdjustPeriodService.class);
        List adjustPeriods = adjustPeriodService.queryAdjustPeriods(dataSchemeKey, period);
        if (!CollectionUtils.isEmpty(adjustPeriods)) {
            adjustPeriods.forEach(o -> this.cache.put(o.getCode(), (AdjustPeriod)o));
        }
    }

    @Override
    public String getCodeByEntityDataKey(String entityDataKey) {
        AdjustPeriod adjustPeriod = this.cache.get(entityDataKey);
        if (adjustPeriod != null) {
            return adjustPeriod.getCode();
        }
        return null;
    }

    @Override
    public String getTitleByEntityDataKey(String entityDataKey) {
        AdjustPeriod adjustPeriod = this.cache.get(entityDataKey);
        if (adjustPeriod != null) {
            return adjustPeriod.getTitle();
        }
        return null;
    }
}

