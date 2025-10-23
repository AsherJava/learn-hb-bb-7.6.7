/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.bean.config;

import java.util.List;

public class UpdateWay {
    private boolean create;
    private boolean update;
    private List<String> ignoreAttribute;

    public boolean isCreate() {
        return this.create;
    }

    public void setCreate(boolean create) {
        this.create = create;
    }

    public boolean isUpdate() {
        return this.update;
    }

    public void setUpdate(boolean update) {
        this.update = update;
    }

    public List<String> getIgnoreAttribute() {
        return this.ignoreAttribute;
    }

    public void setIgnoreAttribute(List<String> ignoreAttribute) {
        this.ignoreAttribute = ignoreAttribute;
    }
}

