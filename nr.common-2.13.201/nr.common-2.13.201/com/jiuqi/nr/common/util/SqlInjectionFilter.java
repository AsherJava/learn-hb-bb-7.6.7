/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.util;

import java.util.regex.Pattern;

public class SqlInjectionFilter {
    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile("(['\"\\\\;#])", 34);

    public static String filterSqlInjection(String input) {
        if (input != null) {
            return SQL_INJECTION_PATTERN.matcher(input).replaceAll("");
        }
        return null;
    }

    public static void main(String[] args) {
        String sql = "SELECT * FROM users WHERE name = 'Alice'--'; DROP TABLE users;";
        String safeSql = SqlInjectionFilter.filterSqlInjection(sql);
        System.out.println(safeSql);
    }
}

