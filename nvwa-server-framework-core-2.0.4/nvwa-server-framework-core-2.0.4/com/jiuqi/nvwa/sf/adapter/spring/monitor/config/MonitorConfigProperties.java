/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.config;

import com.jiuqi.nvwa.sf.adapter.spring.monitor.config.MonitorSystemOptionsProvider;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value=1)
@ConfigurationProperties(prefix="jiuqi.nvwa.monitor.skywalking.config")
@PropertySource(value={"classpath:nvwa-monitor-skywalking.properties"})
public class MonitorConfigProperties
implements MonitorSystemOptionsProvider {
    private Integer sendMaxIntervalSeconds;
    private Integer retryCount;
    private Integer failedRetrySeconds;

    @Override
    public Integer getRetryCount() {
        if (this.retryCount == null) {
            return 5;
        }
        return this.retryCount;
    }

    public void setRetryCount(Integer retryCount) {
        this.retryCount = retryCount;
    }

    @Override
    public Integer getFailedRetrySeconds() {
        if (this.failedRetrySeconds == null) {
            return 600;
        }
        return this.failedRetrySeconds;
    }

    public void setFailedRetrySeconds(Integer failedRetrySeconds) {
        this.failedRetrySeconds = failedRetrySeconds;
    }

    @Override
    public Integer getSendMaxIntervalSeconds() {
        if (this.sendMaxIntervalSeconds == null) {
            return 100;
        }
        return this.sendMaxIntervalSeconds;
    }

    public void setSendMaxIntervalSeconds(Integer sendMaxIntervalSeconds) {
        this.sendMaxIntervalSeconds = sendMaxIntervalSeconds;
    }
}

