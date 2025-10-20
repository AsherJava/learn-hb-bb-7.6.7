/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.exp;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.dc.taskscheduling.logquery.impl.service.LogManagerService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SqlRecordExcelExecutorImpl
extends AbstractExportExcelExecutor {
    @Autowired
    private LogManagerService service;

    public String getName() {
        return "SqlRecordExcelExecutor";
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        context.getProgressData().setProgressValueAndRefresh(0.1);
        String taskItemId = context.getParam();
        this.service.exportSqlRecordByTaskItemId(response, taskItemId);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return "\u5bfc\u51fa\u6210\u529f";
    }
}

