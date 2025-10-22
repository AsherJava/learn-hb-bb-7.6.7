/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.basedata.util;

import org.springframework.util.Assert;

public class BaseDataAdapterUtil {
    public static String getEntityIdByBaseDataName(String baseDataName) {
        return baseDataName + "@" + "BASE";
    }

    public static String getEntityIdByBaseDataCode(String baseDataCode) {
        return baseDataCode + "@" + "BASE";
    }

    public static boolean isBaseData(String entityId) {
        Assert.notNull((Object)entityId, "\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a");
        return entityId.endsWith("@BASE");
    }
}

