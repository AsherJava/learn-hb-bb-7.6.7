/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.sequencecondition.relation;

import com.jiuqi.va.workflow.service.sequencecondition.check.Condition;
import java.util.List;
import java.util.Map;

public interface Relation {
    public String getName();

    public boolean execute(List<Condition> var1, Map<String, Object> var2);
}

