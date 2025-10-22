/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.math.BigDecimal;

public class ResultDetailDTO {
    private String resultItemId;
    private String mdCode;
    private String orgTitle;
    private BigDecimal score;
    private BigDecimal fullScore;
    private String scoreBasis;
    private String ruleItemId;
    private String ruleItemName;
    private String attachmentId;
    private String resultId;

    public String getResultItemId() {
        return this.resultItemId;
    }

    public void setResultItemId(String resultItemId) {
        this.resultItemId = resultItemId;
    }

    public String getMdCode() {
        return this.mdCode;
    }

    public void setMdCode(String mdCode) {
        this.mdCode = mdCode;
    }

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public BigDecimal getFullScore() {
        return this.fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public String getScoreBasis() {
        return this.scoreBasis;
    }

    public void setScoreBasis(String scoreBasis) {
        this.scoreBasis = scoreBasis;
    }

    public String getRuleItemId() {
        return this.ruleItemId;
    }

    public void setRuleItemId(String ruleItemId) {
        this.ruleItemId = ruleItemId;
    }

    public String getRuleItemName() {
        return this.ruleItemName;
    }

    public void setRuleItemName(String ruleItemName) {
        this.ruleItemName = ruleItemName;
    }

    public String getAttachmentId() {
        return this.attachmentId;
    }

    public void setAttachmentId(String attachmentId) {
        this.attachmentId = attachmentId;
    }

    public String getResultId() {
        return this.resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }
}

