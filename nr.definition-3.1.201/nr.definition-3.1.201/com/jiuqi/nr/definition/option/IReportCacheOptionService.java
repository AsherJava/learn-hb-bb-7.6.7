/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.option;

import com.jiuqi.nr.definition.option.dto.ReportCacheConfig;
import java.util.List;

public interface IReportCacheOptionService {
    public boolean isHotUpdate();

    public boolean isCacheAutoExpire();

    public int getMaxProportionOfCache();

    public double getExpirationTime();

    public boolean isEnableMemThreshold();

    public List<ReportCacheConfig> getPermanentCacheRules();

    public List<ReportCacheConfig> getPreloadCacheRules();
}

