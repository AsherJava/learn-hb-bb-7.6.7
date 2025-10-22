/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.impl.util;

import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;

public class MonitorEcxelUtil {
    public static HSSFWorkbook exportCell(String sheetName, List<String> valueColumns1, List<String> valueColumns2, List<List<String>> list, List<Integer[]> list3) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet(sheetName);
        HSSFCellStyle cellStyle = MonitorEcxelUtil.getCellStyle(workbook);
        HSSFRow hssfRow = sheet.createRow(0);
        MonitorEcxelUtil.setTitle(valueColumns1, hssfRow, workbook);
        hssfRow = sheet.createRow(1);
        MonitorEcxelUtil.setTitle(valueColumns2, hssfRow, workbook);
        for (Integer[] arr : list3) {
            sheet.addMergedRegion(new CellRangeAddress(arr[0], arr[1], arr[2], arr[3]));
        }
        for (int i = 0; i < list.size(); ++i) {
            hssfRow = sheet.createRow(i + 2);
            for (int j = 0; j < list.get(i).size(); ++j) {
                HSSFCell cell = hssfRow.createCell(j);
                cell.setCellValue(list.get(i).get(j));
                cell.setCellStyle(cellStyle);
            }
        }
        sheet.setColumnWidth(0, 12800);
        return workbook;
    }

    private static void setTitle(List<String> valueColumns, HSSFRow hssfRow, HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = MonitorEcxelUtil.getCellStyle(workbook);
        for (int i = 0; i < valueColumns.size(); ++i) {
            HSSFCell headCell = hssfRow.createCell(i);
            headCell.setCellValue(valueColumns.get(i));
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            HSSFFont font = workbook.createFont();
            font.setFontHeight((short)6);
            font.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
            font.setColor(HSSFColor.HSSFColorPredefined.BLACK.getIndex());
            font.setFontHeight((short)200);
            font.setBold(true);
            cellStyle.setFont((Font)font);
            cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.GREY_25_PERCENT.getIndex());
            cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            headCell.setCellStyle(cellStyle);
        }
    }

    private static HSSFCellStyle getCellStyle(HSSFWorkbook workbook) {
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return cellStyle;
    }
}

