/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.batch.summary.service.enumeration;

import com.jiuqi.nvwa.definition.common.AggrType;

public enum SummaryFunction {
    SUM(AggrType.SUM, "SUM"),
    COUNT(AggrType.COUNT, "COUNT"),
    AVG(AggrType.AVERAGE, "AVG"),
    MIN(AggrType.MIN, "MIN"),
    MAX(AggrType.MAX, "MAX"),
    DISTINCT_COUNT(AggrType.DISTINCT_COUNT, "DISTINCT");

    public final AggrType type;
    public final String name;

    private SummaryFunction(AggrType type, String name) {
        this.type = type;
        this.name = name;
    }

    public static SummaryFunction valueOfType(AggrType type) {
        for (SummaryFunction f : SummaryFunction.values()) {
            if (type.getValue() != f.type.getValue()) continue;
            return f;
        }
        return null;
    }
}

