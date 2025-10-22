/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdaptCondiUtils {
    private static final String MD_ORG_PARAM = "MD_ORG";
    private static final String ORG_PARAM = "ORG";
    private static final Pattern ADAPT_EXPRESSION_PATTERN = Pattern.compile("^(.*?\\(\")(.*?)(\".*)$");

    public static String expressionOrgTypeConvert(String expression, String gcOrgType) {
        Matcher matcher = ADAPT_EXPRESSION_PATTERN.matcher(expression);
        if (matcher.matches()) {
            String firstParam = matcher.group(2);
            if (MD_ORG_PARAM.equals(firstParam)) {
                return matcher.replaceFirst("$1" + gcOrgType + "$3");
            }
            if (ORG_PARAM.equals(firstParam)) {
                return matcher.replaceFirst("$1MD_ORG$3");
            }
            return expression;
        }
        return expression;
    }
}

