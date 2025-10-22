/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.util.List;

public class AidocauditResultDetailParamDTO {
    private String taskId;
    private String ruleId;
    private String ruleItemId;
    private List<String> orgIds;
    private String dataTime;
    private Integer resultType;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleItemId() {
        return this.ruleItemId;
    }

    public void setRuleItemId(String ruleItemId) {
        this.ruleItemId = ruleItemId;
    }

    public List<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public Integer getResultType() {
        return this.resultType;
    }

    public void setResultType(Integer resultType) {
        this.resultType = resultType;
    }
}

