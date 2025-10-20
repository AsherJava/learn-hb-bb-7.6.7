/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.encrypt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="lcdp.encrypt", ignoreInvalidFields=true, ignoreUnknownFields=true)
public class LcdpEncryptProperties {
    private boolean enable = false;

    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}

