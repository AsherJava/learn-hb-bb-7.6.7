/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.definition.facade.FormSchemeDefine
 */
package com.jiuqi.nr.data.engine.util;

import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import org.springframework.util.Assert;

public class DataInitUtil {
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

