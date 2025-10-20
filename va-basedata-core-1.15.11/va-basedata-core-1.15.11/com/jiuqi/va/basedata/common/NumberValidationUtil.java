/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.basedata.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NumberValidationUtil {
    public static final String REGEX_INTEGER = "^[-\\+]?\\d+$";
    public static final String REGEX_POSITIVE_INTEGER = "^\\+?[1-9]\\d*$";
    public static final String REGEX_NEGATIVE_INTEGER = "^-[1-9]\\d*$";
    public static final String REGEX_NUMERIC = "^\\d+$";
    public static final String REGEX_DECIMAL = "^[-\\+]?\\d+\\.\\d+$";
    public static final String REGEX_POSITIVE_DECIMAL = "^\\+?([1-9]+\\.\\d+|0\\.\\d*[1-9])$";
    public static final String REGEX_NEGATIVE_DECIMAL = "^-([1-9]+\\.\\d+|0\\.\\d*[1-9])$";
    public static final String REGEX_REAL_NUMBER = "^[-\\+]?(\\d+|\\d+\\.\\d+)$";
    public static final String REGEX_NON_NEGATIVE_REAL_NUMBER = "^\\+?(\\d+|\\d+\\.\\d+)$";

    private static boolean isMatch(String regex, String orginal) {
        if (orginal == null || orginal.trim().equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher isNum = pattern.matcher(orginal);
        return isNum.matches();
    }

    public static boolean isNumeric(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_NUMERIC, orginal);
    }

    public static boolean isPositiveInteger(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_POSITIVE_INTEGER, orginal);
    }

    public static boolean isNegativeInteger(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_NEGATIVE_INTEGER, orginal);
    }

    public static boolean isInteger(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_INTEGER, orginal);
    }

    public static boolean isPositiveDecimal(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_POSITIVE_DECIMAL, orginal);
    }

    public static boolean isNegativeDecimal(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_NEGATIVE_DECIMAL, orginal);
    }

    public static boolean isDecimal(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_DECIMAL, orginal);
    }

    public static boolean isRealNumber(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_REAL_NUMBER, orginal);
    }

    public static boolean isNonNegativeRealNumber(String orginal) {
        return NumberValidationUtil.isMatch(REGEX_NON_NEGATIVE_REAL_NUMBER, orginal);
    }

    public static boolean isPositiveRealNumber(String orginal) {
        return NumberValidationUtil.isPositiveDecimal(orginal) || NumberValidationUtil.isPositiveInteger(orginal);
    }
}

