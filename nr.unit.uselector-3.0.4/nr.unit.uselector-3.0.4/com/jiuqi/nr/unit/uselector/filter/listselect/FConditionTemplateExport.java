/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.nr.unit.uselector.dataio.IExcelWriteWorker;
import java.awt.Color;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class FConditionTemplateExport
implements IExcelWriteWorker {
    protected static String xlsx_ext = ".xlsx";
    protected static XSSFColor titleBackGroundColor = new XSSFColor(Color.decode("#e2e2e2"), (IndexedColorMap)new DefaultIndexedColorMap());
    public static final String[] columnNames = new String[]{"\u4ee3\u7801[CODE]", "\u540d\u79f0[TITLE]"};
    protected String entityName;

    public FConditionTemplateExport(String entityName) {
        this.entityName = entityName;
    }

    @Override
    public String getFileName() {
        return "\u3010" + this.entityName + "\u3011\u7b5b\u9009\u6a21\u677f" + xlsx_ext;
    }

    @Override
    public Workbook createWorkbook() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(this.getFileName());
        XSSFRow row = sheet.createRow(0);
        for (int colIndex = 0; colIndex < columnNames.length; ++colIndex) {
            sheet.setColumnWidth(colIndex, columnNames[colIndex].getBytes().length * 256 + 200);
            XSSFCell cell = row.createCell(colIndex, CellType.STRING);
            cell.setCellValue(columnNames[colIndex]);
            cell.setCellStyle(this.madeCellStyle(workbook));
        }
        return workbook;
    }

    protected XSSFFont getFont(XSSFWorkbook wb) {
        XSSFFont font = wb.createFont();
        font.setFontName("\u9ed1\u4f53");
        font.setFontHeightInPoints((short)12);
        return font;
    }

    private XSSFCellStyle madeCellStyle(XSSFWorkbook workbook) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = this.getFont(workbook);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(titleBackGroundColor);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
    }
}

