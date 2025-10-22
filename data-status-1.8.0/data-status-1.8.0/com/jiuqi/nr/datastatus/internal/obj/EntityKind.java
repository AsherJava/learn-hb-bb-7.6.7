/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datastatus.internal.obj;

public enum EntityKind {
    PERIOD_ENTITY_KIND("PERIOD_ENTITY_KIND"),
    NORMAL_ENTITY_KIND("NORMAL_ENTITY_KIND"),
    ADJUST_ENTITY_KIND("ADJUST_ENTITY_KIND");

    private final String kind;

    private EntityKind(String kind) {
        this.kind = kind;
    }

    public String getKind() {
        return this.kind;
    }
}

