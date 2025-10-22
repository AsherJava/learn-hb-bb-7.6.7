/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.archive.util;

public class ArchiveLogUtil {
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

