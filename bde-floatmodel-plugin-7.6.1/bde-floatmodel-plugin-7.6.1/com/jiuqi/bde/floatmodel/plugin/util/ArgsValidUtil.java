/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 */
package com.jiuqi.bde.floatmodel.plugin.util;

import com.jiuqi.common.base.util.StringUtils;
import java.util.regex.Pattern;

public class ArgsValidUtil {
    private static final String SECURITY_SQL_REG = "(?:')|(?:--)|(/\\*(?:.|[\\n\\r])*?\\*/)|(\\b(select|update|union|and|or|delete|insert|trancate|char|into|substr|ascii|declare|exec|count|master|into|drop|execute)\\b)";
    private static final String COLON = ":";
    private static final String COMMON = ",";

    public static boolean isSecuritySql(String data) {
        if (StringUtils.isEmpty((String)data)) {
            return true;
        }
        return !Pattern.matches(SECURITY_SQL_REG, data);
    }

    public static boolean isContainCommaAndColon(String data) {
        return data.contains(COLON) && data.contains(COMMON);
    }

    public static boolean isSeparatorEnd(String data) {
        if (data.length() == 0) {
            return false;
        }
        String lastCharacter = data.substring(data.length() - 1);
        return lastCharacter.equals(COLON) || lastCharacter.equals(COMMON);
    }

    public static boolean isRepeatColon(String data) {
        String newData = data.replaceAll(data, COLON);
        return newData.length() - data.length() > 1;
    }

    public static boolean isContinuitySeparator(String data) {
        return data.contains(",,") || data.contains("::");
    }

    public static boolean isSimpleFloatValidated(String data) {
        if (StringUtils.isEmpty((String)data)) {
            return true;
        }
        return !ArgsValidUtil.isContinuitySeparator(data) && !ArgsValidUtil.isRepeatColon(data) && !ArgsValidUtil.isSeparatorEnd(data) && !ArgsValidUtil.isContainCommaAndColon(data);
    }
}

