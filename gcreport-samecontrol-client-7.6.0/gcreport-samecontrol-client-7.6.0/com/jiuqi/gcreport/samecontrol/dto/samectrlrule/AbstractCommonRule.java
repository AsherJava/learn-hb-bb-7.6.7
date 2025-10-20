/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  javax.validation.constraints.NotNull
 */
package com.jiuqi.gcreport.samecontrol.dto.samectrlrule;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jiuqi.gcreport.samecontrol.enums.SameCtrlRuleTypeEnum;
import java.util.List;
import javax.validation.constraints.NotNull;

public abstract class AbstractCommonRule {
    private String id;
    private String title;
    private String reportSystem;
    private SameCtrlRuleTypeEnum ruleType;
    private String dataType;
    private String ruleCode;
    private String ruleCondition;
    private String businessTypeCode;
    private Integer sortOrder;
    private Boolean leafFlag;
    @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a")
    private @NotNull(message="\u7236\u7ea7\u8282\u70b9\u4e0d\u80fd\u4e3a\u7a7a") String parentId;
    private String taskId;
    private Boolean startFlag;

    @JsonIgnore
    public List<String> getSrcDebitSubjectCodeList() {
        throw new UnsupportedOperationException();
    }

    @JsonIgnore
    public List<String> getSrcCreditSubjectCodeList() {
        throw new UnsupportedOperationException();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReportSystem() {
        return this.reportSystem;
    }

    public void setReportSystem(String reportSystem) {
        this.reportSystem = reportSystem;
    }

    public SameCtrlRuleTypeEnum getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(SameCtrlRuleTypeEnum ruleType) {
        this.ruleType = ruleType;
    }

    public String getRuleCode() {
        return this.ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public String getRuleCondition() {
        return this.ruleCondition;
    }

    public void setRuleCondition(String ruleCondition) {
        this.ruleCondition = ruleCondition;
    }

    public String getBusinessTypeCode() {
        return this.businessTypeCode;
    }

    public void setBusinessTypeCode(String businessTypeCode) {
        this.businessTypeCode = businessTypeCode;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getLeafFlag() {
        return this.leafFlag;
    }

    public void setLeafFlag(Boolean leafFlag) {
        this.leafFlag = leafFlag;
    }

    public Integer getSortOrder() {
        return this.sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public Boolean getStartFlag() {
        return this.startFlag;
    }

    public void setStartFlag(Boolean startFlag) {
        this.startFlag = startFlag;
    }
}

