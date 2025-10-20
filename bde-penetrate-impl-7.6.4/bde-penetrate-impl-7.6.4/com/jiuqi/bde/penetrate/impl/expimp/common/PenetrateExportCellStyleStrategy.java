/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.alibaba.excel.util.StyleUtil
 *  com.alibaba.excel.write.handler.context.CellWriteHandlerContext
 *  com.alibaba.excel.write.metadata.style.WriteCellStyle
 *  com.alibaba.excel.write.metadata.style.WriteFont
 *  com.alibaba.excel.write.style.AbstractCellStyleStrategy
 *  com.jiuqi.bde.common.constant.ColumnEnum
 *  com.jiuqi.bde.common.intf.PenetrateColumn
 *  org.apache.poi.ss.usermodel.BorderStyle
 *  org.apache.poi.ss.usermodel.Cell
 *  org.apache.poi.ss.usermodel.CellStyle
 *  org.apache.poi.ss.usermodel.CellType
 *  org.apache.poi.ss.usermodel.FillPatternType
 *  org.apache.poi.ss.usermodel.HorizontalAlignment
 *  org.apache.poi.ss.usermodel.IndexedColors
 *  org.apache.poi.ss.usermodel.Sheet
 *  org.apache.poi.ss.usermodel.VerticalAlignment
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.bde.penetrate.impl.expimp.common;

import com.alibaba.excel.util.StyleUtil;
import com.alibaba.excel.write.handler.context.CellWriteHandlerContext;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.AbstractCellStyleStrategy;
import com.jiuqi.bde.common.constant.ColumnEnum;
import com.jiuqi.bde.common.intf.PenetrateColumn;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

public class PenetrateExportCellStyleStrategy
extends AbstractCellStyleStrategy {
    private WriteCellStyle headWriteCellStyle = this.getHeadCellStyle();
    private WriteCellStyle contentWriteCellStyle = this.getContentCellStyle();
    private Map<String, PenetrateColumn> penetrateColumnMap;
    private CellStyle headCellStyle;
    private CellStyle contentCellStyle;
    private CellStyle leftCellStyle;
    private CellStyle rightCellStyle;

    public PenetrateExportCellStyleStrategy(List<PenetrateColumn> columns) {
        this.penetrateColumnMap = columns.stream().collect(Collectors.toMap(PenetrateColumn::getTitle, e -> e));
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
        headWriteCellStyle.setWrapped(Boolean.valueOf(false));
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
        contentWriteCellStyle.setWrapped(Boolean.valueOf(false));
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        return contentWriteCellStyle;
    }

    protected void setHeadCellStyle(CellWriteHandlerContext context) {
        if (this.headCellStyle == null) {
            this.headCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.headWriteCellStyle);
        }
        Cell cell = context.getCell();
        cell.setCellStyle(this.headCellStyle);
        List headNameList = context.getHeadData().getHeadNameList();
        String headchildNodeName = (String)headNameList.get(headNameList.size() - 1);
        Sheet sheet = context.getCell().getSheet();
        if (this.penetrateColumnMap.containsKey(headchildNodeName)) {
            int width = 200;
            if (this.penetrateColumnMap.get(headchildNodeName).getWidth() != null) {
                width = this.penetrateColumnMap.get(headchildNodeName).getWidth();
            } else if (this.penetrateColumnMap.get(headchildNodeName).getMinWidth() != null) {
                width = this.penetrateColumnMap.get(headchildNodeName).getMinWidth();
            }
            sheet.setColumnWidth(cell.getColumnIndex(), width * 28);
        } else {
            sheet.setColumnWidth(cell.getColumnIndex(), 3840);
        }
    }

    protected void setContentCellStyle(CellWriteHandlerContext context) {
        if (this.contentCellStyle == null) {
            this.contentCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.contentWriteCellStyle);
        }
        if (this.leftCellStyle == null) {
            this.leftCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.contentWriteCellStyle);
            this.leftCellStyle.setAlignment(HorizontalAlignment.LEFT);
        }
        if (this.rightCellStyle == null) {
            this.rightCellStyle = StyleUtil.buildCellStyle((Workbook)context.getWriteContext().writeWorkbookHolder().getWorkbook(), null, (WriteCellStyle)this.contentWriteCellStyle);
            this.rightCellStyle.setAlignment(HorizontalAlignment.RIGHT);
        }
        Cell cell = context.getCell();
        cell.setCellStyle(this.contentCellStyle);
        List headNameList = context.getHeadData().getHeadNameList();
        String headchildNodeName = (String)headNameList.get(headNameList.size() - 1);
        if (ColumnEnum.ACCTYEAR.getTitle().equals(headchildNodeName) || ColumnEnum.ACCTPERIOD.getTitle().equals(headchildNodeName) || ColumnEnum.ACCTDAY.getTitle().equals(headchildNodeName) || headchildNodeName.contains(ColumnEnum.ORIENT.getTitle())) {
            return;
        }
        if (CellType.NUMERIC.equals((Object)cell.getCellType())) {
            DecimalFormat df = new DecimalFormat("#,###.00");
            cell.setCellValue(cell.getNumericCellValue() == 0.0 ? "" : df.format(cell.getNumericCellValue()));
            cell.setCellStyle(this.rightCellStyle);
        } else if (CellType.STRING.equals((Object)cell.getCellType())) {
            cell.setCellValue(cell.getStringCellValue().replace(" ", ""));
            cell.setCellStyle(this.leftCellStyle);
        }
    }
}

