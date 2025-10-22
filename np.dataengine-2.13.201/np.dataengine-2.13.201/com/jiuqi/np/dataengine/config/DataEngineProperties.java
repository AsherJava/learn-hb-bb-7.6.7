/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.np.dataengine")
public class DataEngineProperties {
    private Boolean canModifyKey;

    public Boolean getCanModifyKey() {
        return this.canModifyKey;
    }

    public void setCanModifyKey(Boolean canModifyKey) {
        this.canModifyKey = canModifyKey;
    }
}

