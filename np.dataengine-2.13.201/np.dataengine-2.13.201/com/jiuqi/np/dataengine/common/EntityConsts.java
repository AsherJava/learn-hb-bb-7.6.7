/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

import java.util.UUID;

public class EntityConsts {
    public static final String AUTH_LOGGER = "authlogger";
    public static final UUID EMPTY_ID = new UUID(0L, 0L);
    public static final String C_SEPARATE = "/";

    public static String getEmptyID() {
        return EntityConsts.formatKey(EMPTY_ID.toString());
    }

    public static String formatKey(String entityKey) {
        return entityKey.toUpperCase().replace("-", "");
    }
}

