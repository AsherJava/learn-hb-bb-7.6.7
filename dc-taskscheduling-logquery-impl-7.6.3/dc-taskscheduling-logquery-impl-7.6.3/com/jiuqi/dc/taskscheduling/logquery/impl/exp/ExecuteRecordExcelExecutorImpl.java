/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor
 *  com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.taskscheduling.logquery.impl.exp;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.dc.taskscheduling.logquery.client.dto.LogManagerDTO;
import com.jiuqi.dc.taskscheduling.logquery.impl.service.LogManagerService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExecuteRecordExcelExecutorImpl
extends AbstractExportExcelExecutor {
    @Autowired
    private LogManagerService service;

    public String getName() {
        return "ExecuteRecordExcelExecutor";
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        context.getProgressData().setProgressValueAndRefresh(0.1);
        String excuteParam = context.getParam();
        LogManagerDTO dto = (LogManagerDTO)JsonUtils.readValue((String)excuteParam, LogManagerDTO.class);
        this.service.exportExecuteRecordByCondition(response, dto);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return "\u5bfc\u51fa\u6210\u529f";
    }
}

