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
package com.jiuqi.dc.base.common.intf.impl;

import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class SimpleHorizontalCellStyleStrategy
extends AbstractCellStyleStrategy {
    private WriteCellStyle headWriteCellStyle;
    private WriteCellStyle contentWriteCellStyle;
    private CellStyle headCellStyle;
    private CellStyle contentCellStyle;

    public SimpleHorizontalCellStyleStrategy() {
        this.headWriteCellStyle = this.getHeadCellStyle();
        this.contentWriteCellStyle = this.getContentCellStyle();
    }

    public SimpleHorizontalCellStyleStrategy(WriteCellStyle headWriteCellStyle, WriteCellStyle contentWriteCellStyle) {
        this.headWriteCellStyle = headWriteCellStyle;
        this.contentWriteCellStyle = contentWriteCellStyle;
    }

    private WriteCellStyle getHeadCellStyle() {
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.GREY_25_PERCENT.getIndex()));
        headWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("\u5b8b\u4f53");
        headWriteFont.setFontHeightInPoints(Short.valueOf((short)14));
        headWriteFont.setBold(Boolean.valueOf(true));
        headWriteCellStyle.setWriteFont(headWriteFont);
        headWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        headWriteCellStyle.setBottomBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        headWriteCellStyle.setLeftBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setBorderRight(BorderStyle.THIN);
        headWriteCellStyle.setRightBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setBorderTop(BorderStyle.THIN);
        headWriteCellStyle.setTopBorderColor(Short.valueOf((short)0));
        headWriteCellStyle.setWrapped(Boolean.valueOf(true));
        headWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        headWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return headWriteCellStyle;
    }

    private WriteCellStyle getContentCellStyle() {
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setFillForegroundColor(Short.valueOf(IndexedColors.WHITE.getIndex()));
        contentWriteCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setBold(Boolean.valueOf(false));
        headWriteFont.setFontName("\u5b8b\u4f53");
        headWriteFont.setFontHeightInPoints(Short.valueOf((short)13));
        contentWriteCellStyle.setWriteFont(headWriteFont);
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

    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (this.headCellStyle == null) {
            this.headCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.headWriteCellStyle);
        }
        context.getCell().setCellStyle(this.headCellStyle);
    }

    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (this.contentCellStyle == null) {
            this.contentCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.contentWriteCellStyle);
        }
        context.getCell().setCellStyle(this.contentCellStyle);
    }
}

