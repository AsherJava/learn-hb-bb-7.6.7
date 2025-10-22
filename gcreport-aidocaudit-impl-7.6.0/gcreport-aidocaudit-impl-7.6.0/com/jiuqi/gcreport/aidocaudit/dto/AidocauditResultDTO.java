/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.math.BigDecimal;
import java.util.Date;

public class AidocauditResultDTO {
    private String id;
    private String mdCode;
    private String orgName;
    private String dataTime;
    private String taskId;
    private String zbCode;
    private String attachmentId;
    private String ruleId;
    private BigDecimal score;
    private Integer ruleNum;
    private Integer ruleMatchNum;
    private Integer ruleUnmatchNum;
    private Integer ruleSuspectMatchNum;
    private BigDecimal passRate;
    private Date createTime;
    private String createUser;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getOrgName() {
        return this.orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getDataTime() {
        return this.dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Integer getRuleNum() {
        return this.ruleNum;
    }

    public void setRuleNum(Integer ruleNum) {
        this.ruleNum = ruleNum;
    }

    public Integer getRuleMatchNum() {
        return this.ruleMatchNum;
    }

    public void setRuleMatchNum(Integer ruleMatchNum) {
        this.ruleMatchNum = ruleMatchNum;
    }

    public Integer getRuleUnmatchNum() {
        return this.ruleUnmatchNum;
    }

    public void setRuleUnmatchNum(Integer ruleUnmatchNum) {
        this.ruleUnmatchNum = ruleUnmatchNum;
    }

    public Integer getRuleSuspectMatchNum() {
        return this.ruleSuspectMatchNum;
    }

    public void setRuleSuspectMatchNum(Integer ruleSuspectMatchNum) {
        this.ruleSuspectMatchNum = ruleSuspectMatchNum;
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

    public BigDecimal getPassRate() {
        return this.passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }
}

