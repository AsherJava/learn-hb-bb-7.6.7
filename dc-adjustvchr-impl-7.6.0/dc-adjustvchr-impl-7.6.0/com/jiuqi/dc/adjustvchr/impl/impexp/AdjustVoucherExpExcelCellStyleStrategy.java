/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.metadata.Head
 *  com.alibaba.excel.util.StyleUtil
 *  com.alibaba.excel.write.handler.context.CellWriteHandlerContext
 *  com.alibaba.excel.write.metadata.style.WriteCellStyle
 *  com.alibaba.excel.write.metadata.style.WriteFont
 *  com.alibaba.excel.write.style.AbstractCellStyleStrategy
 *  org.apache.poi.hssf.usermodel.HSSFDataFormat
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.dc.adjustvchr.impl.impexp;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import java.util.Objects;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class AdjustVoucherExpExcelCellStyleStrategy
extends AbstractCellStyleStrategy {
    private WriteCellStyle timeWriteCellStyle;
    private WriteCellStyle contextWriteCellStyle;
    private WriteCellStyle moneyWriteCellStyle;
    private WriteCellStyle remarkWriteCellStyle;
    private WriteCellStyle exampleWriteCellStyle;
    private boolean exportTemplate;
    private CellStyle timeCellStyle;
    private CellStyle contextCellStyle;
    private CellStyle moneyCellStyle;
    private CellStyle remarkCellStyle;
    private CellStyle exampleCellStyle;

    public AdjustVoucherExpExcelCellStyleStrategy(boolean exportTemplate) {
        this.exportTemplate = exportTemplate;
        this.timeWriteCellStyle = this.getTimeCellStyle();
        this.contextWriteCellStyle = this.getContextCellStyle();
        this.moneyWriteCellStyle = this.getMoneyCellStyle();
        this.remarkWriteCellStyle = this.getRemarkCellStyle();
        this.exampleWriteCellStyle = this.getExampleCellStyle();
    }

    private WriteCellStyle getTimeCellStyle() {
        WriteCellStyle timeWriteCellStyle = new WriteCellStyle();
        timeWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.WHITE.getIndex()));
        timeWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setFontName("\u7b49\u7ebf");
        writeFont.setFontHeightInPoints(Short.valueOf((short)50));
        timeWriteCellStyle.setWriteFont(writeFont);
        timeWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        timeWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        timeWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        timeWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        timeWriteCellStyle.setBorderRight(BorderStyle.THIN);
        timeWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        timeWriteCellStyle.setBorderTop(BorderStyle.THIN);
        timeWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        timeWriteCellStyle.setWrapped(Boolean.valueOf(true));
        timeWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        timeWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return timeWriteCellStyle;
    }

    private WriteCellStyle getContextCellStyle() {
        WriteCellStyle contextWriteCellStyle = new WriteCellStyle();
        contextWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.WHITE.getIndex()));
        contextWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setFontName("\u7b49\u7ebf");
        writeFont.setFontHeightInPoints(Short.valueOf((short)50));
        contextWriteCellStyle.setWriteFont(writeFont);
        contextWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contextWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        contextWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contextWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        contextWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contextWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        contextWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contextWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        contextWriteCellStyle.setWrapped(Boolean.valueOf(true));
        contextWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        contextWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return contextWriteCellStyle;
    }

    private WriteCellStyle getMoneyCellStyle() {
        WriteCellStyle moneyWriteCellStyle = new WriteCellStyle();
        moneyWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.WHITE.getIndex()));
        moneyWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setFontName("\u7b49\u7ebf");
        writeFont.setFontHeightInPoints(Short.valueOf((short)50));
        moneyWriteCellStyle.setWriteFont(writeFont);
        moneyWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        moneyWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        moneyWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        moneyWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        moneyWriteCellStyle.setBorderRight(BorderStyle.THIN);
        moneyWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        moneyWriteCellStyle.setBorderTop(BorderStyle.THIN);
        moneyWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        moneyWriteCellStyle.setWrapped(Boolean.valueOf(true));
        moneyWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        moneyWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return moneyWriteCellStyle;
    }

    private WriteCellStyle getRemarkCellStyle() {
        WriteCellStyle remarkWriteCellStyle = new WriteCellStyle();
        remarkWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.PALE_BLUE.getIndex()));
        remarkWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setFontName("\u7b49\u7ebf");
        writeFont.setFontHeightInPoints(Short.valueOf((short)15));
        remarkWriteCellStyle.setWriteFont(writeFont);
        remarkWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        remarkWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        remarkWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        remarkWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        remarkWriteCellStyle.setBorderRight(BorderStyle.THIN);
        remarkWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        remarkWriteCellStyle.setBorderTop(BorderStyle.THIN);
        remarkWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        remarkWriteCellStyle.setWrapped(Boolean.valueOf(true));
        remarkWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        remarkWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return remarkWriteCellStyle;
    }

    private WriteCellStyle getExampleCellStyle() {
        WriteCellStyle exampleWriteCellStyle = new WriteCellStyle();
        exampleWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.GREY_25_PERCENT.getIndex()));
        exampleWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setFontName("\u7b49\u7ebf");
        writeFont.setFontHeightInPoints(Short.valueOf((short)50));
        exampleWriteCellStyle.setWriteFont(writeFont);
        exampleWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        exampleWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        exampleWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        exampleWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        exampleWriteCellStyle.setBorderRight(BorderStyle.THIN);
        exampleWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        exampleWriteCellStyle.setBorderTop(BorderStyle.THIN);
        exampleWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        exampleWriteCellStyle.setWrapped(Boolean.valueOf(true));
        exampleWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        exampleWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return exampleWriteCellStyle;
    }

    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (Objects.isNull(this.timeCellStyle)) {
            this.timeCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.timeWriteCellStyle);
        }
        if (Objects.isNull(this.contextCellStyle)) {
            this.contextCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.contextWriteCellStyle);
        }
        if (Objects.isNull(this.moneyCellStyle)) {
            this.moneyCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.moneyWriteCellStyle);
            this.moneyCellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat((String)"#,##0.00"));
        }
        if (Objects.isNull(this.remarkCellStyle)) {
            this.remarkCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.remarkWriteCellStyle);
        }
        if (Objects.isNull(this.exampleCellStyle)) {
            this.exampleCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.exampleWriteCellStyle);
        }
    }

    protected void setContentCellStyle(Cell cell, Head head, Integer relativeRowIndex) {
        if (head != null) {
            if (this.exportTemplate && (cell.getRowIndex() == 1 || cell.getRowIndex() == 2 || cell.getRowIndex() == 3)) {
                cell.setCellStyle(this.remarkCellStyle);
            } else {
                String headName = (String)head.getHeadNameList().get(0);
                if ("\u64cd\u4f5c\u65f6\u95f4".equals(headName) || "\u4fee\u6539\u65f6\u95f4".equals(headName)) {
                    cell.setCellStyle(this.timeCellStyle);
                } else if (headName.contains("\u91d1\u989d")) {
                    cell.setCellStyle(this.moneyCellStyle);
                } else {
                    cell.setCellStyle(this.contextCellStyle);
                }
            }
        }
    }
}

