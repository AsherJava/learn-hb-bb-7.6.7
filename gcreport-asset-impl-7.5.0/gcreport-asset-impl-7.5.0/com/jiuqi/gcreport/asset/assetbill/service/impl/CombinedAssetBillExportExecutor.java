/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 */
package com.jiuqi.gcreport.asset.assetbill.service.impl;

import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.asset.assetbill.service.CombinedAssetBillService;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CombinedAssetBillExportExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Autowired
    private CombinedAssetBillService combinedAssetBillService;

    public String getName() {
        return "CombinedAssetBillExportExecutor";
    }

    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        context.getVarMap().put("headAmt", this.getHeadAmtStyle(workbook));
        context.getVarMap().put("contentAmt", this.getContentAmtStyle(workbook));
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        List<ExportExcelSheet> excelSheets = this.combinedAssetBillService.getExcelSheets(context, params);
        return excelSheets;
    }

    private CellStyle getHeadAmtStyle(Workbook workbook) {
        CellStyle headAmtStyle = this.buildDefaultHeadCellStyle(workbook);
        headAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        return headAmtStyle;
    }

    private CellStyle getContentAmtStyle(Workbook workbook) {
        CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
        contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        return contentAmtStyle;
    }
}

