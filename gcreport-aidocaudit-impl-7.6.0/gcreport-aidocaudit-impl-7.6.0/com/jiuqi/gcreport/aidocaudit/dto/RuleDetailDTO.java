/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.jiuqi.gcreport.aidocaudit.dto.RuleItemTreeDTO;
import java.math.BigDecimal;
import java.util.List;

public class RuleDetailDTO {
    private String ruleId;
    private Integer groupCount;
    private Integer itemCount;
    private BigDecimal totalScore;
    private List<RuleItemTreeDTO> ruleItemList;

    public String getRuleId() {
        return this.ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Integer getGroupCount() {
        return this.groupCount;
    }

    public void setGroupCount(Integer groupCount) {
        this.groupCount = groupCount;
    }

    public Integer getItemCount() {
        return this.itemCount;
    }

    public void setItemCount(Integer itemCount) {
        this.itemCount = itemCount;
    }

    public BigDecimal getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public List<RuleItemTreeDTO> getRuleItemList() {
        return this.ruleItemList;
    }

    public void setRuleItemList(List<RuleItemTreeDTO> ruleItemList) {
        this.ruleItemList = ruleItemList;
    }
}

