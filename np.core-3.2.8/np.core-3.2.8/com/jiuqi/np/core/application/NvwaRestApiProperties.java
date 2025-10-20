/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.application;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nvwa.restapi", ignoreInvalidFields=true, ignoreUnknownFields=true)
public class NvwaRestApiProperties {
    private boolean permissions = false;

    public boolean isPermissions() {
        return this.permissions;
    }

    public void setPermissions(boolean permissions) {
        this.permissions = permissions;
    }
}

