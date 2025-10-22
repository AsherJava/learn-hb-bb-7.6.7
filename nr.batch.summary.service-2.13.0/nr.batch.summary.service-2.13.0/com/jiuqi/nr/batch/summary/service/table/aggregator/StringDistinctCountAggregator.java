/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;
import java.util.HashSet;

public class StringDistinctCountAggregator
implements IAggregator<Integer> {
    @Override
    public Integer aggregate(Object[] values) {
        HashSet<String> distinctValues = new HashSet<String>();
        for (Object obj : values) {
            if (obj == null) continue;
            distinctValues.add(obj.toString());
        }
        return distinctValues.size();
    }
}

