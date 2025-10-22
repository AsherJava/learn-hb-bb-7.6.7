/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.StringUtils
 *  com.jiuqi.common.expimp.converters.ExpImpConverter
 *  com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 */
package com.jiuqi.gcreport.invest.offsetitem.task.internal;

import com.jiuqi.common.base.util.StringUtils;
import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.converters.impl.DefaultExpImpConverter;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.util.CollectionUtils;

public class MultiTableExportExcelSheet
extends ExportExcelSheet {
    private String firstRegionLabel;
    private String secondRegionLabel;
    private final List<Object[]> firstRegionRowDatas = new CopyOnWriteArrayList<Object[]>();
    private final List<Object[]> secondRegionRowDatas = new CopyOnWriteArrayList<Object[]>();
    private int headerOffset = 0;

    public MultiTableExportExcelSheet(Integer sheetNo, String sheetName) {
        super(sheetNo, sheetName);
        this.setAutoMergeHeadFlag(false);
    }

    public void setFirstRegionLabel(String firstRegionLabel) {
        this.firstRegionLabel = firstRegionLabel;
    }

    public void setSecondRegionLabel(String secondRegionLabel) {
        this.secondRegionLabel = secondRegionLabel;
    }

    public List<Object[]> getFirstRegionRowDatas() {
        return this.firstRegionRowDatas;
    }

    public List<Object[]> getSecondRegionRowDatas() {
        return this.secondRegionRowDatas;
    }

    public void callBackSheet(ExportContext context, Workbook workbook, Sheet sheet) {
        this.writeRegion(context, workbook, sheet, this.firstRegionLabel, this.getFirstRegionRowDatas());
        this.writeEmptyRow();
        this.writeRegion(context, workbook, sheet, this.secondRegionLabel, this.getSecondRegionRowDatas());
        super.callBackSheet(context, workbook, sheet);
    }

    private void writeEmptyRow() {
        ++this.headerOffset;
    }

    private void writeRegion(ExportContext context, Workbook workbook, Sheet sheet, String regionLabel, List<Object[]> rowDatas) {
        AtomicInteger rowIndex = new AtomicInteger(this.headerOffset);
        if (!StringUtils.isEmpty((String)regionLabel)) {
            Row row = sheet.createRow(rowIndex.get());
            Cell cell = CellUtil.getCell(row, 0);
            cell.setCellStyle(this.getLeftCellStyle(workbook));
            cell.setCellType(CellType.STRING);
            this.writeCellValue(context, this, workbook, sheet, row, cell, regionLabel);
            sheet.addMergedRegion(new CellRangeAddress(rowIndex.get(), rowIndex.get(), 0, this.getColumnMaxIndex()));
            ++this.headerOffset;
            rowIndex.incrementAndGet();
        }
        for (Object[] rowData : rowDatas) {
            Row row = sheet.createRow(rowIndex.get());
            int rowNum = row.getRowNum();
            for (int columnIndex = 0; columnIndex < rowData.length; ++columnIndex) {
                CellType cellType;
                CellStyle cellStyle;
                Cell cell = CellUtil.getCell(row, columnIndex);
                if (rowNum < this.getSheetHeadSize() + this.headerOffset) {
                    cellStyle = (CellStyle)this.getHeadCellStyleCache().get(columnIndex);
                    cellType = CellType.STRING;
                } else {
                    cellStyle = (CellStyle)this.getContentCellStyleCache().get(columnIndex);
                    cellType = (CellType)((Object)this.getContentCellTypeCache().get(columnIndex));
                }
                cell.setCellStyle(cellStyle);
                cell.setCellType(cellType);
                Object cellValue = rowData[columnIndex];
                this.writeCellValue(context, this, workbook, sheet, row, cell, cellValue);
            }
            rowIndex.incrementAndGet();
        }
        this.headerOffset += rowDatas.size();
    }

    private void writeCellValue(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        int rowNum = row.getRowNum();
        int columnIndex = cell.getColumnIndex();
        Object formatCellValue = cellValue;
        if (rowNum >= excelSheet.getSheetHeadSize()) {
            ExpImpConverter converter = excelSheet.getConverter();
            if (converter == null) {
                converter = new DefaultExpImpConverter();
            }
            formatCellValue = converter.convertJavaToExcelData(context, excelSheet, rowNum, columnIndex, cellValue);
        }
        if (formatCellValue == null) {
            return;
        }
        CellType cellType = cell.getCellType();
        switch (cellType) {
            case NUMERIC: {
                if ("".equals(formatCellValue)) {
                    cell.setBlank();
                    return;
                }
                if (formatCellValue instanceof String) {
                    cell.setCellValue(Double.valueOf(formatCellValue.toString().replace(",", "")));
                    break;
                }
                cell.setCellValue(((Number)formatCellValue).doubleValue());
                break;
            }
            case BOOLEAN: {
                cell.setCellValue((Boolean)formatCellValue);
                break;
            }
            case BLANK: {
                cell.setBlank();
                break;
            }
            case FORMULA: {
                cell.setCellFormula(formatCellValue.toString());
                break;
            }
            case ERROR: {
                cell.setCellErrorValue((byte)Integer.valueOf(formatCellValue.toString()).intValue());
                break;
            }
            default: {
                cell.setCellValue(formatCellValue.toString());
            }
        }
    }

    public Integer getColumnMaxIndex() {
        if (CollectionUtils.isEmpty(this.firstRegionRowDatas)) {
            return -1;
        }
        return this.firstRegionRowDatas.get(0).length - 1;
    }

    public List<Object[]> getRowDatas() {
        return Collections.EMPTY_LIST;
    }

    private CellStyle getLeftCellStyle(Workbook workbook) {
        CellStyle headCell = this.buildDefaultHeadCellStyle(workbook);
        headCell.setAlignment(HorizontalAlignment.LEFT);
        return headCell;
    }

    private CellStyle buildDefaultHeadCellStyle(Workbook workbook) {
        CellStyle headCellStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setFontName("\u5b8b\u4f53");
        font.setFontHeightInPoints((short)9);
        font.setItalic(false);
        font.setStrikeout(false);
        font.setColor((short)0);
        font.setTypeOffset((short)0);
        font.setUnderline((byte)0);
        font.setCharSet(0);
        font.setBold(true);
        headCellStyle.setFont(font);
        return headCellStyle;
    }
}

