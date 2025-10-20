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
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.common.subject.impl.subject.expimp.style;

import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.jiuqi.common.subject.impl.subject.expimp.intf.impl.SubjectFieldDefineHolder;
import java.util.HashSet;
import java.util.Set;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class SubjectExpExcelCellStyleStrategy
extends AbstractCellStyleStrategy {
    private WriteCellStyle headWriteCellStyle;
    private WriteCellStyle requiredHeadWriteCellStyle;
    private WriteCellStyle contentWriteCellStyle;
    private WriteCellStyle memoWriteCellStyle;
    private CellStyle headCellStyle;
    private CellStyle requiredHeadCellStyle;
    private CellStyle contentCellStyle;
    private CellStyle memoCellStyle;
    private boolean exportTemplate;
    private Set<Integer> requiredColIndexSet;

    public SubjectExpExcelCellStyleStrategy(SubjectFieldDefineHolder fieldDefineHolder, boolean exportTemplate) {
        this.exportTemplate = exportTemplate;
        this.headWriteCellStyle = this.getHeadCellStyle();
        this.contentWriteCellStyle = this.getContentCellStyle();
        this.requiredHeadWriteCellStyle = this.getRequiredHeadCellStyle();
        this.memoWriteCellStyle = this.getMemoCellStyle();
        this.requiredColIndexSet = new HashSet<Integer>(fieldDefineHolder.getDefineList().size());
        for (int idx = 0; idx < fieldDefineHolder.getDefineList().size(); ++idx) {
            if (!fieldDefineHolder.getDefineList().get(idx).isRequired()) continue;
            this.requiredColIndexSet.add(idx);
        }
    }

    private WriteCellStyle getHeadCellStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.GREY_25_PERCENT.getIndex()));
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setFontName("\u5b8b\u4f53");
        writeFont.setFontHeightInPoints(Short.valueOf((short)14));
        writeFont.setBold(Boolean.valueOf(true));
        headWriteCellStyle.setWriteFont(writeFont);
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        headWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        headWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);
        headWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);
        headWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setWrapped(Boolean.valueOf(false));
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return headWriteCellStyle;
    }

    private WriteCellStyle getRequiredHeadCellStyle() {
        WriteCellStyle requiredHeadWriteCellStyle = new WriteCellStyle();
        requiredHeadWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.GREY_25_PERCENT.getIndex()));
        requiredHeadWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setFontName("\u5b8b\u4f53");
        writeFont.setFontHeightInPoints(Short.valueOf((short)14));
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setColor(Short.valueOf(IndexedColors.RED.getIndex()));
        requiredHeadWriteCellStyle.setWriteFont(writeFont);
        requiredHeadWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        requiredHeadWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        requiredHeadWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        requiredHeadWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        requiredHeadWriteCellStyle.setBorderRight(BorderStyle.THIN);
        requiredHeadWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        requiredHeadWriteCellStyle.setBorderTop(BorderStyle.THIN);
        requiredHeadWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        requiredHeadWriteCellStyle.setWrapped(Boolean.valueOf(false));
        requiredHeadWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        requiredHeadWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return requiredHeadWriteCellStyle;
    }

    private WriteCellStyle getContentCellStyle() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillForegroundColor(Short.valueOf(this.exportTemplate ? IndexedColors.YELLOW.getIndex() : IndexedColors.WHITE.getIndex()));
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(false));
        writeFont.setFontName("\u5b8b\u4f53");
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
        contentWriteCellStyle.setWrapped(Boolean.valueOf(false));
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return contentWriteCellStyle;
    }

    private WriteCellStyle getMemoCellStyle() {
        WriteCellStyle memoWriteCellStyle = new WriteCellStyle();
        memoWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.YELLOW.getIndex()));
        memoWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont writeFont = new WriteFont();
        writeFont.setBold(Boolean.valueOf(true));
        writeFont.setFontName("\u5fae\u8f6f\u96c5\u9ed1");
        writeFont.setFontHeightInPoints(Short.valueOf((short)10));
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
        memoWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
        memoWriteCellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        return memoWriteCellStyle;
    }

    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (this.headCellStyle == null) {
            this.headCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.headWriteCellStyle);
        }
        if (this.requiredHeadCellStyle == null) {
            this.requiredHeadCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.requiredHeadWriteCellStyle);
        }
        if (this.requiredColIndexSet.contains(context.getCell().getColumnIndex())) {
            context.getCell().setCellStyle(this.requiredHeadCellStyle);
        } else {
            context.getCell().setCellStyle(this.headCellStyle);
        }
    }

    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (this.contentCellStyle == null) {
            this.contentCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.contentWriteCellStyle);
        }
        if (this.memoCellStyle == null) {
            this.memoCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.memoWriteCellStyle);
        }
        if (this.exportTemplate && context.getCell().getRowIndex() == 1) {
            context.getCell().setCellStyle(this.memoCellStyle);
        } else {
            context.getCell().setCellStyle(this.contentCellStyle);
        }
    }
}

