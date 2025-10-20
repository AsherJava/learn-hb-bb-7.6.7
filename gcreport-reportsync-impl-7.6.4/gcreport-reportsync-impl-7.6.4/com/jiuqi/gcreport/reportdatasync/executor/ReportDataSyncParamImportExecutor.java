/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.common.ExpImpFileTypeEnum
 *  com.jiuqi.common.expimp.dataimport.ImportExecutor
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.reportdatasync.executor;

import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataimport.ImportExecutor;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class ReportDataSyncParamImportExecutor
implements ImportExecutor {
    @Autowired
    private ReportDataSyncParamUpdateService paramUpdateService;

    public String getName() {
        return "ReportDataSyncParamImportExecutor";
    }

    public ExpImpFileTypeEnum getFileType() {
        return null;
    }

    public Object dataImport(MultipartFile importFile, ImportContext context) throws Exception {
        Boolean isSuccess = this.paramUpdateService.importReportParamFile(importFile, null, context);
        if (isSuccess.booleanValue()) {
            return "\u53c2\u6570\u5bfc\u5165\u6210\u529f\u3002";
        }
        return "\u53c2\u6570\u5bfc\u5165\u5931\u8d25\u3002";
    }
}

