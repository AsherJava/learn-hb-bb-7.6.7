/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.aidocaudit.dto;

import com.jiuqi.gcreport.aidocaudit.dto.ResultItemDTO;
import java.util.List;

public class CountResultItemDTO {
    int ruleNum = 0;
    int ruleMatchNum = 0;
    int ruleUnmatchNum = 0;
    int ruleSuspectMatchNum = 0;
    List<ResultItemDTO> resultItemList;

    public int getRuleNum() {
        return this.ruleNum;
    }

    public void setRuleNum(int ruleNum) {
        this.ruleNum = ruleNum;
    }

    public int getRuleMatchNum() {
        return this.ruleMatchNum;
    }

    public void setRuleMatchNum(int ruleMatchNum) {
        this.ruleMatchNum = ruleMatchNum;
    }

    public int getRuleUnmatchNum() {
        return this.ruleUnmatchNum;
    }

    public void setRuleUnmatchNum(int ruleUnmatchNum) {
        this.ruleUnmatchNum = ruleUnmatchNum;
    }

    public int getRuleSuspectMatchNum() {
        return this.ruleSuspectMatchNum;
    }

    public void setRuleSuspectMatchNum(int ruleSuspectMatchNum) {
        this.ruleSuspectMatchNum = ruleSuspectMatchNum;
    }

    public List<ResultItemDTO> getResultItemList() {
        return this.resultItemList;
    }

    public void setResultItemList(List<ResultItemDTO> resultItemList) {
        this.resultItemList = resultItemList;
    }
}

