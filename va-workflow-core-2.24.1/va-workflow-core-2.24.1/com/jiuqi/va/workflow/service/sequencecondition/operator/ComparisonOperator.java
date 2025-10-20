/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.service.sequencecondition.operator;

public interface ComparisonOperator {
    public String getName();

    public boolean compare(Object var1, Object var2);
}

