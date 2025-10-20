/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.storage;

import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.storage.IReportStorage;
import com.jiuqi.bi.quickreport.storage.ReportStorageException;

public class ReportStorageManager {
    private static IReportStorage reportStorage;

    private ReportStorageManager() {
    }

    public static void setStorage(IReportStorage storage) {
        reportStorage = storage;
    }

    public static IReportStorage getStorage() {
        return reportStorage;
    }

    public static QuickReport loadReport(String reportName) throws ReportStorageException {
        if (reportStorage == null) {
            throw new ReportStorageException("\u672a\u6307\u5b9a\u62a5\u8868\u5f53\u524d\u5b58\u50a8\u63a5\u53e3\u3002");
        }
        return reportStorage.loadReport(reportName);
    }

    public static String getReportTitle(String reportName) throws ReportStorageException {
        if (reportStorage == null) {
            throw new ReportStorageException("\u672a\u6307\u5b9a\u62a5\u8868\u5f53\u524d\u5b58\u50a8\u63a5\u53e3\u3002");
        }
        return reportStorage.getReportTitle(reportName);
    }
}

