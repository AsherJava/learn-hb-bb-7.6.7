/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.ConverterUtils
 *  com.jiuqi.common.expimp.common.ExpImpFileTypeEnum
 *  com.jiuqi.common.expimp.dataexport.ExportExecutor
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.reportdatasync.executor;

import com.jiuqi.common.base.util.ConverterUtils;
import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataexport.ExportExecutor;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncUploadService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDataSyncDataExportExecutor
implements ExportExecutor {
    @Autowired
    private ReportDataSyncUploadService uploadService;

    public String getName() {
        return "ReportDataSyncDataExportExecutor";
    }

    public ExpImpFileTypeEnum getFileType() {
        return null;
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) {
        String uploadSchemeId = ConverterUtils.getAsString((Object)context.getParam());
        this.uploadService.downloadReportData(request, response, uploadSchemeId);
        return null;
    }
}

