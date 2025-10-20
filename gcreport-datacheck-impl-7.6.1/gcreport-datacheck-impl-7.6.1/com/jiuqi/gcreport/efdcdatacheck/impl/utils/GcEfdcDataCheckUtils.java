/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.excel.consts.ExcelConsts$ContentCellStyle
 *  com.jiuqi.common.expimp.dataexport.excel.consts.ExcelConsts$HeadCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFCellStyle
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.hssf.usermodel.HSSFWorkbook
 *  org.apache.poi.hssf.util.HSSFColor$HSSFColorPredefined
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.Font
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.DefaultIndexedColorMap
 *  org.apache.poi.xssf.usermodel.IndexedColorMap
 *  org.apache.poi.xssf.usermodel.XSSFCellStyle
 *  org.apache.poi.xssf.usermodel.XSSFColor
 */
package com.jiuqi.gcreport.efdcdatacheck.impl.utils;

import com.jiuqi.common.expimp.dataexport.excel.consts.ExcelConsts;
import java.awt.Color;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.springframework.stereotype.Component;

@Component
public class GcEfdcDataCheckUtils {
    private ThreadLocal<Map<String, CellStyle>> threadLocal = new ThreadLocal();

    public static HSSFCellStyle getExelCellStyle(HSSFWorkbook workbook, String cellType) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        switch (cellType) {
            case "head": {
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.ORANGE.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                break;
            }
            case "text": {
                cellStyle.setAlignment(HorizontalAlignment.LEFT);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                break;
            }
            case "amount": {
                cellStyle.setAlignment(HorizontalAlignment.RIGHT);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
                cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
                break;
            }
            case "merge": {
                cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.BLUE.getIndex());
                cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
                cellStyle.setBorderBottom(BorderStyle.THIN);
                cellStyle.setBorderLeft(BorderStyle.THIN);
                cellStyle.setBorderRight(BorderStyle.THIN);
                cellStyle.setBorderTop(BorderStyle.THIN);
            }
        }
        return cellStyle;
    }

    public Map<String, CellStyle> getCellStyleMap(Workbook workbook) {
        Map<String, CellStyle> cellStyleMap = this.threadLocal.get();
        if (cellStyleMap == null) {
            cellStyleMap = new ConcurrentHashMap<String, CellStyle>();
            CellStyle headTextStyle = this.buildDefaultHeadCellStyle(workbook);
            headTextStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("headText", headTextStyle);
            CellStyle headAmountStyle = this.buildDefaultHeadCellStyle(workbook);
            headAmountStyle.setAlignment(HorizontalAlignment.RIGHT);
            cellStyleMap.put("headAmount", headAmountStyle);
            CellStyle contentTextStyle = this.buildDefaultContentCellStyle(workbook);
            contentTextStyle.setAlignment(HorizontalAlignment.LEFT);
            cellStyleMap.put("contentText", contentTextStyle);
            CellStyle contentAmountStyle = this.buildDefaultContentCellStyle(workbook);
            contentAmountStyle.setAlignment(HorizontalAlignment.RIGHT);
            contentAmountStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("contentAmount", contentAmountStyle);
            XSSFCellStyle intervalColorString = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorString.setAlignment(HorizontalAlignment.LEFT);
            intervalColorString.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cellStyleMap.put("intervalColorText", (CellStyle)intervalColorString);
            XSSFCellStyle intervalColorAmt = (XSSFCellStyle)this.buildDefaultContentCellStyle(workbook);
            intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
            intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(239, 248, 254), (IndexedColorMap)new DefaultIndexedColorMap()));
            intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
            cellStyleMap.put("intervalColorAmount", (CellStyle)intervalColorAmt);
            this.threadLocal.set(cellStyleMap);
        }
        return cellStyleMap;
    }

    private CellStyle buildDefaultHeadCellStyle(Workbook workbook) {
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

    private CellStyle buildDefaultContentCellStyle(Workbook workbook) {
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
        cellStyle.setDataFormat((short)0);
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

