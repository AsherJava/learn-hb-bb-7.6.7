/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService
 */
package com.jiuqi.nr.definition.option.impl;

import com.jiuqi.nr.definition.option.IReportCacheOptionService;
import com.jiuqi.nr.definition.option.common.ReportCacheUtil;
import com.jiuqi.nr.definition.option.dto.ReportCacheConfig;
import com.jiuqi.nvwa.systemoption.service.INvwaSystemOptionService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ReportCacheOptionServiceImpl
implements IReportCacheOptionService {
    @Autowired
    private INvwaSystemOptionService systemOptionService;

    @Override
    public boolean isHotUpdate() {
        return this.getOptionValue("report_cache_hot_update").equals("1");
    }

    @Override
    public boolean isCacheAutoExpire() {
        return this.getOptionValue("report_cache_expire_switch").equals("1");
    }

    @Override
    public int getMaxProportionOfCache() {
        String optionValue = this.getOptionValue("report_cache_percent");
        return Integer.parseInt(optionValue);
    }

    @Override
    public double getExpirationTime() {
        String optionValue = this.getOptionValue("report_cache_expiration_time");
        return Double.parseDouble(optionValue);
    }

    @Override
    public boolean isEnableMemThreshold() {
        return this.getOptionValue("report_cache_mem_threshold_switch").equals("1");
    }

    @Override
    public List<ReportCacheConfig> getPermanentCacheRules() {
        ArrayList<ReportCacheConfig> configs = new ArrayList<ReportCacheConfig>();
        String optionValue = this.getOptionValue("report_cache_permanent_detail");
        if (StringUtils.hasText(optionValue)) {
            configs.addAll(ReportCacheUtil.configRevert(optionValue));
            return configs;
        }
        return configs;
    }

    @Override
    public List<ReportCacheConfig> getPreloadCacheRules() {
        ArrayList<ReportCacheConfig> configs = new ArrayList<ReportCacheConfig>();
        String optionValue = this.getOptionValue("report_cache_preload_detail");
        if (StringUtils.hasText(optionValue)) {
            configs.addAll(ReportCacheUtil.configRevert(optionValue));
            return configs;
        }
        return configs;
    }

    private String getOptionValue(String optionItemKey) {
        return this.systemOptionService.get("report-cache-option", optionItemKey);
    }
}

