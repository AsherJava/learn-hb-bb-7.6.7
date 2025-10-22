/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class RuleItemTreeDTO {
    private String id;
    private String ruleId;
    private String scoreItemId;
    private String scoreItemName;
    private BigDecimal fullScore;
    private String parentScoreItemId;
    private String paragraphTitle;
    private String prompt;
    private String ordinal;
    private Date createTime;
    private String createUser;
    private Date updateTime;
    private String updateUser;
    private List<RuleItemTreeDTO> children;
    private Integer matchUnitNum;
    private Integer suspectMatchUnitNum;
    private Integer unMatchUnitNum;
    private BigDecimal passRate;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public String getScoreItemId() {
        return this.scoreItemId;
    }

    public void setScoreItemId(String scoreItemId) {
        this.scoreItemId = scoreItemId;
    }

    public String getScoreItemName() {
        return this.scoreItemName;
    }

    public void setScoreItemName(String scoreItemName) {
        this.scoreItemName = scoreItemName;
    }

    public BigDecimal getFullScore() {
        return this.fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public String getParentScoreItemId() {
        return this.parentScoreItemId;
    }

    public void setParentScoreItemId(String parentScoreItemId) {
        this.parentScoreItemId = parentScoreItemId;
    }

    public String getParagraphTitle() {
        return this.paragraphTitle;
    }

    public void setParagraphTitle(String paragraphTitle) {
        this.paragraphTitle = paragraphTitle;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
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

    public List<RuleItemTreeDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<RuleItemTreeDTO> children) {
        this.children = children;
    }

    public Integer getMatchUnitNum() {
        return this.matchUnitNum;
    }

    public void setMatchUnitNum(Integer matchUnitNum) {
        this.matchUnitNum = matchUnitNum;
    }

    public Integer getSuspectMatchUnitNum() {
        return this.suspectMatchUnitNum;
    }

    public void setSuspectMatchUnitNum(Integer suspectMatchUnitNum) {
        this.suspectMatchUnitNum = suspectMatchUnitNum;
    }

    public Integer getUnMatchUnitNum() {
        return this.unMatchUnitNum;
    }

    public void setUnMatchUnitNum(Integer unMatchUnitNum) {
        this.unMatchUnitNum = unMatchUnitNum;
    }

    public BigDecimal getPassRate() {
        return this.passRate;
    }

    public void setPassRate(BigDecimal passRate) {
        this.passRate = passRate;
    }
}

