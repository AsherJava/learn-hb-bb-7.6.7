/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.tree;

public interface TreeData {
    default public String getKey() {
        return null;
    }

    default public String getParent() {
        return null;
    }

    public String getTitle();
}

