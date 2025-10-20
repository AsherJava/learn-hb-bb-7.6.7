/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 */
package com.jiuqi.dc.adjustvchr.impl.impexp;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.impl.service.AdjustVoucherImpExpService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdjustVoucherExcelExecutorImpl
extends AbstractExportExcelExecutor {
    @Autowired
    private AdjustVoucherImpExpService adjustVoucherImpExpService;

    public String getName() {
        return "AdjustVchrExportExcelExecutor";
    }

    public Object dataExport(HttpServletRequest request, HttpServletResponse response, ExportContext context) throws Exception {
        context.getProgressData().setProgressValueAndRefresh(0.1);
        AdjustVoucherQueryDTO adjustVoucherQueryDTO = (AdjustVoucherQueryDTO)JsonUtils.readValue((String)context.getParam(), AdjustVoucherQueryDTO.class);
        this.adjustVoucherImpExpService.exportAdjustVoucher(response, adjustVoucherQueryDTO, context.isTemplateExportFlag());
        context.getProgressData().setProgressValueAndRefresh(0.8);
        return "\u5bfc\u51fa\u6210\u529f";
    }
}

