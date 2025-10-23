/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.summary.vo.ListNode;
import java.util.List;

public class TaskParamVO {
    private List<ListNode> relatedTasks;
    private List<ListNode> targetDims;
    private boolean targetDimDisabled = true;
    private PeriodType periodType;
    private List<ListNode> sceneDims;

    public List<ListNode> getRelatedTasks() {
        return this.relatedTasks;
    }

    public void setRelatedTasks(List<ListNode> relatedTasks) {
        this.relatedTasks = relatedTasks;
    }

    public List<ListNode> getTargetDims() {
        return this.targetDims;
    }

    public void setTargetDims(List<ListNode> targetDims) {
        this.targetDims = targetDims;
    }

    public boolean isTargetDimDisabled() {
        return this.targetDimDisabled;
    }

    public void setTargetDimDisabled(boolean targetDimDisabled) {
        this.targetDimDisabled = targetDimDisabled;
    }

    public PeriodType getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(PeriodType periodType) {
        this.periodType = periodType;
    }

    public List<ListNode> getSceneDims() {
        return this.sceneDims;
    }

    public void setSceneDims(List<ListNode> sceneDims) {
        this.sceneDims = sceneDims;
    }
}

