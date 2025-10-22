/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.comparator;

import com.jiuqi.nr.batch.summary.service.table.comparator.IComparator;
import java.math.BigDecimal;

public class BigDecimalComparator
implements IComparator<BigDecimal> {
    @Override
    public int compare(BigDecimal o1, BigDecimal o2) {
        return o1.compareTo(o2);
    }
}

