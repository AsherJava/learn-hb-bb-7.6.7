/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.enumcheck.adapter.provider;

import java.util.List;

public class EnumConfig {
    private String filterFormula;
    private List<String> selectEnums;

    public String getFilterFormula() {
        return this.filterFormula;
    }

    public void setFilterFormula(String filterFormula) {
        this.filterFormula = filterFormula;
    }

    public List<String> getSelectEnums() {
        return this.selectEnums;
    }

    public void setSelectEnums(List<String> selectEnums) {
        this.selectEnums = selectEnums;
    }
}

