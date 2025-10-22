/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.util.List;

public class SelectorParam {
    private String selectorKey;
    private List<String> selectList;
    private Long selectCount;

    public String getSelectorKey() {
        return this.selectorKey;
    }

    public void setSelectorKey(String selectorKey) {
        this.selectorKey = selectorKey;
    }

    public List<String> getSelectList() {
        return this.selectList;
    }

    public void setSelectList(List<String> selectList) {
        this.selectList = selectList;
    }

    public Long getSelectCount() {
        return this.selectCount;
    }

    public void setSelectCount(Long selectCount) {
        this.selectCount = selectCount;
    }
}

