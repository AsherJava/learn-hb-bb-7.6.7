/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.aggregator;

import com.jiuqi.nr.batch.summary.service.table.aggregator.IAggregator;

public class StringCountAggregator
implements IAggregator<String> {
    @Override
    public String aggregate(Object[] values) {
        int count = 0;
        for (Object obj : values) {
            if (obj == null) continue;
            ++count;
        }
        return Integer.valueOf(count).toString();
    }
}

