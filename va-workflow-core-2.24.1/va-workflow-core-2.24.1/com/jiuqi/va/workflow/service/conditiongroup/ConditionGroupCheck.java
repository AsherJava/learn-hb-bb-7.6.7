/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.conditiongroup;

import com.jiuqi.va.workflow.service.conditiongroup.ConditionCheck;
import com.jiuqi.va.workflow.service.conditiongroup.Relationship;
import java.util.List;

public class ConditionGroupCheck
implements ConditionCheck {
    private List<ConditionCheck> conditionChecks;
    private Relationship relationship;

    public ConditionGroupCheck(List<ConditionCheck> conditionChecks, Relationship relationship) {
        this.conditionChecks = conditionChecks;
        this.relationship = relationship;
    }

    @Override
    public boolean check(Object data) {
        return this.relationship.isSatisfied(this.conditionChecks, data);
    }
}

