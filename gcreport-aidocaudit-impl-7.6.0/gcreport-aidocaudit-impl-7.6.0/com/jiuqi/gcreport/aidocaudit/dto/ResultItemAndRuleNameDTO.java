/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.math.BigDecimal;

public class ResultItemAndRuleNameDTO {
    private String id;
    private String resultId;
    private BigDecimal score;
    private String scoreBasis;
    private BigDecimal fullScore;
    private String ruleItemId;
    private String ruleItemName;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResultId() {
        return this.resultId;
    }

    public void setResultId(String resultId) {
        this.resultId = resultId;
    }

    public BigDecimal getScore() {
        return this.score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getScoreBasis() {
        return this.scoreBasis;
    }

    public void setScoreBasis(String scoreBasis) {
        this.scoreBasis = scoreBasis;
    }

    public BigDecimal getFullScore() {
        return this.fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
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
}

