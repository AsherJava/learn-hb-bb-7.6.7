/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.unit.uselector.filter.listselect;

import com.jiuqi.nr.unit.uselector.filter.listselect.FConditionTemplateExport;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableConditionRow;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableDataSet;
import com.jiuqi.nr.unit.uselector.filter.listselect.FTableUnitRow;
import java.awt.Color;
import java.util.List;
import java.util.stream.Collectors;
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

public class FConditionResultExport
extends FConditionTemplateExport {
    private static String[] columnNames = new String[]{"\u4ee3\u7801[CODE]", "\u540d\u79f0[TITLE]", "\u5355\u4f4d\u4ee3\u7801[DWDM]", "\u5355\u4f4d\u540d\u79f0[DWMC]"};
    private static XSSFColor[] colorIndex = new XSSFColor[]{titleBackGroundColor, new XSSFColor(Color.decode("#e2e2e2"), (IndexedColorMap)new DefaultIndexedColorMap()), new XSSFColor(Color.decode("#ffffff"), (IndexedColorMap)new DefaultIndexedColorMap())};
    private static XSSFCellStyle titleCellStyle;
    private static XSSFCellStyle valueCellStyle1;
    private static XSSFCellStyle valueCellStyle2;
    private FTableDataSet tableDataSet;

    public FConditionResultExport(String entityName, FTableDataSet tableDataSet) {
        super(entityName);
        this.tableDataSet = tableDataSet;
    }

    @Override
    public String getFileName() {
        return "\u5355\u4f4d\u5339\u914d\u7ed3\u679c[" + this.entityName + "]" + xlsx_ext;
    }

    @Override
    public Workbook createWorkbook() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        titleCellStyle = this.madeTitleCellStyle(workbook, colorIndex[0]);
        valueCellStyle1 = this.madeValueCellStyle(workbook, colorIndex[1]);
        valueCellStyle2 = this.madeValueCellStyle(workbook, colorIndex[2]);
        this.buildExactMatchSheet(workbook);
        this.buildFuzzyMatchSheet(workbook);
        this.buildUnMatchSheet(workbook);
        return workbook;
    }

    private void buildExactMatchSheet(XSSFWorkbook workbook) {
        List<FTableConditionRow> rowData = this.getMatchTypeRow("T");
        XSSFSheet sheet = workbook.createSheet("\u5b8c\u5168\u5339\u914d");
        this.buildRowsWithConditions(sheet, rowData);
    }

    private void buildFuzzyMatchSheet(XSSFWorkbook workbook) {
        List<FTableConditionRow> rowData = this.getMatchTypeRow("F");
        XSSFSheet sheet = workbook.createSheet("\u6a21\u7cca\u5339\u914d");
        this.buildRowsWithConditions(sheet, rowData);
    }

    private void buildUnMatchSheet(XSSFWorkbook workbook) {
        List<FTableConditionRow> rowData = this.getMatchTypeRow("N");
        XSSFSheet sheet = workbook.createSheet("\u672a\u5339\u914d");
        this.buildRowsWithConditions(sheet, rowData);
    }

    private XSSFCellStyle madeTitleCellStyle(XSSFWorkbook workbook, XSSFColor color) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        XSSFFont font = this.getFont(workbook);
        font.setBold(true);
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        return cellStyle;
    }

    private XSSFCellStyle madeValueCellStyle(XSSFWorkbook workbook, XSSFColor color) {
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setFont(this.getFont(workbook));
        cellStyle.setAlignment(HorizontalAlignment.LEFT);
        cellStyle.setFillForegroundColor(color);
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        return cellStyle;
    }

    private List<FTableConditionRow> getMatchTypeRow(String matchType) {
        return this.tableDataSet.getTableData().stream().filter(r -> matchType.equals(r.getMatchType())).collect(Collectors.toList());
    }

    private void buildRowsWithConditions(XSSFSheet sheet, List<FTableConditionRow> rowData) {
        int rowIdx = 0;
        XSSFRow row = sheet.createRow(rowIdx++);
        for (int colIndex = 0; colIndex < columnNames.length; ++colIndex) {
            XSSFCell cell = row.createCell(colIndex, CellType.STRING);
            cell.getSheet().setColumnWidth(colIndex, new Double((double)columnNames[colIndex].getBytes().length * 1.5 * 256.0).intValue());
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue(columnNames[colIndex]);
        }
        for (int conditionNum = 0; conditionNum < rowData.size(); ++conditionNum) {
            rowIdx = this.buildRowsWithCondition(sheet, rowData.get(conditionNum), rowIdx, conditionNum);
        }
    }

    private int buildRowsWithCondition(XSSFSheet sheet, FTableConditionRow conditionRow, int rowIdx, int conditionNum) {
        List<FTableUnitRow> dataRows = conditionRow.getChildren();
        if (dataRows != null && !dataRows.isEmpty()) {
            for (FTableUnitRow unitRow : dataRows) {
                String[] cellValues = new String[]{conditionRow.getCode(), conditionRow.getTitle(), unitRow.getCode(), unitRow.getTitle()};
                this.buildOneRowData(sheet.createRow(rowIdx++), cellValues, conditionNum);
            }
        } else {
            String[] cellValues = new String[]{conditionRow.getCode(), conditionRow.getTitle(), "-", "-"};
            this.buildOneRowData(sheet.createRow(rowIdx++), cellValues, conditionNum);
        }
        return rowIdx;
    }

    private void buildOneRowData(XSSFRow sheetRow, String[] cellValues, int conditionNum) {
        int maxWidth = 65280;
        for (int colIndex = 0; colIndex < cellValues.length; ++colIndex) {
            XSSFCell cell = sheetRow.createCell(colIndex, CellType.STRING);
            int colWidth = new Double((double)cellValues[colIndex].getBytes().length * 1.5 * 256.0).intValue();
            if (colWidth > maxWidth) {
                colWidth = maxWidth;
            }
            if (colWidth > cell.getSheet().getColumnWidth(colIndex)) {
                cell.getSheet().setColumnWidth(colIndex, colWidth);
            }
            cell.setCellStyle(conditionNum % 2 == 0 ? valueCellStyle2 : valueCellStyle1);
            cell.setCellValue(cellValues[colIndex]);
        }
    }
}

