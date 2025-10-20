/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.base.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.ColumnType;
import com.jiuqi.common.base.util.ColumnTypeEnum;
import java.util.List;

public class SqlBuildUtil {
    public static String getStrInCondi(String columnName, List<String> list) {
        return SqlBuildUtil.getInCondi(columnName, list, ColumnTypeEnum.STRING.getColumnType());
    }

    public static String getStrNotInCondi(String columnName, List<String> list) {
        return SqlBuildUtil.getNotInCondi(columnName, list, ColumnTypeEnum.STRING.getColumnType());
    }

    public static String getIntegerInCondi(String columnName, List<Integer> list) {
        return SqlBuildUtil.getInCondi(columnName, list, ColumnTypeEnum.INT.getColumnType());
    }

    public static <T> String getInCondi(String columnName, List<T> list, ColumnType<? super T> type) {
        return SqlBuildUtil.getInCondi(columnName, list, true, 500, type);
    }

    public static <T> String getNotInCondi(String columnName, List<T> list, ColumnType<? super T> type) {
        return SqlBuildUtil.getInCondi(columnName, list, false, 500, type);
    }

    private static <T> String getInCondi(String columnName, List<T> list, boolean isEqual, int threshold, ColumnType<? super T> type) {
        if (list == null || list.size() == 0) {
            throw new BusinessRuntimeException("\u62fc\u63a5IN/NOT IN SQL\u6761\u4ef6\u65f6\u4f20\u5165\u7684\u96c6\u5408\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        int count = list.size();
        if (count == 1) {
            return " " + columnName + (isEqual ? " = " : " <> ") + type.getColumn(list.get(0)) + " ";
        }
        StringBuilder result = new StringBuilder("");
        int group = count % threshold == 0 ? count / threshold : count / threshold + 1;
        result.append(" (");
        for (int j = 0; j < group; ++j) {
            if (j > 0) {
                result.append("\n " + (isEqual ? " or " : " and "));
            }
            result.append(columnName).append((isEqual ? " in " : " not in ") + " (");
            for (int i = j * threshold; i < count && i < threshold * (j + 1); ++i) {
                result.append(type.getColumn(list.get(i)));
                if (i == count - 1 || i == threshold * (j + 1) - 1) break;
                result.append(",");
            }
            result.append(" ) ");
        }
        result.append(") \n");
        return result.toString();
    }
}

