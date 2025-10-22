/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.CollectionUtils
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.common.ExpImpFileTypeEnum
 *  com.jiuqi.common.expimp.dataexport.ExportExecutor
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.reportdatasync.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.CollectionUtils;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataexport.ExportExecutor;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.gcreport.reportdatasync.service.ReportDataSyncParamUpdateService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportDataSyncParamSyncReciveUpateInstructionsExportExecutor
implements ExportExecutor {
    @Autowired
    ReportDataSyncParamUpdateService reportDataSyncParamUpdateService;

    public String getName() {
        return "ReportDataSyncParamSyncReciveUpateInstructionsExportExecutor";
    }

    public ExpImpFileTypeEnum getFileType() {
        return null;
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        Param param = (Param)JsonUtils.readValue((String)context.getParam(), Param.class);
        String receiveTaskId = param.getLogId();
        List<String> downTypes = param.getDownType();
        if (CollectionUtils.isEmpty(downTypes)) {
            throw new BusinessRuntimeException("\u53c2\u6570\u4e0b\u8f7d\u9879\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        if (downTypes.size() == 1 && downTypes.contains("UpdateInstruction")) {
            this.reportDataSyncParamUpdateService.downloadParamUpateInstructions(request, response, receiveTaskId);
        } else if (downTypes.size() == 1 && downTypes.contains("Param")) {
            this.reportDataSyncParamUpdateService.downloadParam(request, response, receiveTaskId);
        } else {
            this.reportDataSyncParamUpdateService.downloadParamAndUpateInstructions(request, response, receiveTaskId);
        }
        return null;
    }

    public static class Param {
        private String logId;
        private List<String> downType;

        public String getLogId() {
            return this.logId;
        }

        public void setLogId(String logId) {
            this.logId = logId;
        }

        public List<String> getDownType() {
            return this.downType;
        }

        public void setDownType(List<String> downType) {
            this.downType = downType;
        }
    }
}

