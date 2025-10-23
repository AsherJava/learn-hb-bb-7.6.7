/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.instance.entity;

import com.jiuqi.nr.workflow2.instance.enumeration.TableDataQueryMode;
import java.util.List;

public class TableDataFilterInfo {
    private String locateUnitKey;
    private String filterTitle;
    private List<Integer> filterState;
    private TableDataQueryMode queryMode;

    public String getLocateUnitKey() {
        return this.locateUnitKey;
    }

    public void setLocateUnitKey(String locateUnitKey) {
        this.locateUnitKey = locateUnitKey;
    }

    public String getFilterTitle() {
        return this.filterTitle;
    }

    public void setFilterTitle(String filterTitle) {
        this.filterTitle = filterTitle;
    }

    public List<Integer> getFilterState() {
        return this.filterState;
    }

    public void setFilterState(List<Integer> filterState) {
        this.filterState = filterState;
    }

    public TableDataQueryMode getQueryMode() {
        return this.queryMode;
    }

    public void setQueryMode(TableDataQueryMode queryMode) {
        this.queryMode = queryMode;
    }
}

