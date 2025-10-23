/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nvwa.cellbook.excel.IExcelToCellSheetProvider
 */
package com.jiuqi.nr.task.form.formio.service;

import com.jiuqi.nr.task.form.formio.context.FormImportContext;
import com.jiuqi.nvwa.cellbook.excel.IExcelToCellSheetProvider;
import org.apache.poi.ss.usermodel.Sheet;

public interface IExcelToCellSheetProviderExtend
extends IExcelToCellSheetProvider {
    public boolean isGenerateFields();

    public void refresh(Sheet var1);

    public FormImportContext getFormImportContext();
}

