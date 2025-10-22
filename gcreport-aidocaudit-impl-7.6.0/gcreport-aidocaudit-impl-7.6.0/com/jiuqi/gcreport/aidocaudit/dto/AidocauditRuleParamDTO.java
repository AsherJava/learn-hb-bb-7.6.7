/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.jiuqi.gcreport.aidocaudit.dto.RuleItemTreeDTO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class AidocauditRuleParamDTO {
    private String id;
    private String ruleName;
    private Integer ruleType;
    private Integer ruleStatus;
    private String reportTemplate;
    private String ruleAttachmentId;
    private String ruleAttachmentName;
    private String ruleAttachmentType;
    private String ruleAttachmentSize;
    private String achmentZbCode;
    private String scoreTmplId;
    private Integer ruleCount;
    private BigDecimal totalScore;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private List<RuleItemTreeDTO> ruleItemList;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleName() {
        return this.ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public Integer getRuleType() {
        return this.ruleType;
    }

    public void setRuleType(Integer ruleType) {
        this.ruleType = ruleType;
    }

    public Integer getRuleStatus() {
        return this.ruleStatus;
    }

    public void setRuleStatus(Integer ruleStatus) {
        this.ruleStatus = ruleStatus;
    }

    public String getReportTemplate() {
        return this.reportTemplate;
    }

    public void setReportTemplate(String reportTemplate) {
        this.reportTemplate = reportTemplate;
    }

    public String getRuleAttachmentId() {
        return this.ruleAttachmentId;
    }

    public void setRuleAttachmentId(String ruleAttachmentId) {
        this.ruleAttachmentId = ruleAttachmentId;
    }

    public String getRuleAttachmentName() {
        return this.ruleAttachmentName;
    }

    public void setRuleAttachmentName(String ruleAttachmentName) {
        this.ruleAttachmentName = ruleAttachmentName;
    }

    public String getRuleAttachmentType() {
        return this.ruleAttachmentType;
    }

    public void setRuleAttachmentType(String ruleAttachmentType) {
        this.ruleAttachmentType = ruleAttachmentType;
    }

    public String getRuleAttachmentSize() {
        return this.ruleAttachmentSize;
    }

    public void setRuleAttachmentSize(String ruleAttachmentSize) {
        this.ruleAttachmentSize = ruleAttachmentSize;
    }

    public String getAchmentZbCode() {
        return this.achmentZbCode;
    }

    public void setAchmentZbCode(String achmentZbCode) {
        this.achmentZbCode = achmentZbCode;
    }

    public String getScoreTmplId() {
        return this.scoreTmplId;
    }

    public void setScoreTmplId(String scoreTmplId) {
        this.scoreTmplId = scoreTmplId;
    }

    public Integer getRuleCount() {
        return this.ruleCount;
    }

    public void setRuleCount(Integer ruleCount) {
        this.ruleCount = ruleCount;
    }

    public BigDecimal getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return this.updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser;
    }

    public List<RuleItemTreeDTO> getRuleItemList() {
        return this.ruleItemList;
    }

    public void setRuleItemList(List<RuleItemTreeDTO> ruleItemList) {
        this.ruleItemList = ruleItemList;
    }
}

