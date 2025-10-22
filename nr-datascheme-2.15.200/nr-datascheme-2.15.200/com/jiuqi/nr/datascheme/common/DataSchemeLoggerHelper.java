/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.datascheme.common;

import com.jiuqi.np.log.LogHelper;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

public class DataSchemeLoggerHelper {
    private static final String LOG_MOUDLE_DATASCHEME = "\u6570\u636e\u65b9\u6848";

    public static void info(String outline, String message) {
        LogHelper.info((String)LOG_MOUDLE_DATASCHEME, (String)outline, (String)message);
    }

    public static void info(String outline, String format, Object ... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        DataSchemeLoggerHelper.info(outline, ft.getMessage());
    }

    public static void error(String outline, String message) {
        LogHelper.error((String)LOG_MOUDLE_DATASCHEME, (String)outline, (String)message);
    }

    public static void error(String outline, String format, Object ... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        DataSchemeLoggerHelper.error(outline, ft.getMessage());
    }

    public static void warn(String outline, String message) {
        LogHelper.warn((String)LOG_MOUDLE_DATASCHEME, (String)outline, (String)message);
    }

    public static void warn(String outline, String format, Object ... arguments) {
        FormattingTuple ft = MessageFormatter.arrayFormat(format, arguments);
        DataSchemeLoggerHelper.warn(outline, ft.getMessage());
    }
}

