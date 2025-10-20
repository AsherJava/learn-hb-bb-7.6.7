/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.monitor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MonitorConfig {
    @Value(value="${biz.monitor.query.period:1}")
    private int period;
    @Value(value="${biz.monitor.query.persistence:15}")
    private int persistence;
    @Value(value="${biz.monitor.query.enabled:false}")
    private boolean enabled;
    @Value(value="${biz.monitor.query.threshold:5000}")
    private int threshold;

    public int getPeriod() {
        return this.period;
    }

    public int getPersistence() {
        return this.persistence;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public int getThreshold() {
        return this.threshold;
    }
}

