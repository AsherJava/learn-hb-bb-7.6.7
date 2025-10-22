/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.util.Assert
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet
 *  com.jiuqi.common.expimp.util.ExpImpUtils
 */
package com.jiuqi.gcreport.offsetitem.executor.common;

import com.jiuqi.common.base.util.Assert;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.util.ExpImpUtils;
import com.jiuqi.gcreport.offsetitem.executor.common.IntervalColorExportExcelSheet;
import java.awt.Color;
import java.util.BitSet;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.IndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class CellStyleExportExcelSheet
extends IntervalColorExportExcelSheet {
    private Map<String, CellStyle> cellStyleName2StyleMap;
    private BitSet amtCellCol;
    private boolean hasInit;

    public CellStyleExportExcelSheet(Integer sheetNo, String sheetName, Integer sheetHeadSize) {
        super(sheetNo, sheetName, sheetHeadSize);
    }

    public void setAmtCellCol(BitSet amtCellCol) {
        this.amtCellCol = amtCellCol;
    }

    @Override
    public void callBackCell(ExportContext context, ExportExcelSheet excelSheet, Workbook workbook, Sheet sheet, Row row, Cell cell, Object cellValue) {
        this.buildCache(workbook);
        super.callBackCell(context, excelSheet, workbook, sheet, row, cell, cellValue);
    }

    private void buildCache(Workbook workbook) {
        if (this.hasInit) {
            return;
        }
        Assert.isNotNull((Object)this.amtCellCol, (String)"\u8bf7\u8c03\u7528setAmtCellCol\u65b9\u6cd5\u8bbe\u7f6e\u54ea\u4e9b\u5217\u4e3a\u91d1\u989d\u5217", (Object[])new Object[0]);
        this.buildDefaultCellStyleMap(workbook);
        this.checkCellStyle();
        this.hasInit = true;
    }

    private void checkCellStyle() {
        Integer columnMaxIndex = this.getColumnMaxIndex();
        for (int i = 0; i <= columnMaxIndex; ++i) {
            if (this.amtCellCol.get(i)) {
                this.setCellStyleCacheIfEmpty(i, this.getHeadCellStyleCache(), "headAmt");
                this.setCellStyleCacheIfEmpty(i, this.getContentCellStyleCache(), "contentAmt");
                this.getContentCellTypeCache().put(i, CellType.NUMERIC);
                continue;
            }
            this.setCellStyleCacheIfEmpty(i, this.getHeadCellStyleCache(), "headText");
            this.setCellStyleCacheIfEmpty(i, this.getContentCellStyleCache(), "contentText");
            this.getContentCellTypeCache().put(i, CellType.STRING);
        }
    }

    private void setCellStyleCacheIfEmpty(Integer columnIndex, Map<Integer, CellStyle> cellStyleCache, String cellStyleCacheName) {
        cellStyleCache.put(columnIndex, this.cellStyleName2StyleMap.get(cellStyleCacheName));
    }

    private void buildDefaultCellStyleMap(Workbook workbook) {
        if (this.cellStyleName2StyleMap != null) {
            return;
        }
        this.cellStyleName2StyleMap = new ConcurrentHashMap<String, CellStyle>(16);
        CellStyle headStringStyle = ExpImpUtils.buildDefaultHeadCellStyle((Workbook)workbook);
        headStringStyle.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleName2StyleMap.put("headText", headStringStyle);
        CellStyle headAmtStyle = ExpImpUtils.buildDefaultHeadCellStyle((Workbook)workbook);
        headAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        headAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        this.cellStyleName2StyleMap.put("headAmt", headAmtStyle);
        CellStyle contentStringStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
        contentStringStyle.setAlignment(HorizontalAlignment.LEFT);
        this.cellStyleName2StyleMap.put("contentText", contentStringStyle);
        CellStyle contentAmtStyle = ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
        contentAmtStyle.setAlignment(HorizontalAlignment.RIGHT);
        contentAmtStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        this.cellStyleName2StyleMap.put("contentAmt", contentAmtStyle);
        XSSFCellStyle intervalColorString = (XSSFCellStyle)ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
        intervalColorString.setAlignment(HorizontalAlignment.LEFT);
        intervalColorString.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
        intervalColorString.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        this.cellStyleName2StyleMap.put("intervalColorText", intervalColorString);
        XSSFCellStyle intervalColorAmt = (XSSFCellStyle)ExpImpUtils.buildDefaultContentCellStyle((Workbook)workbook);
        intervalColorAmt.setAlignment(HorizontalAlignment.RIGHT);
        intervalColorAmt.setFillForegroundColor(new XSSFColor(new Color(209, 235, 251), (IndexedColorMap)new DefaultIndexedColorMap()));
        intervalColorAmt.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        intervalColorAmt.setDataFormat(HSSFDataFormat.getBuiltinFormat("#,##0.00"));
        this.cellStyleName2StyleMap.put("intervalColorAmt", intervalColorAmt);
    }
}

