/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor
 *  com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO
 */
package com.jiuqi.dc.adjustvchr.impl.impexp;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor;
import com.jiuqi.dc.adjustvchr.client.dto.AdjustVoucherQueryDTO;
import com.jiuqi.dc.adjustvchr.impl.service.AdjustVoucherImpExpService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AdjustVoucherImportExcelExecutorImpl
extends AbstractImportExcelOneSheetExecutor {
    @Autowired
    private AdjustVoucherImpExpService impExpService;

    public String getName() {
        return "AdjustVchrImportExcelExecutor";
    }

    protected Object importExcelSheet(ImportContext context, List<Object[]> excelDatas) {
        if (excelDatas == null || excelDatas.isEmpty()) {
            return "Excel\u4e2d\u6ca1\u6709\u6570\u636e\uff0c\u5bfc\u5165\u5931\u8d25";
        }
        AdjustVoucherQueryDTO importParam = (AdjustVoucherQueryDTO)JsonUtils.readValue((String)context.getParam(), AdjustVoucherQueryDTO.class);
        context.getProgressData().setProgressValueAndRefresh(0.1);
        return this.impExpService.importAdjustVoucher(excelDatas, importParam);
    }
}

