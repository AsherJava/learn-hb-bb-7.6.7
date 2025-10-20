/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.conditiongroup;

import com.jiuqi.va.workflow.domain.WorkflowOption;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionCheck;
import com.jiuqi.va.workflow.service.conditiongroup.Relationship;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class OrRelationship
implements Relationship {
    @Override
    public String getName() {
        return WorkflowOption.RelationType.OR.name();
    }

    @Override
    public boolean isSatisfied(List<ConditionCheck> conditionChecks, Object data) {
        for (ConditionCheck conditionCheck : conditionChecks) {
            if (!conditionCheck.check(data)) continue;
            return true;
        }
        return false;
    }
}

