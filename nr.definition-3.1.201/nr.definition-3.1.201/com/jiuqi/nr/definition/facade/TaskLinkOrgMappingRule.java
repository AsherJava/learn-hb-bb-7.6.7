/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.facade;

import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.DesignTaskLinkOrgMappingDefine;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingDefine;
import com.jiuqi.nr.definition.internal.impl.DesignTaskLinkOrgMappingDefineImpl;
import com.jiuqi.nr.definition.internal.impl.TaskLinkOrgMappingDefineImpl;

public class TaskLinkOrgMappingRule {
    private String taskLinkKey;
    private String sourceEntity;
    private String targetEntity;
    private TaskLinkMatchingType matchingType;
    private String targetFormula;
    private String sourceFormula;
    private TaskLinkExpressionType expressionType;
    private String order;

    public TaskLinkOrgMappingRule() {
    }

    public TaskLinkOrgMappingRule(DesignTaskLinkOrgMappingDefine define) {
        if (define != null) {
            this.taskLinkKey = define.getTaskLinkKey();
            this.sourceEntity = define.getSourceEntity();
            this.targetEntity = define.getTargetEntity();
            this.matchingType = define.getMatchingType();
            this.targetFormula = define.getTargetFormula();
            this.sourceFormula = define.getSourceFormula();
            this.expressionType = define.getExpressionType();
            this.order = define.getOrder();
        }
    }

    public TaskLinkOrgMappingRule(TaskLinkOrgMappingDefine define) {
        if (define != null) {
            this.taskLinkKey = define.getTaskLinkKey();
            this.sourceEntity = define.getSourceEntity();
            this.targetEntity = define.getTargetEntity();
            this.matchingType = define.getMatchingType();
            this.targetFormula = define.getTargetFormula();
            this.sourceFormula = define.getSourceFormula();
            this.expressionType = define.getExpressionType();
            this.order = define.getOrder();
        }
    }

    public String getTaskLinkKey() {
        return this.taskLinkKey;
    }

    public void setTaskLinkKey(String taskLinkKey) {
        this.taskLinkKey = taskLinkKey;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public TaskLinkMatchingType getMatchingType() {
        return this.matchingType;
    }

    public void setMatchingType(TaskLinkMatchingType matchingType) {
        this.matchingType = matchingType;
    }

    public String getTargetFormula() {
        return this.targetFormula;
    }

    public void setTargetFormula(String targetFormula) {
        this.targetFormula = targetFormula;
    }

    public String getSourceFormula() {
        return this.sourceFormula;
    }

    public void setSourceFormula(String sourceFormula) {
        this.sourceFormula = sourceFormula;
    }

    public TaskLinkExpressionType getExpressionType() {
        return this.expressionType;
    }

    public void setExpressionType(TaskLinkExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    public String getSourceEntity() {
        return this.sourceEntity;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public String getTargetEntity() {
        return this.targetEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public DesignTaskLinkOrgMappingDefine toDesignDefine() {
        DesignTaskLinkOrgMappingDefineImpl define = new DesignTaskLinkOrgMappingDefineImpl();
        define.setTaskLinkKey(this.taskLinkKey);
        define.setSourceEntity(this.sourceEntity);
        define.setTargetEntity(this.targetEntity);
        define.setMatchingType(this.matchingType);
        define.setTargetFormula(this.targetFormula);
        define.setSourceFormula(this.sourceFormula);
        define.setExpressionType(this.expressionType);
        define.setOrder(this.order);
        return define;
    }

    public TaskLinkOrgMappingDefine toDefine() {
        TaskLinkOrgMappingDefineImpl define = new TaskLinkOrgMappingDefineImpl();
        define.setTaskLinkKey(this.taskLinkKey);
        define.setSourceEntity(this.sourceEntity);
        define.setTargetEntity(this.targetEntity);
        define.setMatchingType(this.matchingType);
        define.setTargetFormula(this.targetFormula);
        define.setSourceFormula(this.sourceFormula);
        define.setExpressionType(this.expressionType);
        define.setOrder(this.order);
        return define;
    }

    public static TaskLinkOrgMappingRule valueOf(DesignTaskLinkOrgMappingDefine define) {
        TaskLinkOrgMappingRule DesRule = new TaskLinkOrgMappingRule();
        DesRule.setTaskLinkKey(define.getTaskLinkKey());
        DesRule.setSourceEntity(define.getSourceEntity());
        DesRule.setTargetEntity(define.getTargetEntity());
        DesRule.setMatchingType(define.getMatchingType());
        DesRule.setTargetFormula(define.getTargetFormula());
        DesRule.setSourceFormula(define.getSourceFormula());
        DesRule.setExpressionType(define.getExpressionType());
        DesRule.setOrder(define.getOrder());
        return DesRule;
    }

    public static TaskLinkOrgMappingRule valueOf(TaskLinkOrgMappingDefine define) {
        TaskLinkOrgMappingRule rule = new TaskLinkOrgMappingRule();
        rule.setTaskLinkKey(define.getTaskLinkKey());
        rule.setSourceEntity(define.getSourceEntity());
        rule.setTargetEntity(define.getTargetEntity());
        rule.setMatchingType(define.getMatchingType());
        rule.setTargetFormula(define.getTargetFormula());
        rule.setSourceFormula(define.getSourceFormula());
        rule.setExpressionType(define.getExpressionType());
        rule.setOrder(define.getOrder());
        return rule;
    }
}

