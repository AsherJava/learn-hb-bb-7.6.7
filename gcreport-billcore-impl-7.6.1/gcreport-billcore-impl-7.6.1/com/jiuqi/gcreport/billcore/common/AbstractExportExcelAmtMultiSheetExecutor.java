/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.gcreport.billcore.common;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractExportExcelAmtMultiSheetExecutor
extends AbstractExportExcelMultiSheetExecutor {
    protected List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        context.getVarMap().put("headAmt", this.getHeadAmtStyle(workbook));
        context.getVarMap().put("contentAmt", this.getContentAmtStyle(workbook));
        context.getVarMap().put("contentInt", this.getContentIntStyle(workbook));
        context.getVarMap().put("workbook", workbook);
        return this.exportExcelSheetsNew(context, workbook);
    }

    protected abstract List<ExportExcelSheet> exportExcelSheetsNew(ExportContext var1, Workbook var2);

    private CellStyle getHeadAmtStyle(Workbook workbook) {
        CellStyle headAmtStyle = this.buildDefaultHeadCellStyle(workbook);
        headAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        return headAmtStyle;
    }

    private CellStyle getContentAmtStyle(Workbook workbook) {
        CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
        contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
        return contentAmtStyle;
    }

    private CellStyle getContentIntStyle(Workbook workbook) {
        CellStyle contentAmtStyle = this.buildDefaultContentCellStyle(workbook);
        contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0"));
        return contentAmtStyle;
    }
}

