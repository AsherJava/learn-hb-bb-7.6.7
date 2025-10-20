/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.util;

public class DCQuerySqlBuildUtil {
    private DCQuerySqlBuildUtil() {
    }

    public static String getValueListStr(int count) {
        if (count == 0) {
            return " 1 = 1 ";
        }
        StringBuilder result = new StringBuilder("");
        result.append(" (");
        for (int i = 0; i < count; ++i) {
            result.append(" ? ");
            if (i == count - 1) break;
            result.append(",");
        }
        result.append(") \n");
        return result.toString();
    }

    public static String getStrInCondi(String columnName, int count) {
        return DCQuerySqlBuildUtil.getInCondi(columnName, count, true, 500);
    }

    public static String getStrNotInCondi(String columnName, int count) {
        return DCQuerySqlBuildUtil.getInCondi(columnName, count, false, 500);
    }

    private static <T> String getInCondi(String columnName, int count, boolean isEqual, int threshold) {
        if (count == 0) {
            return " 1 = 1 ";
        }
        if (count == 1) {
            return " " + columnName + (isEqual ? " = " : " <> ") + " ? ";
        }
        StringBuilder result = new StringBuilder();
        int group = count % threshold == 0 ? count / threshold : count / threshold + 1;
        result.append(" (");
        for (int j = 0; j < group; ++j) {
            if (j > 0) {
                result.append("\n ").append(isEqual ? " or " : " and ");
            }
            result.append(columnName).append(isEqual ? " in " : " not in ").append(" (");
            for (int i = j * threshold; i < count && i < threshold * (j + 1); ++i) {
                result.append(" ? ");
                if (i == count - 1 || i == threshold * (j + 1) - 1) break;
                result.append(",");
            }
            result.append(" ) ");
        }
        result.append(") \n");
        return result.toString();
    }
}

