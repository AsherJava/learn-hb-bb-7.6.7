/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.converters.impl;

import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;

public class DefaultExpImpConverter
implements ExpImpConverter {
    @Override
    public Object convertExcelToJavaData(ImportContext context, ImportExcelSheet excelSheet, int currRowIndex, int currColumnIndex, Object cellValue) {
        return cellValue;
    }

    @Override
    public Object convertJavaToExcelData(ExportContext context, ExportExcelSheet excelSheet, int currRowIndex, int currColumnIndex, Object javaValue) {
        return javaValue;
    }
}

