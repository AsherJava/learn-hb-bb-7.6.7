/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import java.math.BigDecimal;
import java.util.List;

public class ResultitemOrderDTO {
    private String id;
    private String resultId;
    private BigDecimal score;
    private String scoreBasis;
    private BigDecimal fullScore;
    private String ruleItemId;
    private String ruleItemName;
    private String ordinal;
    private String scoreItemId;
    private String parentScoreItemId;
    private String paragraphTitle;
    private List<ResultitemOrderDTO> children;

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

    public String getOrdinal() {
        return this.ordinal;
    }

    public void setOrdinal(String ordinal) {
        this.ordinal = ordinal;
    }

    public List<ResultitemOrderDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<ResultitemOrderDTO> children) {
        this.children = children;
    }

    public String getScoreItemId() {
        return this.scoreItemId;
    }

    public void setScoreItemId(String scoreItemId) {
        this.scoreItemId = scoreItemId;
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
}

