/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.env;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.gcreport.security", ignoreInvalidFields=true)
public class GcSecurityProperties {
    private Integer level = 0;

    public Integer getLevel() {
        return this.level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }
}

