/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import java.util.List;

public class BatchDeleteVO {
    private List<String> sumSolutionGroupKeys;
    private List<String> sumSolutionKeys;
    private List<String> sumReportKeys;

    public List<String> getSumSolutionGroupKeys() {
        return this.sumSolutionGroupKeys;
    }

    public void setSumSolutionGroupKeys(List<String> sumSolutionGroupKeys) {
        this.sumSolutionGroupKeys = sumSolutionGroupKeys;
    }

    public List<String> getSumSolutionKeys() {
        return this.sumSolutionKeys;
    }

    public void setSumSolutionKeys(List<String> sumSolutionKeys) {
        this.sumSolutionKeys = sumSolutionKeys;
    }

    public List<String> getSumReportKeys() {
        return this.sumReportKeys;
    }

    public void setSumReportKeys(List<String> sumReportKeys) {
        this.sumReportKeys = sumReportKeys;
    }
}

