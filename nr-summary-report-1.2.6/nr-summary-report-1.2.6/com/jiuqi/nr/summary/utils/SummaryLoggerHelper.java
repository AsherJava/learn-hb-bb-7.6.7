/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.log.LogHelper
 *  com.jiuqi.np.log.enums.OperLevel
 *  com.jiuqi.np.log.enums.OperResult
 */
package com.jiuqi.nr.summary.utils;

import com.jiuqi.np.log.LogHelper;
import com.jiuqi.np.log.enums.OperLevel;
import com.jiuqi.np.log.enums.OperResult;
import java.util.Map;

public abstract class SummaryLoggerHelper {
    private static final String MOUDLE = "\u81ea\u5b9a\u4e49\u6c47\u603b\u8868";

    public static void info(String title, String message) {
        LogHelper.info((String)MOUDLE, (String)title, (String)message, (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.USER_OPER);
    }

    public static void error(String title, String message) {
        LogHelper.error((String)MOUDLE, (String)title, (String)message, (OperResult)OperResult.FAIL, (OperLevel)OperLevel.USER_OPER);
    }

    public static void info(String title, String message, Map<String, String> customMap) {
        LogHelper.info((String)MOUDLE, (String)title, (String)message, customMap, (OperResult)OperResult.SUCCESS, (OperLevel)OperLevel.USER_OPER);
    }

    public static void error(String title, String message, Map<String, String> customMap) {
        LogHelper.error((String)MOUDLE, (String)title, (String)message, customMap, (OperResult)OperResult.FAIL, (OperLevel)OperLevel.USER_OPER);
    }
}

