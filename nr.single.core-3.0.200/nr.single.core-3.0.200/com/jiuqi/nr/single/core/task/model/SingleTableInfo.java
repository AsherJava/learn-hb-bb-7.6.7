/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.single.core.task.model;

import com.jiuqi.nr.single.core.task.model.SingleRegionInfo;
import java.util.ArrayList;
import java.util.List;

public class SingleTableInfo {
    private String tableFlag;
    private String tableTitle;
    private String condition;
    private SingleRegionInfo fixRegion;
    private List<SingleRegionInfo> childRegions;

    public String getTableFlag() {
        return this.tableFlag;
    }

    public void setTableFlag(String tableFlag) {
        this.tableFlag = tableFlag;
    }

    public String getTableTitle() {
        return this.tableTitle;
    }

    public void setTableTitle(String tableTitle) {
        this.tableTitle = tableTitle;
    }

    public SingleRegionInfo getFixRegion() {
        if (this.fixRegion == null) {
            this.fixRegion = new SingleRegionInfo();
        }
        return this.fixRegion;
    }

    public void setFixRegion(SingleRegionInfo fixRegion) {
        this.fixRegion = fixRegion;
    }

    public List<SingleRegionInfo> getChildRegions() {
        if (this.childRegions == null) {
            this.childRegions = new ArrayList<SingleRegionInfo>();
        }
        return this.childRegions;
    }

    public void setChildRegions(List<SingleRegionInfo> childRegions) {
        this.childRegions = childRegions;
    }

    public String getCondition() {
        return this.condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }
}

