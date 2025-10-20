/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.organization.impl.dao;

import com.jiuqi.gcreport.organization.impl.bean.OrgDisplaySchemeDO;

public interface OrgDisplaySchemeDao {
    public static final String TABLE_NAME = "NR_UNITTREE_CAPTION_FIELDS";
    public static final String CF_KEY = "CF_KEY";
    public static final String CF_FORMSCHEME = "CF_FORMSCHEME";
    public static final String CF_OWNER = "CF_OWNER";
    public static final String CF_FIELDS = "CF_FIELDS";
    public static final String CF_CREATIVE = "CF_CREATIVE";
    public static final String CF_ENTITYID = "CF_ENTITYID";

    public OrgDisplaySchemeDO findByOwner(String var1, String var2, String var3);
}

