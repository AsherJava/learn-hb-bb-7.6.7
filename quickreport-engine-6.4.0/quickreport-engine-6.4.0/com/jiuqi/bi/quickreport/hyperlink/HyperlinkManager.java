/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.hyperlink;

import com.jiuqi.bi.quickreport.hyperlink.HyperlinkFactory;
import com.jiuqi.bi.quickreport.hyperlink.IHyperlinkExecutor;
import com.jiuqi.bi.quickreport.hyperlink.ReportHyperlinkException;

@Deprecated
public class HyperlinkManager {
    private static HyperlinkFactory global_factory;

    private HyperlinkManager() {
    }

    public static void setHyperlinkFactory(HyperlinkFactory factory) {
        global_factory = factory;
    }

    public static boolean isEnabled() {
        return global_factory != null;
    }

    public static IHyperlinkExecutor createExecutor() throws ReportHyperlinkException {
        if (global_factory == null) {
            throw new ReportHyperlinkException("\u7cfb\u7edf\u672a\u6ce8\u518c\u4efb\u4f55\u8d85\u94fe\u63a5\u5904\u7406\u5668\u3002");
        }
        return global_factory.createExecutor();
    }

    public static String getHyperlinkFunction(String contextPath, String cacheID) throws ReportHyperlinkException {
        if (global_factory == null) {
            return null;
        }
        return global_factory.getHyperlinkFunction(contextPath, cacheID);
    }
}

