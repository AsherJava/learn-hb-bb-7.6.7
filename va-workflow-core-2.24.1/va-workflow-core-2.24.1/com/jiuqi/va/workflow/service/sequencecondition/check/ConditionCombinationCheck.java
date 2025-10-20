/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.workflow.exception.WorkflowException
 */
package com.jiuqi.va.workflow.service.sequencecondition.check;

import com.jiuqi.va.domain.workflow.exception.WorkflowException;
import com.jiuqi.va.workflow.service.sequencecondition.check.Condition;
import com.jiuqi.va.workflow.service.sequencecondition.check.ConditionGroupCheck;
import com.jiuqi.va.workflow.service.sequencecondition.relation.Relation;
import com.jiuqi.va.workflow.utils.SequenceConditionUtils;
import com.jiuqi.va.workflow.utils.VaWorkFlowI18nUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class ConditionCombinationCheck
implements Condition {
    private static final Logger logger = LoggerFactory.getLogger(ConditionCombinationCheck.class);
    private final String relationName;
    private final List<ConditionGroupCheck> conditionGroups;

    public ConditionCombinationCheck(String relationName, List<ConditionGroupCheck> conditionGroups) {
        this.relationName = relationName;
        this.conditionGroups = conditionGroups;
    }

    @Override
    public boolean check(Map<String, Object> data) {
        if (!StringUtils.hasText(this.relationName)) {
            return true;
        }
        Relation relation = SequenceConditionUtils.getRelation(this.relationName);
        if (relation == null) {
            logger.error("\u6d41\u8f6c\u7ebf\u6761\u4ef6\u7ec4\u4e0d\u652f\u6301\u5173\u7cfb\u64cd\u4f5c\u7b26\uff1a{}", (Object)this.relationName);
            throw new WorkflowException(VaWorkFlowI18nUtils.getInfo("va.workflow.notsupportseqrelationoperator") + this.relationName);
        }
        ArrayList<Condition> conditions = new ArrayList<Condition>(this.conditionGroups);
        return relation.execute(conditions, data);
    }
}

