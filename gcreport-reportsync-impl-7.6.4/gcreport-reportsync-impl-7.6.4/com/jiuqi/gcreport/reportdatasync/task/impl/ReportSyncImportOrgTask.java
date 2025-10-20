/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.progress.common.ProgressDataImpl
 *  com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext
 *  com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum
 *  com.jiuqi.common.reportsync.task.IReportSyncImportTask
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO
 */
package com.jiuqi.gcreport.reportdatasync.task.impl;

import com.jiuqi.common.expimp.progress.common.ProgressDataImpl;
import com.jiuqi.common.reportsync.dto.ReportSyncExportTaskContext;
import com.jiuqi.common.reportsync.enums.ReportDataSyncTypeEnum;
import com.jiuqi.common.reportsync.task.IReportSyncImportTask;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.org.impl.io.service.GcOrgDataService;
import com.jiuqi.gcreport.reportdatasync.util.ReportDataSyncDataUtils;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncUploadDataTaskVO;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class ReportSyncImportOrgTask
implements IReportSyncImportTask {
    @Autowired
    private GcOrgDataService gcOrgDataService;

    public List<String> exec(ReportSyncExportTaskContext reportSyncExportTaskContext) {
        File rootFolder = reportSyncExportTaskContext.getRootFolder();
        ProgressDataImpl progressData = reportSyncExportTaskContext.getProgressData();
        ArrayList<String> msgList = new ArrayList<String>();
        String path = rootFolder.getPath();
        File orgMsgFile = new File(path + "/orgMsg");
        if (!orgMsgFile.exists()) {
            return null;
        }
        ReportDataSyncUploadDataTaskVO uploadDataTaskVO = null;
        try {
            byte[] bytes = FileCopyUtils.copyToByteArray(orgMsgFile);
            if (bytes == null) {
                msgList.add("\u6ca1\u6709\u673a\u6784\u6570\u636e\u5b57\u6bb5\u4fe1\u606f");
                return msgList;
            }
            uploadDataTaskVO = (ReportDataSyncUploadDataTaskVO)JsonUtils.readValue((byte[])bytes, ReportDataSyncUploadDataTaskVO.class);
        }
        catch (Exception e) {
            msgList.add("\u67e5\u8be2\u673a\u6784\u6570\u636e\u5b57\u6bb5\u4fe1\u606f\u9519\u8bef");
            return msgList;
        }
        ReportDataSyncDataUtils.importBaseOrgs(null, path, uploadDataTaskVO, msgList);
        return msgList;
    }

    public String funcTitle() {
        return "\u57fa\u7840\u7ec4\u7ec7";
    }

    public ReportDataSyncTypeEnum syncType() {
        return ReportDataSyncTypeEnum.PARAM;
    }
}

