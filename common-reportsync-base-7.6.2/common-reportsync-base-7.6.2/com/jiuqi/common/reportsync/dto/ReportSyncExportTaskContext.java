/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 */
package com.jiuqi.common.reportsync.dto;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportDataSyncServerInfoBase;
import com.jiuqi.common.reportsync.vo.ReportDataSyncParams;
import java.io.File;
import java.util.Set;

public class ReportSyncExportTaskContext {
    private ReportDataSyncParams reportDataSyncParams;
    private File rootFolder;
    private ReportDataSyncServerInfoBase reportDataSyncServerInfoBase;
    private ProgressDataImpl<Set> progressData;

    public ReportDataSyncParams getReportDataSyncParams() {
        return this.reportDataSyncParams;
    }

    public void setReportDataSyncParams(ReportDataSyncParams reportDataSyncParams) {
        this.reportDataSyncParams = reportDataSyncParams;
    }

    public File getRootFolder() {
        return this.rootFolder;
    }

    public void setRootFolder(File rootFolder) {
        this.rootFolder = rootFolder;
    }

    public ReportDataSyncServerInfoBase getReportDataSyncServerInfoBase() {
        return this.reportDataSyncServerInfoBase;
    }

    public void setReportDataSyncServerInfoBase(ReportDataSyncServerInfoBase reportDataSyncServerInfoBase) {
        this.reportDataSyncServerInfoBase = reportDataSyncServerInfoBase;
    }

    public ProgressDataImpl<Set> getProgressData() {
        return this.progressData;
    }

    public void setProgressData(ProgressDataImpl<Set> progressData) {
        this.progressData = progressData;
    }
}

