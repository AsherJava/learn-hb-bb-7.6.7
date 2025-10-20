/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.LogManager
 */
package com.jiuqi.bi.quickreport;

import com.jiuqi.bi.logging.LogManager;

public class ReportConfig {
    private static int maxCellSize = 40000000;

    private ReportConfig() {
    }

    public static int getMaxCellSize() {
        return maxCellSize;
    }

    public static void setMaxCellSize(int size) {
        if (size <= 1000000) {
            throw new IllegalArgumentException("\u8bbe\u7f6e\u6700\u5927\u5355\u5143\u683c\u6570\u8fc7\u5c0f\uff1a" + size);
        }
        maxCellSize = size;
    }

    static {
        String text = System.getProperty("com.jiuqi.bi.quickreport.maxCellSize");
        if (text != null && !text.isEmpty()) {
            try {
                int value = Integer.parseInt(text);
                if (value >= 1000000) {
                    maxCellSize = value;
                } else {
                    LogManager.getLogger(ReportConfig.class).error("\u65e0\u6548\u7684\u5206\u6790\u8868\u53c2\u6570\u914d\u7f6e\uff1acom.jiuqi.bi.quickreport.maxCellSize=" + value);
                }
            }
            catch (NumberFormatException e) {
                LogManager.getLogger(ReportConfig.class).error("\u89e3\u6790\u5206\u6790\u8868\u914d\u7f6e\u9519\u8bef\uff1acom.jiuqi.bi.quickreport.maxCellSize=" + text, (Throwable)e);
            }
        }
    }
}

