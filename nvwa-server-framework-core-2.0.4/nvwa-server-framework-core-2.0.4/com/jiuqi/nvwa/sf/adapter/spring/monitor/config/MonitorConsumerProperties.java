/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.monitor.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nvwa.monitor.quene.consumer")
public class MonitorConsumerProperties {
    private Integer corePoolSize;
    private Integer maximumPoolSize;
    private String waitStrategyClassName;
    private Integer ringBufferSize;

    public Integer getCorePoolSize() {
        return this.corePoolSize;
    }

    public Integer getMaximumPoolSize() {
        return this.maximumPoolSize;
    }

    public void setCorePoolSize(Integer corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public void setMaximumPoolSize(Integer maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public String getWaitStrategyClassName() {
        return this.waitStrategyClassName;
    }

    public void setWaitStrategyClassName(String waitStrategyClassName) {
        this.waitStrategyClassName = waitStrategyClassName;
    }

    public Integer getRingBufferSize() {
        return this.ringBufferSize;
    }

    public void setRingBufferSize(Integer ringBufferSize) {
        this.ringBufferSize = ringBufferSize;
    }
}

