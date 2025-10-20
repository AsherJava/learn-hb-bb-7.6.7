/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.definition.util;

import com.jiuqi.np.log.LogHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class DefinitionUtils {
    private static final String LOG_MOUDLE = "\u62a5\u8868\u53c2\u6570";

    private DefinitionUtils() {
    }

    public static <K, E> List<E> limitExe(List<K> keys, Function<List<K>, List<E>> function) {
        ArrayList list = new ArrayList();
        int number = 1000;
        int limit = (keys.size() + number - 1) / number;
        Stream.iterate(0, n -> n + 1).limit(limit).forEach(i -> {
            List subKeys = keys.stream().skip(i * number).limit(number).collect(Collectors.toList());
            List apply = (List)function.apply(subKeys);
            if (null != apply && !apply.isEmpty()) {
                list.addAll(apply);
            }
        });
        return list;
    }

    public static void info(String outline, String message) {
        LogHelper.info((String)LOG_MOUDLE, (String)outline, (String)message);
    }

    public static void info(String outline, String format, Object ... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        DefinitionUtils.info(outline, ft.getMessage());
    }

    public static void warn(String outline, String message) {
        LogHelper.warn((String)LOG_MOUDLE, (String)outline, (String)message);
    }

    public static void warn(String outline, String format, Object ... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        DefinitionUtils.warn(outline, ft.getMessage());
    }

    public static void error(String outline, String message) {
        LogHelper.error((String)LOG_MOUDLE, (String)outline, (String)message);
    }

    public static void error(String outline, String format, Object ... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        DefinitionUtils.error(outline, ft.getMessage());
    }
}

