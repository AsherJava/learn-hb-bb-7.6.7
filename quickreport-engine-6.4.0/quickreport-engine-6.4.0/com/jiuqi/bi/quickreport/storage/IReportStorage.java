/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.storage;

import com.jiuqi.bi.quickreport.model.QuickReport;
import com.jiuqi.bi.quickreport.storage.ReportStorageException;

public interface IReportStorage {
    public QuickReport loadReport(String var1) throws ReportStorageException;

    public String getReportTitle(String var1) throws ReportStorageException;
}

