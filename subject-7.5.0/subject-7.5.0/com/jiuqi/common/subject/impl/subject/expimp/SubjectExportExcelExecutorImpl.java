/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.common.subject.impl.subject.expimp;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.common.subject.impl.subject.service.SubjectExpImpService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SubjectExportExcelExecutorImpl
extends AbstractExportExcelExecutor {
    @Autowired
    private SubjectExpImpService expImpService;

    public String getName() {
        return "AcctSubjectExportExcelExecutor";
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        context.getProgressData().setProgressValueAndRefresh(0.1);
        String result = this.expImpService.exportExcel(response, context.isTemplateExportFlag());
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return result;
    }
}

