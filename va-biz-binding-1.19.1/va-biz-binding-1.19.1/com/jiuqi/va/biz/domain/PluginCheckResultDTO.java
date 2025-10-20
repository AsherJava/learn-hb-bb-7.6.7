/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.biz.domain;

import com.jiuqi.va.biz.domain.PluginCheckType;

public class PluginCheckResultDTO {
    private PluginCheckType type;
    private String message;
    private String objectpath;

    public PluginCheckType getType() {
        return this.type;
    }

    public void setType(PluginCheckType type) {
        this.type = type;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getObjectpath() {
        return this.objectpath;
    }

    public void setObjectpath(String objectpath) {
        this.objectpath = objectpath;
    }
}

