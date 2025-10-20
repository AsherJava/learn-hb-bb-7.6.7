/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor
 *  com.jiuqi.dc.datamapping.client.dto.DataRefListDTO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.datamapping.impl.expimp;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.dc.datamapping.client.dto.DataRefListDTO;
import com.jiuqi.dc.datamapping.impl.expimp.DataRefConfigureExpImpService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataRefConfigureExportExcelExecutorImpl
extends AbstractExportExcelExecutor {
    @Autowired
    private DataRefConfigureExpImpService expImpService;

    public String getName() {
        return "DataRefConfigureExportExcelExecutor";
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        DataRefListDTO dto = (DataRefListDTO)JsonUtils.readValue((String)context.getParam(), DataRefListDTO.class);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        String result = this.expImpService.exportExcel(response, context.isTemplateExportFlag(), dto);
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return result;
    }
}

