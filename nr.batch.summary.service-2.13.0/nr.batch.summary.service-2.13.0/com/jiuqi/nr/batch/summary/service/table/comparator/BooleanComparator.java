/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.comparator;

import com.jiuqi.nr.batch.summary.service.table.comparator.IComparator;

public class BooleanComparator
implements IComparator<Boolean> {
    @Override
    public int compare(Boolean o1, Boolean o2) {
        return Boolean.compare(o1, o2);
    }
}

