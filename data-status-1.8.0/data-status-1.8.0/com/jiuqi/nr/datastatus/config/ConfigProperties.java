/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datastatus.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nr.data.status")
public class ConfigProperties {
    private boolean enable = true;

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

