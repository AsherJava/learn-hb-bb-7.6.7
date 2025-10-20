/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Workbook
 *  org.apache.poi.xssf.usermodel.XSSFSheet
 *  org.apache.poi.xssf.usermodel.XSSFWorkbook
 */
package com.jiuqi.bi.office.excel.watermark;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public interface IWatermarkInjector {
    @Deprecated
    default public void onCreateWorkbook(XSSFWorkbook xssfWorkbook) {
    }

    @Deprecated
    default public void onCreateSheet(XSSFSheet xssfSheet) {
    }

    public void doInject(Workbook var1);
}

