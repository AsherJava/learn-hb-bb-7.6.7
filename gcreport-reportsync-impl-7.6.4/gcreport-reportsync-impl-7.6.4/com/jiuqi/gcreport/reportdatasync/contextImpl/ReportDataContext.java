/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 */
package com.jiuqi.gcreport.reportdatasync.contextImpl;

import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadLogEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncUploadSchemeEO;
import java.util.ArrayList;
import java.util.List;

public class ReportDataContext
extends MultilevelSyncContext {
    private ReportDataSyncUploadLogEO uploadLogEO;
    private ReportDataSyncUploadSchemeEO uploadSchemeEO;
    private String taskId;
    private List<String> logs;

    public ReportDataSyncUploadLogEO getUploadLogEO() {
        return this.uploadLogEO;
    }

    public void setUploadLogEO(ReportDataSyncUploadLogEO uploadLogEO) {
        this.uploadLogEO = uploadLogEO;
    }

    public List<String> getLogs() {
        return this.logs == null ? new ArrayList() : this.logs;
    }

    public void setLogs(List<String> logs) {
        this.logs = logs;
    }

    public ReportDataSyncUploadSchemeEO getUploadSchemeEO() {
        return this.uploadSchemeEO;
    }

    public void setUploadSchemeEO(ReportDataSyncUploadSchemeEO uploadSchemeEO) {
        this.uploadSchemeEO = uploadSchemeEO;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}

