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

public class MQRuleItemResultDTO {
    @JsonProperty(value="score_item_id")
    private String scoreItemId;
    @JsonProperty(value="score_item_name")
    private String scoreItemName;
    @JsonProperty(value="full_score")
    private BigDecimal fullScore;
    @JsonProperty(value="parent_id")
    private String parentId;
    private String prompt;
    private List<MQRuleItemResultDTO> children;

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

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public List<MQRuleItemResultDTO> getChildren() {
        return this.children;
    }

    public void setChildren(List<MQRuleItemResultDTO> children) {
        this.children = children;
    }
}

