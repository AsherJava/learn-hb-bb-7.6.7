/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.common.utils;

import org.springframework.util.Assert;

public class EntityUtils {
    public static final String DEFAULT_ENTITY_CONNECT = "@";

    public static String getId(String entityId) {
        Assert.notNull((Object)entityId, "\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a.");
        return entityId.substring(0, entityId.indexOf(DEFAULT_ENTITY_CONNECT) == -1 ? entityId.length() : entityId.indexOf(DEFAULT_ENTITY_CONNECT));
    }

    public static String getCategory(String entityId) {
        Assert.notNull((Object)entityId, "\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a.");
        return entityId.substring(entityId.indexOf(DEFAULT_ENTITY_CONNECT) + 1);
    }

    public static String getEntityId(String defineId, String categoryId) {
        Assert.notNull((Object)defineId, "\u5b9e\u4f53\u5b9a\u4e49ID\u4e0d\u80fd\u4e3a\u7a7a.");
        Assert.notNull((Object)categoryId, "\u5b9e\u4f53\u7c7b\u578b\u4e0d\u80fd\u4e3a\u7a7a.");
        return defineId + DEFAULT_ENTITY_CONNECT + categoryId;
    }

    public static Boolean isOldParam(String defineId) {
        Assert.notNull((Object)defineId, "\u5b9e\u4f53\u5b9a\u4e49ID\u4e0d\u80fd\u4e3a\u7a7a.");
        return defineId.indexOf(DEFAULT_ENTITY_CONNECT) == -1;
    }
}

