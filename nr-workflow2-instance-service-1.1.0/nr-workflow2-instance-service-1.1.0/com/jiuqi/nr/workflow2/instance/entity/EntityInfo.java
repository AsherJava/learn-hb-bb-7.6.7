/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.entity;

import java.util.List;

public class EntityInfo {
    private boolean isCheckAll;
    private List<String> valueList;

    public boolean isCheckAll() {
        return this.isCheckAll;
    }

    public void setIsCheckAll(boolean isCheckAll) {
        this.isCheckAll = isCheckAll;
    }

    public List<String> getValueList() {
        return this.valueList;
    }

    public void setValueList(List<String> valueList) {
        this.valueList = valueList;
    }
}

