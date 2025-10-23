/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.settings.entity;

import com.jiuqi.nr.workflow2.engine.core.settings.entity.ManualTerminationConfig;

public class ManualTerminationConfigImpl
implements ManualTerminationConfig {
    private boolean enable;
    private String role;

    @Override
    public boolean isEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

