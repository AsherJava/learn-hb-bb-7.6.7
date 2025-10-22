/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataresource.authority;

public class Util {
    private static final String TAG = "__";

    public static String getResourceIdByType(String dataSourceId, int nodeTpe) {
        return nodeTpe + TAG + dataSourceId;
    }

    public static String[] splitResourceId(String resourceId) {
        return resourceId.split(TAG);
    }
}

