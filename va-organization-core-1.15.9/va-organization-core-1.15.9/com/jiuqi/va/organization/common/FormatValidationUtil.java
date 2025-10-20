/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.organization.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormatValidationUtil {
    public static final String REGEX_INTEGER = "^[-\\+]?\\d+$";
    public static final String REGEX_POSITIVE_INTEGER = "^\\+?[1-9]\\d*$";
    public static final String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
    public static final String REGEX_NUMERIC = "^\\d+$";
    public static final String REGEX_DECIMAL = "^[-\\+]?\\d+\\.\\d+$";
    public static final String REGEX_POSITIVE_DECIMAL = "^\\+?([1-9]+\\.\\d+|0\\.\\d*[1-9])$";
    public static final String REGEX_NEGATIVE_DECIMAL = "^-([1-9]+\\.\\d+|0\\.\\d*[1-9])$";
    public static final String REGEX_REAL_NUMBER = "^[-\\+]?(\\d+|\\d+\\.\\d+)$";
    public static final String REGEX_NON_NEGATIVE_REAL_NUMBER = "^\\+?(\\d+|\\d+\\.\\d+)$";
    public static final String REGEX_ORG_CODE = "^(?:(?!(,|;|/)).){1,60}$";
    public static final String REGEX_ORG_UNIONCODE = "^[A-Za-z0-9]+$";

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean customMatch(String regex, String orginal) {
        return FormatValidationUtil.isMatch(regex, orginal);
    }

    public static boolean isOrgUnion(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_ORG_UNIONCODE, orginal);
    }

    public static boolean isOrgCode(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_ORG_CODE, orginal);
    }

    public static boolean isNumeric(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_NUMERIC, orginal);
    }

    public static boolean isPositiveInteger(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_POSITIVE_INTEGER, orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_NEGATIVE_INTEGER, orginal);
    }

    public static boolean isInteger(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_INTEGER, orginal);
    }

    public static boolean isPositiveDecimal(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_POSITIVE_DECIMAL, orginal);
    }

    public static boolean isNegativeDecimal(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_NEGATIVE_DECIMAL, orginal);
    }

    public static boolean isDecimal(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_DECIMAL, orginal);
    }

    public static boolean isRealNumber(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_REAL_NUMBER, orginal);
    }

    public static boolean isNonNegativeRealNumber(String orginal) {
        return FormatValidationUtil.isMatch(REGEX_NON_NEGATIVE_REAL_NUMBER, orginal);
    }

    public static boolean isPositiveRealNumber(String orginal) {
        return FormatValidationUtil.isPositiveDecimal(orginal) || FormatValidationUtil.isPositiveInteger(orginal);
    }

    public static String replaceInjection(String str) {
        return str.replaceAll("\\s*", "").replace("'", "").replace("{", "");
    }

    public static String checkInjection(String str) {
        if (str.contains(" ") || str.contains("\t") || str.contains("\n") || str.contains("'") || str.contains("{")) {
            throw new RuntimeException("\u975e\u6cd5\u7684\u53c2\u6570");
        }
        return str;
    }
}

