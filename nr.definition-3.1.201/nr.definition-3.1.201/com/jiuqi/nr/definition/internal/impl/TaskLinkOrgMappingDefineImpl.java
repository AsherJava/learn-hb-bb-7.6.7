/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBField
 *  com.jiuqi.np.definition.internal.anno.DBAnno$DBTable
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.np.definition.internal.anno.DBAnno;
import com.jiuqi.nr.definition.common.TaskLinkExpressionType;
import com.jiuqi.nr.definition.common.TaskLinkMatchingType;
import com.jiuqi.nr.definition.facade.TaskLinkOrgMappingDefine;

@DBAnno.DBTable(dbTable="NR_PARAM_TASKLINK_ORG")
public class TaskLinkOrgMappingDefineImpl
implements TaskLinkOrgMappingDefine {
    @DBAnno.DBField(dbField="tor_tr_key")
    private String taskLinkKey;
    @DBAnno.DBField(dbField="tor_source_entity")
    private String sourceEntity;
    @DBAnno.DBField(dbField="tor_target_entity")
    private String targetEntity;
    @DBAnno.DBField(dbField="tor_matching_type", tranWith="transTaskLinkMatchingType", dbType=Integer.class, appType=TaskLinkMatchingType.class)
    private TaskLinkMatchingType matchingType;
    @DBAnno.DBField(dbField="tor_target_formula")
    private String targetFormula;
    @DBAnno.DBField(dbField="tor_source_formula")
    private String sourceFormula;
    @DBAnno.DBField(dbField="tor_expression_type", tranWith="transTaskLinkExpressionType", dbType=Integer.class, appType=TaskLinkExpressionType.class)
    private TaskLinkExpressionType expressionType = TaskLinkExpressionType.EQUALS;
    @DBAnno.DBField(dbField="tor_order", isOrder=true)
    private String order;

    public void setTaskLinkKey(String taskLinkKey) {
        this.taskLinkKey = taskLinkKey;
    }

    public void setSourceEntity(String sourceEntity) {
        this.sourceEntity = sourceEntity;
    }

    public void setTargetEntity(String targetEntity) {
        this.targetEntity = targetEntity;
    }

    public void setMatchingType(TaskLinkMatchingType matchingType) {
        this.matchingType = matchingType;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public void setTargetFormula(String targetFormula) {
        this.targetFormula = targetFormula;
    }

    public void setSourceFormula(String sourceFormula) {
        this.sourceFormula = sourceFormula;
    }

    public void setExpressionType(TaskLinkExpressionType expressionType) {
        this.expressionType = expressionType;
    }

    @Override
    public String getTaskLinkKey() {
        return this.taskLinkKey;
    }

    @Override
    public String getTargetEntity() {
        return this.targetEntity;
    }

    @Override
    public String getSourceEntity() {
        return this.sourceEntity;
    }

    @Override
    public TaskLinkMatchingType getMatchingType() {
        return this.matchingType;
    }

    @Override
    public String getTargetFormula() {
        return this.targetFormula;
    }

    @Override
    public String getSourceFormula() {
        return this.sourceFormula;
    }

    @Override
    public TaskLinkExpressionType getExpressionType() {
        return this.expressionType;
    }

    @Override
    public String getOrder() {
        return this.order;
    }
}

