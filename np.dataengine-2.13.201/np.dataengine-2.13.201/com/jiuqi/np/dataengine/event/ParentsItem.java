/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.event;

public class ParentsItem {
    private String parentsValue;
    private boolean updateChildren;

    public ParentsItem(String parentsValue, boolean updateChildren) {
        this.parentsValue = parentsValue;
        this.updateChildren = updateChildren;
    }

    public String getParentsValue() {
        return this.parentsValue;
    }

    public boolean isUpdateChildren() {
        return this.updateChildren;
    }
}

