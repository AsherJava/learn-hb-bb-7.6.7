/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.calibre2.common;

import java.util.List;
import java.util.Map;
import org.springframework.util.StringUtils;

public class Utils {
    private static final String CALIBRE_PREFIX = "NR_CALIBRE_";
    private static final String CALIBRE_SUFFIX = "_SUBLIST";

    public static boolean sqlValidate(String str) {
        str = str.toLowerCase();
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|table|from|grant|use|group_concat|column_name|information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";
        String[] badStrs = badStr.split("\\|");
        for (int i = 0; i < badStrs.length; ++i) {
            if (str.indexOf(badStrs[i]) < 0) continue;
            return true;
        }
        return false;
    }

    public static String generatorTableCode(String code) {
        return CALIBRE_PREFIX.concat(code);
    }

    public static String generatorSubTableCode(String code) {
        return code.concat(CALIBRE_SUFFIX);
    }

    public static String buildEntityTitle(List<String> keys, Map<String, String> keyToTitle) {
        StringBuffer builder = new StringBuffer();
        for (String key : keys) {
            String title = keyToTitle.get(key);
            if (!StringUtils.hasText(title)) continue;
            builder.append(title).append(";");
        }
        if (builder.length() > 1) {
            builder.replace(builder.length() - 1, builder.length(), "");
        }
        return builder.toString();
    }
}

