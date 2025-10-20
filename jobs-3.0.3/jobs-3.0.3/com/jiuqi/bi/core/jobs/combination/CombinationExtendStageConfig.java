/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.core.jobs.combination;

import org.json.JSONObject;

public class CombinationExtendStageConfig {
    private String type;
    private String config;

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfig() {
        return this.config;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public CombinationExtendStageConfig fromJson(JSONObject object) {
        this.type = object.optString("type");
        this.config = object.optString("config");
        return this;
    }
}

