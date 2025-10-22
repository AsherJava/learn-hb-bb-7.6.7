/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.service.impl;

public class ViewOperator {
    private String entityKey;
    private String viewKey;
    private boolean contain = false;

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public String getViewKey() {
        return this.viewKey;
    }

    public void setViewKey(String viewKey) {
        this.viewKey = viewKey;
    }

    public boolean isContain() {
        return this.contain;
    }

    public void setContain(boolean contain) {
        this.contain = contain;
    }

    public ViewOperator(String entityKey, String viewKey, boolean contain) {
        this.entityKey = entityKey;
        this.viewKey = viewKey;
        this.contain = contain;
    }

    public ViewOperator() {
    }
}

