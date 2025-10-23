/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.zbquery.model;

import com.jiuqi.nr.zbquery.model.CustomFilter;
import com.jiuqi.nr.zbquery.model.FilterType;
import java.util.List;

public class FilterField {
    private String fullName;
    private List<FilterType> filterTypes;
    private CustomFilter customFilter;

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<FilterType> getFilterTypes() {
        return this.filterTypes;
    }

    public void setFilterTypes(List<FilterType> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public CustomFilter getCustomFilter() {
        return this.customFilter;
    }

    public void setCustomFilter(CustomFilter customFilter) {
        this.customFilter = customFilter;
    }
}

