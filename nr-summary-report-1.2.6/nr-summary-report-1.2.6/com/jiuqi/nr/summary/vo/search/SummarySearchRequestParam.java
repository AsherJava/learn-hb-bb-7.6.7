/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo.search;

public class SummarySearchRequestParam {
    private boolean withGroup;
    private boolean withSolution;
    private boolean withReport = true;
    private String keywords;

    public boolean isWithGroup() {
        return this.withGroup;
    }

    public void setWithGroup(boolean withGroup) {
        this.withGroup = withGroup;
    }

    public boolean isWithSolution() {
        return this.withSolution;
    }

    public void setWithSolution(boolean withSolution) {
        this.withSolution = withSolution;
    }

    public boolean isWithReport() {
        return this.withReport;
    }

    public void setWithReport(boolean withReport) {
        this.withReport = withReport;
    }

    public String getKeywords() {
        return this.keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}

