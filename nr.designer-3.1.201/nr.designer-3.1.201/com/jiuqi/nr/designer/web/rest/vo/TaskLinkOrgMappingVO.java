/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.common.TaskLinkExpressionType
 *  com.jiuqi.nr.definition.common.TaskLinkMatchingType
 *  com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule
 */
package com.jiuqi.nr.designer.web.rest.vo;

import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingRule;
import com.jiuqi.nr.designer.web.rest.vo.TaskLinkDimMappingVO;

public class TaskLinkOrgMappingVO
extends TaskLinkOrgMappingRule {
    private Integer matchingTypeValue;
    private Integer expressionTypeValue;
    private TaskLinkDimMappingVO dimMapping;

    public TaskLinkOrgMappingVO() {
    }

    public TaskLinkOrgMappingVO(TaskLinkOrgMappingRule rule) {
        this.setTaskLinkKey(rule.getTaskLinkKey());
        this.setSourceEntity(rule.getSourceEntity());
        this.setTargetEntity(rule.getTargetEntity());
        this.setMatchingType(rule.getMatchingType());
        this.matchingTypeValue = rule.getMatchingType().getValue();
        this.setTargetFormula(rule.getTargetFormula());
        this.setSourceFormula(rule.getSourceFormula());
        this.setExpressionType(rule.getExpressionType());
        this.expressionTypeValue = rule.getExpressionType().getValue();
        this.setOrder(rule.getOrder());
    }

    public TaskLinkOrgMappingRule toMappingRule() {
        TaskLinkOrgMappingRule rule = new TaskLinkOrgMappingRule();
        rule.setTaskLinkKey(this.getTaskLinkKey());
        rule.setSourceEntity(this.getSourceEntity());
        rule.setTargetEntity(this.getTargetEntity());
        rule.setMatchingType(TaskLinkMatchingType.forValue((int)this.matchingTypeValue));
        rule.setTargetFormula(this.getTargetFormula());
        rule.setSourceFormula(this.getSourceFormula());
        rule.setExpressionType(TaskLinkExpressionType.forValue((int)this.expressionTypeValue));
        rule.setOrder(this.getOrder());
        return rule;
    }

    public Integer getMatchingTypeValue() {
        return this.matchingTypeValue;
    }

    public void setMatchingTypeValue(Integer matchingTypeValue) {
        this.matchingTypeValue = matchingTypeValue;
    }

    public Integer getExpressionTypeValue() {
        return this.expressionTypeValue;
    }

    public void setExpressionTypeValue(Integer expressionTypeValue) {
        this.expressionTypeValue = expressionTypeValue;
    }
}

