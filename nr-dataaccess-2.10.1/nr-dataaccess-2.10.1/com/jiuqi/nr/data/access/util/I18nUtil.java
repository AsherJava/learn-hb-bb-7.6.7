/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.data.access.util;

import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.NpContextHolder;

public class I18nUtil {
    public static String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (StringUtils.isEmpty((String)language) || language.equals("zh")) {
            return "zh";
        }
        return language;
    }

    public static boolean isChinese() {
        return I18nUtil.getLanguage().equals("zh");
    }
}

