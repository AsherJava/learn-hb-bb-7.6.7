/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fmdm.option;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(value="nvwa.organization.modify-org-code")
public class OrgCodeEditOption {
    private boolean allow;

    public boolean isAllow() {
        return this.allow;
    }

    public void setAllow(boolean allow) {
        this.allow = allow;
    }
}

