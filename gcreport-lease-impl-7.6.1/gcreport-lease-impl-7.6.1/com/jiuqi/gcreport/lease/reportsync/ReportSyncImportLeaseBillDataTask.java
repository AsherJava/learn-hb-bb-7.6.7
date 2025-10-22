/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.gcreport.billcore.reportsync.BillDataSyncTool
 */
package com.jiuqi.gcreport.lease.reportsync;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.gcreport.billcore.reportsync.BillDataSyncTool;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncImportLeaseBillDataTask
implements IReportSyncImportTask {
    public Collection<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        ArrayList<String> logMsg = new ArrayList<String>();
        logMsg.addAll(BillDataSyncTool.importCommonBillTxt((File)rootFolder, (String)"GC_LESSORBILL"));
        logMsg.addAll(BillDataSyncTool.importCommonBillTxt((File)rootFolder, (String)"GC_TENANTRYBILL"));
        return logMsg;
    }

    public String funcTitle() {
        return "\u878d\u8d44\u79df\u8d41\u53f0\u8d26";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }
}

