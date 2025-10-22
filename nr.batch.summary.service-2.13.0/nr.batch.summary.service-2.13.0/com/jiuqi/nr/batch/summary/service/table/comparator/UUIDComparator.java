/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.service.table.comparator;

import com.jiuqi.nr.batch.summary.service.table.comparator.IComparator;
import java.util.UUID;

public class UUIDComparator
implements IComparator<UUID> {
    @Override
    public int compare(UUID o1, UUID o2) {
        return o1.compareTo(o2);
    }
}

