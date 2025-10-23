/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

public interface ISchemeNode {
    public static final String DM_SPLIT = ":";

    public int getType();

    public String getKey();

    default public Object getData() {
        return null;
    }
}

