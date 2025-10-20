/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.framework.parameter.model;

import java.io.Serializable;

public class ParameterHierarchyFilterItem
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String parent;
    private boolean allSub;

    public ParameterHierarchyFilterItem(String parent) {
        this.parent = parent;
    }

    public void setAllSub(boolean allSub) {
        this.allSub = allSub;
    }

    public boolean isAllSub() {
        return this.allSub;
    }

    public String getParent() {
        return this.parent;
    }
}

