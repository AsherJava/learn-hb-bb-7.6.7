/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.zbselector.utils;

import com.jiuqi.np.core.context.NpContextHolder;
import java.util.Locale;

public class ZBSelectorUtil {
    public static String getHiddenZBTitle() {
        if (Locale.SIMPLIFIED_CHINESE.getLanguage().equals(NpContextHolder.getContext().getLocale().getLanguage())) {
            return "\u9690\u85cf\u6307\u6807";
        }
        return "Hidden Fields";
    }
}

