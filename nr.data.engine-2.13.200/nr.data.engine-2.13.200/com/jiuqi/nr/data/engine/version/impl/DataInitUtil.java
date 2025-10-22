/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.engine.version.impl;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import org.springframework.util.Assert;

public class DataInitUtil {
    public static final String SPLIT_CHAR = ";";
    public static final String ISAUTOCREATED_FIELD = "ISAUTOCREATED";
    public static final String TITLE_FIELD = "TITLE";
    public static final String DESCRIBE_FIELD = "DESCRIBE_";
    public static final String CREATTIME_FIELD = "CREATTIME";
    public static final String CREATUSER_FIELD = "CREATUSER";

    public static String getSysVersionTableName(FormSchemeDefine formScheme) {
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", "SYS_VER_", formScheme.getFormSchemeCode());
    }

    public static String getSysVersionRelationTableName(FormSchemeDefine formScheme) {
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", "SYS_VER_REL_", formScheme.getFormSchemeCode());
    }

    public static String getSysHistotyTableName(FormSchemeDefine formScheme) {
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", "SYS_UP_HI_", formScheme.getFormSchemeCode());
    }

    public static String getSysStateTableName(FormSchemeDefine formScheme) {
        Assert.notNull((Object)formScheme, "parameter 'formScheme' must not be null.");
        return String.format("%s%s", "SYS_UP_ST_", formScheme.getFormSchemeCode());
    }
}

