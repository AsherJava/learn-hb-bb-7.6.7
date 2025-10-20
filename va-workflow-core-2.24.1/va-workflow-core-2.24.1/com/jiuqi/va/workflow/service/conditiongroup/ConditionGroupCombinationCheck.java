/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.conditiongroup;

import com.jiuqi.va.workflow.service.conditiongroup.ConditionCheck;
import com.jiuqi.va.workflow.service.conditiongroup.ConditionGroupCheck;
import com.jiuqi.va.workflow.service.conditiongroup.Relationship;
import java.util.ArrayList;
import java.util.List;

public class ConditionGroupCombinationCheck
implements ConditionCheck {
    private List<ConditionGroupCheck> conditionGroupChecks;
    private Relationship relationship;

    public ConditionGroupCombinationCheck(List<ConditionGroupCheck> conditionGroupChecks, Relationship relationship) {
        this.conditionGroupChecks = conditionGroupChecks;
        this.relationship = relationship;
    }

    @Override
    public boolean check(Object data) {
        ArrayList<ConditionCheck> conditionChecks = new ArrayList<ConditionCheck>(this.conditionGroupChecks);
        return this.relationship.isSatisfied(conditionChecks, data);
    }
}

