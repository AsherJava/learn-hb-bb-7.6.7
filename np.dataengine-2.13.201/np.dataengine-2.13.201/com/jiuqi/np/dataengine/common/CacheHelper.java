/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.common;

public class CacheHelper {
    private static final String ENTITYCACHENAME = "entitycache";
    private static final String DATACACHENAME = "datacache";

    public static String getEntityCacheName(String tableKey) {
        String cacheName = String.format("%s_%s", ENTITYCACHENAME, tableKey);
        return cacheName;
    }

    public static String getDataCacheTableName(String tableKey) {
        String cacheName = String.format("%s_%s", DATACACHENAME, tableKey);
        return cacheName;
    }
}

