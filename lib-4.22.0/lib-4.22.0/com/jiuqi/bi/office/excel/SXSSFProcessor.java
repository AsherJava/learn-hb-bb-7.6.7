/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.xssf.streaming.SXSSFWorkbook
 *  org.apache.poi.xssf.usermodel.XSSFColor
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.office.excel.ExcelProcessor;
import com.jiuqi.bi.office.excel.IExcelProcessor;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFColor;

public class SXSSFProcessor
extends ExcelProcessor
implements IExcelProcessor {
    private final SXSSFWorkbook workbook;

    public SXSSFProcessor(SXSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    @Override
    protected XSSFColor colorOf(int color) {
        byte[] rgb = GridColor.getTriple(color);
        return new XSSFColor(rgb, this.workbook.getXSSFWorkbook().getStylesSource().getIndexedColors());
    }
}

