/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 */
package com.jiuqi.nr.analysisreport.helper;

import com.jiuqi.np.log.LogHelper;

public class AnalysisReportLogHelper {
    public static final String MODULE_TITLE = "\u5206\u6790\u62a5\u544a";
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

    public static void log(String title, String message, Exception e) {
        try {
            StringBuffer print = new StringBuffer().append(message).append("\r\n");
            print.append(e.getClass().toString().replaceAll("class (.*)", "$1")).append("\r\n");
            for (StackTraceElement ste : e.getStackTrace()) {
                print.append("\tat ").append(ste.getClassName() + "." + ste.getMethodName() + "(" + ste.getFileName() + ":" + ste.getLineNumber() + ")").append("\r\n");
            }
            message = print.toString();
        }
        catch (Exception exception) {
            // empty catch block
        }
        LogHelper.error((String)MODULE_TITLE, (String)title, (String)message);
    }
}

