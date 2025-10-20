/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.common.intf;

import com.jiuqi.bde.common.constant.MatchRuleEnum;
import java.util.List;

public class ConditionMatchRule {
    private MatchRuleEnum matchRule;
    private List<String> subjectCodes;
    private String tbName;

    public ConditionMatchRule(MatchRuleEnum matchRule, List<String> subjectCodes) {
        this.matchRule = matchRule;
        this.subjectCodes = subjectCodes;
        this.tbName = "BDE_TEMP_MAINCODE";
    }

    public MatchRuleEnum getMatchRule() {
        return this.matchRule;
    }

    public void setMatchRule(MatchRuleEnum matchRule) {
        this.matchRule = matchRule;
    }

    public List<String> getSubjectCodes() {
        return this.subjectCodes;
    }

    public void setSubjectCodes(List<String> subjectCodes) {
        this.subjectCodes = subjectCodes;
    }

    public String getTbName() {
        return this.tbName;
    }

    public void setTbName(String tbName) {
        this.tbName = tbName;
    }
}

