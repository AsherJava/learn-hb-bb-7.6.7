/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuiqi.nr.unit.treebase.web.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDimConfig {
    private boolean dimGroupConfig;
    private boolean dimFilterConfig;

    @JsonProperty(value="hasDimGroupConfig")
    public boolean isDimGroupConfig() {
        return this.dimGroupConfig;
    }

    public void setDimGroupConfig(boolean dimGroupConfig) {
        this.dimGroupConfig = dimGroupConfig;
    }

    @JsonProperty(value="hasDimFilterConfig")
    public boolean isDimFilterConfig() {
        return this.dimFilterConfig;
    }

    public void setDimFilterConfig(boolean dimFilterConfig) {
        this.dimFilterConfig = dimFilterConfig;
    }
}

