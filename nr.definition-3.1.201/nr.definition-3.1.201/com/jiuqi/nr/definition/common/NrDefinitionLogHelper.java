/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.definition.common;

import com.jiuqi.np.log.LogHelper;

public class NrDefinitionLogHelper {
    private static final String MODULE_TITLE = "\u62a5\u8868\u53c2\u6570\u5b9a\u4e49";
    public static int LOGLEVEL_ERROR = 1;
    public static int LOGLEVEL_DEFAULT = LOGLEVEL_ERROR >> 1;
    public static int LOGLEVEL_WARN = LOGLEVEL_ERROR << 1;
    public static int LOGLEVEL_INFO = LOGLEVEL_ERROR << 2;
    public static int LOGLEVEL_DEBUG = LOGLEVEL_ERROR << 3;
    public static int LOGLEVEL_TRACE = LOGLEVEL_ERROR << 4;

    public static void log(String title, String message, int level) {
        if (level == LOGLEVEL_DEFAULT) {
            level = LOGLEVEL_INFO;
        }
        if ((level & LOGLEVEL_ERROR) > 0) {
            LogHelper.error((String)MODULE_TITLE, (String)title, (String)message);
        }
        if ((level & LOGLEVEL_WARN) > 0) {
            LogHelper.warn((String)MODULE_TITLE, (String)title, (String)message);
        }
        if ((level & LOGLEVEL_INFO) > 0) {
            LogHelper.info((String)MODULE_TITLE, (String)title, (String)message);
        }
        if ((level & LOGLEVEL_DEBUG) > 0) {
            LogHelper.debug((String)MODULE_TITLE, (String)title, (String)message);
        }
        if ((level & LOGLEVEL_TRACE) > 0) {
            LogHelper.trace((String)MODULE_TITLE, (String)title, (String)message);
        }
    }
}

