/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.filter.scheme;

import com.jiuqi.nr.unit.uselector.filter.scheme.FilterSchemeInfo;
import java.util.List;

public class FilterSchemeTableData {
    private boolean canShared;
    private List<FilterSchemeInfo> filterSchemes;

    public boolean isCanShared() {
        return this.canShared;
    }

    public void setCanShared(boolean canShared) {
        this.canShared = canShared;
    }

    public List<FilterSchemeInfo> getFilterSchemes() {
        return this.filterSchemes;
    }

    public void setFilterSchemes(List<FilterSchemeInfo> filterSchemes) {
        this.filterSchemes = filterSchemes;
    }
}

