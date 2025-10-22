/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package com.jiuqi.nr.jtable.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtils {
    private static String[] IEBrowserSignals = new String[]{"MSIE", "Trident", "Edge"};

    public static boolean isMSBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (!userAgent.contains(signal)) continue;
            return true;
        }
        return false;
    }
}

