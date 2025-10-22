/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.util.List;

public class ResultItemDTO {
    private List<ResultItemDTO> children;
    @JsonProperty(value="full_score")
    private BigDecimal fullScore;
    private String prompt;
    private BigDecimal score;
    @JsonProperty(value="score_basis")
    private String scoreBasis;
    @JsonProperty(value="score_item_id")
    private String scoreItemId;
    @JsonProperty(value="score_item_name")
    private String scoreItemName;

    public List<ResultItemDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<ResultItemDTO> children) {
        this.children = children;
    }

    public BigDecimal getFullScore() {
        return this.fullScore;
    }

    public void setFullScore(BigDecimal fullScore) {
        this.fullScore = fullScore;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
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
}

