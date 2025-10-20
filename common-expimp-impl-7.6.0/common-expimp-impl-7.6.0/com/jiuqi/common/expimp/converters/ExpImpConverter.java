/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.converters;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;

public interface ExpImpConverter {
    public Object convertExcelToJavaData(ImportContext var1, ImportExcelSheet var2, int var3, int var4, Object var5);

    public Object convertJavaToExcelData(ExportContext var1, ExportExcelSheet var2, int var3, int var4, Object var5);
}

