/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.jiuqi.gcreport.aidocaudit.enums.ReScoreOptionEnum;
import com.jiuqi.gcreport.aidocaudit.enums.subUnitSelectEnum;
import java.util.List;

public class AuditParamDTO {
    private String taskId;
    private List<String> orgIds;
    private Boolean isAllOrg;
    private String dataTime;
    private List<String> ruleIds;
    private String ruleId;
    private subUnitSelectEnum subUnitSelect;
    private Integer record;
    private ReScoreOptionEnum reScoreOption;
    private Integer currentPage = 1;
    private Integer pageSize = 50;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getOrgIds() {
        return this.orgIds;
    }

    public void setOrgIds(List<String> orgIds) {
        this.orgIds = orgIds;
    }

    public Boolean getIsAllOrg() {
        return this.isAllOrg;
    }

    public void setIsAllOrg(Boolean allOrg) {
        this.isAllOrg = allOrg;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public List<String> getRuleIds() {
        return this.ruleIds;
    }

    public void setRuleIds(List<String> ruleIds) {
        this.ruleIds = ruleIds;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public subUnitSelectEnum getSubUnitSelect() {
        return this.subUnitSelect;
    }

    public void setSubUnitSelect(subUnitSelectEnum subUnitSelect) {
        this.subUnitSelect = subUnitSelect;
    }

    public Integer getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getRecord() {
        return this.record;
    }

    public void setRecord(Integer record) {
        this.record = record;
    }

    public ReScoreOptionEnum getReScoreOption() {
        return this.reScoreOption;
    }

    public void setReScoreOption(ReScoreOptionEnum reScoreOption) {
        this.reScoreOption = reScoreOption;
    }
}

