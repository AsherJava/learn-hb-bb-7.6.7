/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.comparator;

import com.jiuqi.nr.batch.summary.service.table.comparator.IComparator;

public class StringComparator
implements IComparator<String> {
    @Override
    public int compare(String a, String b) {
        if (a == null) {
            return -1;
        }
        if (b == null) {
            return 1;
        }
        return a.compareTo(b);
    }
}

