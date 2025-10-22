/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import java.util.HashMap;
import java.util.Map;
import org.springframework.util.StringUtils;

public class TaskLinkEntityMappingRule
extends TaskLinkOrgMappingRule {
    private Map<String, String> assignDimMap;

    public TaskLinkEntityMappingRule() {
    }

    public TaskLinkEntityMappingRule(TaskLinkOrgMappingRule orgRule, String dimRule) {
        this.setTaskLinkKey(orgRule.getTaskLinkKey());
        this.setSourceEntity(orgRule.getSourceEntity());
        this.setTargetEntity(orgRule.getTargetEntity());
        this.setMatchingType(orgRule.getMatchingType());
        this.setTargetFormula(orgRule.getTargetFormula());
        this.setSourceFormula(orgRule.getSourceFormula());
        this.setExpressionType(orgRule.getExpressionType());
        this.setOrder(orgRule.getOrder());
    }

    private Map<String, String> getDimMapping(String dimRule) {
        HashMap<String, String> dimMap = new HashMap<String, String>();
        if (!StringUtils.hasText(dimRule)) {
            return dimMap;
        }
        String[] dimRuleObjs = dimRule.split("/");
        for (int i = 0; i < dimRuleObjs.length; ++i) {
            String dimRuleObj = dimRuleObjs[i];
            String[] dimAndDimData = dimRuleObj.split(";");
            dimMap.put(dimAndDimData[0], dimAndDimData[1]);
        }
        return dimMap;
    }
}

