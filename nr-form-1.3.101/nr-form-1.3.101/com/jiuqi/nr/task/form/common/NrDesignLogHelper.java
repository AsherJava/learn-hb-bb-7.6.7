/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.log.enums.OperResult
 */
package com.jiuqi.nr.task.form.common;

import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.log.enums.OperResult;

public class NrDesignLogHelper {
    private static final String MODULE_TITLE = "\u4efb\u52a1\u8bbe\u8ba1";
    public static final int LOGLEVEL_ERROR = 1;
    public static final int LOGLEVEL_DEFAULT = 0;
    public static final int LOGLEVEL_WARN = 2;
    public static final int LOGLEVEL_INFO = 4;
    public static final int LOGLEVEL_DEBUG = 8;
    public static final int LOGLEVEL_TRACE = 16;

    public static void log(String title, String message, int level) {
        if (level == 0) {
            level = 4;
        }
        if ((level & 1) > 0) {
            LogHelper.error((String)MODULE_TITLE, (String)title, (String)message, (OperResult)OperResult.FAIL, (OperLevel)OperLevel.SYS_OPER);
        }
        if ((level & 2) > 0) {
            LogHelper.warn((String)MODULE_TITLE, (String)title, (String)message, (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.SYS_OPER);
        }
        if ((level & 4) > 0) {
            LogHelper.info((String)MODULE_TITLE, (String)title, (String)message, (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.SYS_OPER);
        }
        if ((level & 8) > 0) {
            LogHelper.debug((String)MODULE_TITLE, (String)title, (String)message, (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.SYS_OPER);
        }
        if ((level & 0x10) > 0) {
            LogHelper.trace((String)MODULE_TITLE, (String)title, (String)message, (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.SYS_OPER);
        }
    }
}

