/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 */
package com.jiuqi.gcreport.reportdatasync.task;

import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import java.io.File;
import java.util.List;

public interface IReportSyncAfterImportTask {
    public List<String> afterImport(File var1);

    public ReportDataSyncTypeEnum syncType();
}

