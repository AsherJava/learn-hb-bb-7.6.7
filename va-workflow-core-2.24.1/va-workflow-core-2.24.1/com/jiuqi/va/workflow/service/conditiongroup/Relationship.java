/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.conditiongroup;

import com.jiuqi.va.workflow.service.conditiongroup.ConditionCheck;
import java.util.List;

public interface Relationship {
    public String getName();

    public boolean isSatisfied(List<ConditionCheck> var1, Object var2);
}

