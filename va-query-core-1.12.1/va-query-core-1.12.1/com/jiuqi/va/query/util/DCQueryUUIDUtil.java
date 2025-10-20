/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.util;

import java.util.UUID;

public final class DCQueryUUIDUtil {
    public static final UUID emptyID = new UUID(0L, 0L);
    public static final String EMPTY_STRID = "00000000-0000-0000-0000-000000000000";

    private DCQueryUUIDUtil() {
    }

    public static String getUUIDStr() {
        return UUID.randomUUID().toString();
    }
}

