/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 */
package com.jiuqi.common.expimp.util;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.consts.ExcelConsts;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelExecutor;
import com.jiuqi.common.expimp.dataexport.service.ExportDispatchService;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

public class ExpImpUtils {
    public static SXSSFWorkbook getWorkbook(String dataExportExecutorName, String exportSn, String dataExportParam) {
        try {
            ExportDispatchService dispatchService = (ExportDispatchService)SpringContextUtils.getBean(ExportDispatchService.class);
            AbstractExportExcelExecutor dataExportExecutor = (AbstractExportExcelExecutor)dispatchService.findDataExportExecutor(dataExportExecutorName);
            ExportContext context = dispatchService.buildDataExportContext(false, dataExportParam, dataExportExecutorName, exportSn, false);
            SXSSFWorkbook workbook = dataExportExecutor.getWorkbook(context);
            return workbook;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException(e.getMessage(), (Throwable)e);
        }
    }

    public static CellStyle buildDefaultHeadCellStyle(Workbook workbook) {
        CellStyle headCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("\u5b8b\u4f53");
        font.setFontHeightInPoints((short)14);
        font.setItalic(false);
        font.setStrikeout(false);
        font.setColor((short)0);
        font.setTypeOffset((short)0);
        font.setUnderline((byte)0);
        font.setCharSet(0);
        font.setBold(true);
        headCellStyle.setFont(font);
        headCellStyle.setDataFormat((short)0);
        headCellStyle.setHidden(false);
        headCellStyle.setLocked(false);
        headCellStyle.setQuotePrefixed(false);
        headCellStyle.setAlignment(ExcelConsts.HeadCellStyle.horizontalAlignment);
        headCellStyle.setWrapText(false);
        headCellStyle.setVerticalAlignment(ExcelConsts.HeadCellStyle.verticalAlignment);
        headCellStyle.setRotation((short)0);
        headCellStyle.setIndention((short)0);
        headCellStyle.setBorderLeft(ExcelConsts.HeadCellStyle.borderLeft);
        headCellStyle.setBorderRight(ExcelConsts.HeadCellStyle.borderRight);
        headCellStyle.setBorderTop(ExcelConsts.HeadCellStyle.borderTop);
        headCellStyle.setBorderBottom(ExcelConsts.HeadCellStyle.borderBottom);
        headCellStyle.setLeftBorderColor((short)0);
        headCellStyle.setRightBorderColor((short)0);
        headCellStyle.setTopBorderColor((short)0);
        headCellStyle.setBottomBorderColor((short)0);
        headCellStyle.setFillPattern(ExcelConsts.HeadCellStyle.fillPatternType);
        headCellStyle.setFillBackgroundColor((short)9);
        headCellStyle.setFillForegroundColor((short)22);
        headCellStyle.setShrinkToFit(false);
        return headCellStyle;
    }

    public static CellStyle buildDefaultContentCellStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("\u5b8b\u4f53");
        font.setFontHeightInPoints((short)13);
        font.setItalic(false);
        font.setStrikeout(false);
        font.setColor((short)0);
        font.setTypeOffset((short)0);
        font.setUnderline((byte)0);
        font.setCharSet(0);
        font.setBold(false);
        cellStyle.setFont(font);
        short stringFormat = workbook.createDataFormat().getFormat("@");
        cellStyle.setDataFormat(stringFormat);
        cellStyle.setHidden(false);
        cellStyle.setLocked(false);
        cellStyle.setQuotePrefixed(false);
        cellStyle.setAlignment(ExcelConsts.ContentCellStyle.horizontalAlignment);
        cellStyle.setWrapText(false);
        cellStyle.setVerticalAlignment(ExcelConsts.ContentCellStyle.verticalAlignment);
        cellStyle.setRotation((short)0);
        cellStyle.setIndention((short)0);
        cellStyle.setBorderLeft(ExcelConsts.ContentCellStyle.borderLeft);
        cellStyle.setBorderRight(ExcelConsts.ContentCellStyle.borderRight);
        cellStyle.setBorderTop(ExcelConsts.ContentCellStyle.borderTop);
        cellStyle.setBorderBottom(ExcelConsts.ContentCellStyle.borderBottom);
        cellStyle.setLeftBorderColor((short)0);
        cellStyle.setRightBorderColor((short)0);
        cellStyle.setTopBorderColor((short)0);
        cellStyle.setBottomBorderColor((short)0);
        cellStyle.setFillPattern(ExcelConsts.ContentCellStyle.fillPatternType);
        cellStyle.setFillBackgroundColor((short)9);
        cellStyle.setFillForegroundColor((short)9);
        cellStyle.setShrinkToFit(false);
        return cellStyle;
    }
}

