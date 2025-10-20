/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.config;

import org.springframework.stereotype.Component;

@Component
public interface MonitorSystemOptionsProvider {
    default public Boolean getEnable() {
        return false;
    }

    default public Boolean getEnableApi() {
        return false;
    }

    default public Boolean getEnableSql() {
        return false;
    }

    default public String getHttpUrl() {
        return null;
    }

    default public Integer getBatchSize() {
        return null;
    }

    default public Integer getRetryCount() {
        return null;
    }

    default public Integer getFailedRetrySeconds() {
        return null;
    }

    default public Integer getSendMaxIntervalSeconds() {
        return null;
    }

    default public Boolean isUseLogRecord() {
        return false;
    }
}

