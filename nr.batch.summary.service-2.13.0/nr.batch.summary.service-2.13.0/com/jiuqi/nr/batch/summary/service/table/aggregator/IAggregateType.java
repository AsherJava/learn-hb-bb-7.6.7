/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.definition.common.AggrType
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.enumeration.SummaryFunction;
import com.jiuqi.nvwa.definition.common.AggrType;
import java.util.HashMap;
import java.util.Map;

public enum IAggregateType {
    NONE(AggrType.NONE.getValue()),
    SUM(AggrType.SUM.getValue()),
    COUNT(AggrType.COUNT.getValue()),
    AVERAGE(AggrType.AVERAGE.getValue()),
    MIN(AggrType.MIN.getValue()),
    MAX(AggrType.MAX.getValue()),
    DISTINCT_COUNT(AggrType.DISTINCT_COUNT.getValue()),
    NO_AGGREGATION(-1326);

    private final int intValue;
    private static Map<Integer, IAggregateType> mappings;

    private static synchronized Map<Integer, IAggregateType> getMappings() {
        if (mappings == null) {
            mappings = new HashMap<Integer, IAggregateType>();
        }
        return mappings;
    }

    private IAggregateType(int value) {
        this.intValue = value;
        IAggregateType.getMappings().put(value, this);
    }

    public static IAggregateType getAggregateType(AggrType type) {
        return IAggregateType.getMappings().get(type.getValue());
    }

    public static IAggregateType getAggregateType(SummaryFunction function) {
        return IAggregateType.getAggregateType(function.type);
    }
}

