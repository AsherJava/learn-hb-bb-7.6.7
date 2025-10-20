/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.common.reportsync.util.CommonReportUtil
 */
package com.jiuqi.gcreport.inputdata.reportsyncTask;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.common.reportsync.util.CommonReportUtil;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ReportSyncImportNrDataTask
implements IReportSyncImportTask {
    private final String NR_FOLDER_NAME = "NR-data-report";

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"NR-data-report");
        File file = new File(filePath);
        if (file.listFiles().length <= 0) {
            return null;
        }
        ArrayList<String> msgList = new ArrayList<String>();
        msgList.addAll(this.importData(new File(filePath + "/businessData.txt")));
        msgList.addAll(this.importData(new File(filePath + "/annotateData.txt")));
        msgList.addAll(this.importData(new File(filePath + "/errorExplainData.txt")));
        return (List)CommonReportUtil.appendImportMsgIfEmpty(msgList);
    }

    public List<String> importData(File file) {
        if (!file.exists()) {
            return Collections.EMPTY_LIST;
        }
        return Collections.EMPTY_LIST;
    }

    public int priority() {
        return 6;
    }

    public String funcTitle() {
        return "\u62a5\u8868\u6570\u636e";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.DATA;
    }

    public List<String> afterImport(File rootFolder) {
        String filePath = CommonReportUtil.createNewPath((String)rootFolder.getPath(), (String)"NR-data-report");
        File file = new File(filePath);
        if (file.listFiles().length <= 0) {
            return null;
        }
        this.importData(new File(filePath + "/processData.txt"));
        return null;
    }
}

