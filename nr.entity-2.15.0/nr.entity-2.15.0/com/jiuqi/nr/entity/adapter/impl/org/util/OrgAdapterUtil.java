/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.adapter.impl.org.util;

import org.springframework.util.Assert;

public class OrgAdapterUtil {
    public static String getEntityIdByOrgName(String orgName) {
        return orgName + "@" + "ORG";
    }

    public static String getEntityIdByOrgCode(String orgCode) {
        return orgCode + "@" + "ORG";
    }

    public static boolean isOrg(String entityId) {
        Assert.notNull((Object)entityId, "\u5b9e\u4f53ID\u4e0d\u80fd\u4e3a\u7a7a");
        return entityId.endsWith("@ORG");
    }
}

