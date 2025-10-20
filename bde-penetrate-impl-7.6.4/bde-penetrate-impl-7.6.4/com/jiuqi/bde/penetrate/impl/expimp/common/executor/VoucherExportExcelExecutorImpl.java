/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.bde.penetrate.impl.expimp.common.executor;

import com.jiuqi.bde.penetrate.impl.expimp.common.PenetrateExcelExportParam;
import com.jiuqi.bde.penetrate.impl.service.CommonPenetrateExportExcelService;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VoucherExportExcelExecutorImpl
extends AbstractExportExcelExecutor {
    @Autowired
    CommonPenetrateExportExcelService exportExcelService;

    public String getName() {
        return "CommonVoucherExportExcelExecutor";
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        PenetrateExcelExportParam param = (PenetrateExcelExportParam)JsonUtils.readValue((String)context.getParam(), PenetrateExcelExportParam.class);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        String exportSituation = this.exportExcelService.exportExcel(response, context.isTemplateExportFlag(), param);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return exportSituation;
    }
}

