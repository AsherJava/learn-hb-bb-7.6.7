/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.office.excel2.filter;

import com.jiuqi.np.office.excel2.filter.FilterColCondition;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FilterRegionCondition
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String region;
    private Map<String, List<FilterColCondition>> filterCols = new LinkedHashMap<String, List<FilterColCondition>>();
    private String sortCol;
    private boolean isAsc;

    public String getRegion() {
        return this.region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Map<String, List<FilterColCondition>> getFilterCols() {
        return this.filterCols;
    }

    public void setFilterCols(Map<String, List<FilterColCondition>> filterCols) {
        this.filterCols = filterCols;
    }

    public void addColFilterCondition(String colName, List<FilterColCondition> oneCol) {
        this.filterCols.put(colName, oneCol);
    }

    public String getSortCol() {
        return this.sortCol;
    }

    public void setSortCol(String sortCol) {
        this.sortCol = sortCol;
    }

    public boolean getIsAsc() {
        return this.isAsc;
    }

    public void setIsAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }
}

