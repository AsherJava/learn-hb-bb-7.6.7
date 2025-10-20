/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.common.ExpImpFileTypeEnum
 *  com.jiuqi.common.expimp.dataexport.ExportExecutor
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.gcreport.analysisreport.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.common.ExpImpFileTypeEnum;
import com.jiuqi.common.expimp.dataexport.ExportExecutor;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.gcreport.analysisreport.dto.req.AnalysisReportDataExportExecutorParamDTO;
import com.jiuqi.gcreport.analysisreport.service.AnalysisReportDataService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AnalysisReportDataExportExecutor
implements ExportExecutor {
    @Autowired
    AnalysisReportDataService analysisReportDataService;

    public String getName() {
        return "AnalysisReportDataExportExecutor";
    }

    public ExpImpFileTypeEnum getFileType() {
        return null;
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        AnalysisReportDataExportExecutorParamDTO exportParamDTO = (AnalysisReportDataExportExecutorParamDTO)JsonUtils.readValue((String)context.getParam(), AnalysisReportDataExportExecutorParamDTO.class);
        String currType = exportParamDTO.getCurrType();
        if (StringUtils.isEmpty((String)currType)) {
            throw new BusinessRuntimeException("\u5bfc\u51fa\u6587\u4ef6\u7c7b\u578b\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        this.analysisReportDataService.exportFile(request, response, exportParamDTO);
        return null;
    }
}

