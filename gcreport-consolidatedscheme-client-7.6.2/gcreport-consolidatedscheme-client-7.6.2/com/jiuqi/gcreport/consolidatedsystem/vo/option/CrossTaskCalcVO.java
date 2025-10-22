/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.consolidatedsystem.vo.option;

import java.util.List;

public class CrossTaskCalcVO {
    private List<String> ruleIds;
    private String taskId;

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

