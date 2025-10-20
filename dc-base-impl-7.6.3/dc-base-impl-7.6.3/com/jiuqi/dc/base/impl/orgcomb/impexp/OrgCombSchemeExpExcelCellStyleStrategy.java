/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.util.StyleUtil
 *  com.alibaba.excel.write.handler.context.CellWriteHandlerContext
 *  com.alibaba.excel.write.metadata.style.WriteCellStyle
 *  com.alibaba.excel.write.metadata.style.WriteFont
 *  com.alibaba.excel.write.style.AbstractCellStyleStrategy
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.dc.base.impl.orgcomb.impexp;

import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class OrgCombSchemeExpExcelCellStyleStrategy
extends AbstractCellStyleStrategy {
    private WriteCellStyle contentWriteCellStyle = this.getContentCellStyle();
    private WriteCellStyle keyContentWriteCellStyle;
    private WriteCellStyle noRequiredKeyContentWriteCellStyle;
    private WriteCellStyle explainWriteCellStyle = this.getExplainCellStyle();
    private CellStyle contentCellStyle;
    private CellStyle keyContentCellStyle;
    private CellStyle noRequiredKeyContentCellStyle;
    private CellStyle explainCellStyle;

    public OrgCombSchemeExpExcelCellStyleStrategy(boolean exportTemplate) {
        this.keyContentWriteCellStyle = this.getKeyContentCellStyle();
        this.noRequiredKeyContentWriteCellStyle = this.getNoRequiredKeyContentCellStyle();
    }

    private WriteCellStyle getContentCellStyle() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.WHITE.getIndex()));
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(false));
        writeFont.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        writeFont.setFontHeightInPoints(Short.valueOf((short)13));
        contentWriteCellStyle.setWriteFont(writeFont);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        contentWriteCellStyle.setWrapped(Boolean.valueOf(true));
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return contentWriteCellStyle;
    }

    private WriteCellStyle getKeyContentCellStyle() {
        WriteCellStyle keyContentWriteCellStyle = new WriteCellStyle();
        keyContentWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.PALE_BLUE.getIndex()));
        keyContentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(false));
        writeFont.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        writeFont.setFontHeightInPoints(Short.valueOf((short)13));
        keyContentWriteCellStyle.setWriteFont(writeFont);
        keyContentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        keyContentWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        keyContentWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        keyContentWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        keyContentWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setWrapped(Boolean.valueOf(true));
        keyContentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        keyContentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        keyContentWriteCellStyle.getWriteFont().setColor(Short.valueOf(IndexedColors.RED.getIndex()));
        return keyContentWriteCellStyle;
    }

    private WriteCellStyle getNoRequiredKeyContentCellStyle() {
        WriteCellStyle keyContentWriteCellStyle = new WriteCellStyle();
        keyContentWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.PALE_BLUE.getIndex()));
        keyContentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(false));
        writeFont.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        writeFont.setFontHeightInPoints(Short.valueOf((short)13));
        keyContentWriteCellStyle.setWriteFont(writeFont);
        keyContentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        keyContentWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        keyContentWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        keyContentWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        keyContentWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        keyContentWriteCellStyle.setWrapped(Boolean.valueOf(true));
        keyContentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        keyContentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return keyContentWriteCellStyle;
    }

    private WriteCellStyle getExplainCellStyle() {
        WriteCellStyle memoWriteCellStyle = new WriteCellStyle();
        memoWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.WHITE.getIndex()));
        memoWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        writeFont.setFontHeightInPoints(Short.valueOf((short)13));
        memoWriteCellStyle.setWriteFont(writeFont);
        memoWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        memoWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        memoWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        memoWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        memoWriteCellStyle.setBorderRight(BorderStyle.THIN);
        memoWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        memoWriteCellStyle.setBorderTop(BorderStyle.THIN);
        memoWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        memoWriteCellStyle.setWrapped(Boolean.valueOf(true));
        memoWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        memoWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        memoWriteCellStyle.getWriteFont().setColor(Short.valueOf(IndexedColors.RED.getIndex()));
        return memoWriteCellStyle;
    }

    protected void setHeadCellStyle(CellWriteHandlerContext context) {
    }

    protected void setContentCellStyle(CellWriteHandlerContext context) {
        Cell cell;
        if (this.contentCellStyle == null) {
            this.contentCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.contentWriteCellStyle);
        }
        if (this.explainCellStyle == null) {
            this.explainCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.explainWriteCellStyle);
        }
        if (this.keyContentCellStyle == null) {
            this.keyContentCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.keyContentWriteCellStyle);
        }
        if (this.noRequiredKeyContentCellStyle == null) {
            this.noRequiredKeyContentCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.noRequiredKeyContentWriteCellStyle);
        }
        if ((cell = context.getCell()).getRowIndex() <= 2) {
            if (cell.getRowIndex() == 2) {
                cell.setCellStyle(cell.getColumnIndex() == 0 ? this.noRequiredKeyContentCellStyle : this.contentCellStyle);
            } else {
                cell.setCellStyle(cell.getColumnIndex() == 0 ? this.keyContentCellStyle : this.contentCellStyle);
            }
        } else if (cell.getRowIndex() == 3) {
            cell.setCellStyle(cell.getColumnIndex() == 0 ? this.keyContentCellStyle : (cell.getColumnIndex() == 2 ? this.contentCellStyle : this.noRequiredKeyContentCellStyle));
        } else {
            cell.setCellStyle(cell.getColumnIndex() == 2 ? this.explainCellStyle : this.contentCellStyle);
        }
    }
}

