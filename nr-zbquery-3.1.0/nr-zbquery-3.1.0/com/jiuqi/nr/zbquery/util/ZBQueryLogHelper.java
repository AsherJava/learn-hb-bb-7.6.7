/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.zbquery.util;

import com.jiuqi.np.log.LogHelper;

public class ZBQueryLogHelper {
    private static final String MODULE_TITLE = "\u6307\u6807\u7efc\u5408\u67e5\u8be2";

    public static void info(String title, String message) {
        LogHelper.info((String)MODULE_TITLE, (String)title, (String)message);
    }

    public static void error(String title, String message) {
        LogHelper.error((String)MODULE_TITLE, (String)title, (String)message);
    }

    public static void warn(String title, String message) {
        LogHelper.warn((String)MODULE_TITLE, (String)title, (String)message);
    }

    public static void debug(String title, String message) {
        LogHelper.debug((String)MODULE_TITLE, (String)title, (String)message);
    }

    public static void trace(String title, String message) {
        LogHelper.trace((String)MODULE_TITLE, (String)title, (String)message);
    }
}

