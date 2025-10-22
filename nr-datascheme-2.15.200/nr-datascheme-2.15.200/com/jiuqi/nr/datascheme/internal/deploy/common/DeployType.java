/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.internal.deploy.common;

public enum DeployType {
    NONE(0),
    ADD(1),
    UPDATE(2),
    UPDATE_NODPLOY(3),
    DELETE(4);

    private final int level;

    private DeployType(int level) {
        this.level = level;
    }

    public int getLevel() {
        return this.level;
    }
}

