/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bizmeta.common.utils;

public enum MetaItemServer {
    BILLLIST("billlist"),
    WORKFLOW("workflow"),
    BILL("bill");

    private String metaType;

    private MetaItemServer(String metaType) {
        this.metaType = metaType;
    }

    public String getMetaType() {
        return this.metaType;
    }
}

