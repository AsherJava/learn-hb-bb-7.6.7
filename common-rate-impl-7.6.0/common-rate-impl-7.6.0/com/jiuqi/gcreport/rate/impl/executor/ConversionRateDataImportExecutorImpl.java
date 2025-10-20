/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor
 */
package com.jiuqi.gcreport.rate.impl.executor;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.rate.impl.executor.ConversionRateDataImportParam;
import com.jiuqi.gcreport.rate.impl.service.RateExportImportService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionRateDataImportExecutorImpl
extends AbstractImportExcelMultiSheetExecutor {
    @Autowired
    private RateExportImportService service;

    public String getName() {
        return "ConversionRateDataImportExecutor";
    }

    protected Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        ConversionRateDataImportParam importParam = (ConversionRateDataImportParam)JsonUtils.readValue((String)context.getParam(), ConversionRateDataImportParam.class);
        String rateSchemeCode = importParam.getRateSchemeCode();
        this.service.rateUpload(rateSchemeCode, excelSheets);
        return null;
    }

    protected int[] getReadSheetNos() {
        return null;
    }
}

