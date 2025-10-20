/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.common.expimp.dataexport.excel.executor;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelMultiSheetExecutor;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractExportExcelOneSheetExecutor
extends AbstractExportExcelMultiSheetExecutor {
    @Override
    protected final List<ExportExcelSheet> exportExcelSheets(ExportContext context, Workbook workbook) {
        ExportExcelSheet dataExportExcelSheet = this.exportExcelSheet(context, workbook);
        return Arrays.asList(dataExportExcelSheet);
    }

    protected abstract ExportExcelSheet exportExcelSheet(ExportContext var1, Workbook var2);
}

