/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.commons.lang3.StringUtils
 */
package com.jiuqi.gcreport.aidocaudit.util;

import org.apache.commons.lang3.StringUtils;

public class TextPreprocessUtil {
    private TextPreprocessUtil() {
        throw new UnsupportedOperationException("\u8be5\u7c7b\u4e0d\u5141\u8bb8\u5b9e\u4f8b\u5316");
    }

    public static String preprocessText(String text) {
        if (StringUtils.isBlank((CharSequence)text)) {
            return text;
        }
        return text.replaceAll("[^\\p{L}\\p{Nd}]", "").toLowerCase();
    }
}

