/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.xssf.usermodel.XSSFColor
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.bi.office.excel;

import com.jiuqi.bi.grid.GridColor;
import com.jiuqi.bi.office.excel.ExcelProcessor;
import com.jiuqi.bi.office.excel.IExcelProcessor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class XSSFProcessor
extends ExcelProcessor
implements IExcelProcessor {
    private final XSSFWorkbook workbook;

    public XSSFProcessor(XSSFWorkbook workbook) {
        this.workbook = workbook;
    }

    @Override
    protected XSSFColor colorOf(int color) {
        byte[] rgb = GridColor.getTriple(color);
        return new XSSFColor(rgb, this.workbook.getStylesSource().getIndexedColors());
    }
}

