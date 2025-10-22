/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.integritycheck.common;

import java.io.Serializable;

public class ErrorDesShowInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String updateTime;
    private String updater;
    private String description;

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdater() {
        return this.updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

