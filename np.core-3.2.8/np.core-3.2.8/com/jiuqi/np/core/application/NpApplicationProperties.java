/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.np.app", ignoreInvalidFields=true, ignoreUnknownFields=true)
public class NpApplicationProperties {
    private String name = "applicationName";

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

