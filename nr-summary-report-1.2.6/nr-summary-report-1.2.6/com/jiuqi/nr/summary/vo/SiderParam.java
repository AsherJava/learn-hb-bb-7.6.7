/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.model.soulution.TargetDimensionRange;
import java.util.List;

public class SiderParam {
    private String treeId;
    private String taskId;
    private List<String> sumSoluGroups;
    private List<String> sumSolus;
    private List<String> sumReports;
    private String targetDimensionKey;
    private TargetDimensionRange targetDimensionRange;
    private List<String> targetDimensionValues;
    private String targetDimensionFilter;

    public String getTreeId() {
        return this.treeId;
    }

    public void setTreeId(String treeId) {
        this.treeId = treeId;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
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

    public TargetDimensionRange getTargetDimensionRange() {
        return this.targetDimensionRange;
    }

    public void setTargetDimensionRange(TargetDimensionRange targetDimensionRange) {
        this.targetDimensionRange = targetDimensionRange;
    }

    public List<String> getTargetDimensionValues() {
        return this.targetDimensionValues;
    }

    public void setTargetDimensionValues(List<String> targetDimensionValues) {
        this.targetDimensionValues = targetDimensionValues;
    }

    public String getTargetDimensionFilter() {
        return this.targetDimensionFilter;
    }

    public void setTargetDimensionFilter(String targetDimensionFilter) {
        this.targetDimensionFilter = targetDimensionFilter;
    }

    public String getTargetDimensionKey() {
        return this.targetDimensionKey;
    }

    public void setTargetDimensionKey(String targetDimensionKey) {
        this.targetDimensionKey = targetDimensionKey;
    }
}

