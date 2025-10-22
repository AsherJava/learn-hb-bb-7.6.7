/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nr.entity.dimension", ignoreInvalidFields=true)
public class IsolatingBaseDataConfig {
    private boolean enableIsolation;

    public boolean isEnableIsolation() {
        return this.enableIsolation;
    }

    public void setEnableIsolation(boolean enableIsolation) {
        this.enableIsolation = enableIsolation;
    }
}

