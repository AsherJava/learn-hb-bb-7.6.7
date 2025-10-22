/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.analysis.define;

import com.jiuqi.nr.data.engine.analysis.define.GroupingConfig;
import com.jiuqi.nr.data.engine.analysis.define.OrderField;
import java.util.ArrayList;
import java.util.List;

public class FloatRegionConfig {
    private String regionCode;
    private int regionRowIndex;
    private String srcMainDimFilter;
    private String rowFilter;
    private List<OrderField> orderFields = new ArrayList<OrderField>();
    private int topN = -1;
    private GroupingConfig groupingConfig;

    public FloatRegionConfig(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getRegionCode() {
        return this.regionCode;
    }

    public void setRegionCode(String regionCode) {
        this.regionCode = regionCode;
    }

    public String getSrcMainDimFilter() {
        return this.srcMainDimFilter;
    }

    public String getRowFilter() {
        return this.rowFilter;
    }

    public List<OrderField> getOrderFields() {
        return this.orderFields;
    }

    public int getTopN() {
        return this.topN;
    }

    public GroupingConfig getGroupingConfig() {
        return this.groupingConfig;
    }

    public void setSrcMainDimFilter(String srcMainDimFilter) {
        this.srcMainDimFilter = srcMainDimFilter;
    }

    public void setRowFilter(String rowFilter) {
        this.rowFilter = rowFilter;
    }

    public void setTopN(int topN) {
        this.topN = topN;
    }

    public void setGroupingConfig(GroupingConfig groupingConfig) {
        this.groupingConfig = groupingConfig;
    }

    public int getRegionRowIndex() {
        return this.regionRowIndex;
    }

    public void setRegionRowIndex(int regionRowIndex) {
        this.regionRowIndex = regionRowIndex;
    }

    public String toString() {
        return "[srcMainDimFilter=" + this.srcMainDimFilter + ", rowFilter=" + this.rowFilter + ", orderFields=" + this.orderFields + ", topN=" + this.topN + ", groupingConfig=" + this.groupingConfig + "]";
    }
}

