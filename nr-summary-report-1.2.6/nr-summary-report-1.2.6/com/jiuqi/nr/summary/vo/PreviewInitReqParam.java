/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import java.util.List;

public class PreviewInitReqParam {
    private String menuId;
    private List<String> sumSoluGroups;
    private List<String> sumSolus;
    private List<String> sumReports;

    public String getMenuId() {
        return this.menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

    public List<String> getSumSoluGroups() {
        return this.sumSoluGroups;
    }

    public void setSumSoluGroups(List<String> sumSoluGroups) {
        this.sumSoluGroups = sumSoluGroups;
    }

    public List<String> getSumSolus() {
        return this.sumSolus;
    }

    public void setSumSolus(List<String> sumSolus) {
        this.sumSolus = sumSolus;
    }

    public List<String> getSumReports() {
        return this.sumReports;
    }

    public void setSumReports(List<String> sumReports) {
        this.sumReports = sumReports;
    }
}

