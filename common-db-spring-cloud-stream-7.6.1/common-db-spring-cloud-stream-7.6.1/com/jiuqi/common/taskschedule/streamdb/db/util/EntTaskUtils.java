/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.sf.Framework
 */
package com.jiuqi.common.taskschedule.streamdb.db.util;

import com.jiuqi.nvwa.sf.Framework;

public class EntTaskUtils {
    public static void waitServerStartUp() {
        try {
            for (int waitMinute = 0; !EntTaskUtils.serverStartUp() && waitMinute < 10; ++waitMinute) {
                Thread.sleep(60000L);
            }
            return;
        }
        catch (Exception exception) {
            return;
        }
    }

    public static boolean serverStartUp() {
        return Framework.getInstance().startSuccessful();
    }

    public static String getExceptionStackStr(Throwable e) {
        StringBuffer result = new StringBuffer();
        result.append(e.getMessage()).append("\n");
        if (e.getStackTrace() != null) {
            for (StackTraceElement element : e.getStackTrace()) {
                result.append(element.toString()).append("\n");
            }
        }
        return result.toString();
    }
}

