/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.poi.ss.usermodel.Workbook
 */
package com.jiuqi.common.expimp.dataexport.excel.executor;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.common.ExportExcelSheet;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelOneSheetExecutor;
import com.jiuqi.common.expimp.dataexport.excel.util.ExportModelExcelUtils;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Workbook;

public abstract class AbstractExportExcelModelExecutor<TModel>
extends AbstractExportExcelOneSheetExecutor {
    private Class<TModel> modelClass;

    protected AbstractExportExcelModelExecutor(Class<TModel> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public final ExportExcelSheet exportExcelSheet(ExportContext context, Workbook workbook) {
        List<Object> models = context.isTemplateExportFlag() ? new ArrayList() : this.exportExcelModels(context);
        ExportExcelSheet exportExcelSheet = ExportModelExcelUtils.convertModelsToExportExcelSheet(context, workbook, this.modelClass, models);
        return exportExcelSheet;
    }

    protected abstract List<TModel> exportExcelModels(ExportContext var1);
}

