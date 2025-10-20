/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dataimport.excel.executor;

import com.jiuqi.common.expimp.converters.ExpImpConverter;
import com.jiuqi.common.expimp.dataexport.excel.annotation.Excel;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor;
import com.jiuqi.common.expimp.dataimport.excel.util.ImportModelExcelUtils;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractImportExcelModelExecutor<TModel>
extends AbstractImportExcelOneSheetExecutor {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractImportExcelModelExecutor.class);
    private Class<TModel> modelClass;

    protected AbstractImportExcelModelExecutor(Class<TModel> modelClass) {
        this.modelClass = modelClass;
    }

    @Override
    public final Object importExcelSheetRowDatas(ImportContext context, List<ImportExcelSheet.ImportExcelSheetRowData> excelDatas) {
        List<Object[]> rowDatas = excelDatas.stream().map(ImportExcelSheet.ImportExcelSheetRowData::getRowData).collect(Collectors.toList());
        List<TModel> rowDataModels = ImportModelExcelUtils.convertImportExcelSheetDatasToModels(context, rowDatas, this.modelClass);
        Object result = this.importExcelModels(context, rowDataModels);
        return result;
    }

    protected abstract Object importExcelModels(ImportContext var1, List<TModel> var2);

    @Override
    protected ExpImpConverter getImportExcelSheetConverter(ImportContext context, ImportExcelSheet excelSheetInfo) {
        Class<? extends ExpImpConverter> converterClass;
        Excel excel = this.modelClass.getAnnotation(Excel.class);
        ExpImpConverter converter = null;
        if (excel != null && (converterClass = excel.converter()) != null) {
            try {
                converter = converterClass.newInstance();
            }
            catch (Exception e) {
                LOGGER.error("\u6570\u636e\u5bfc\u5165\u83b7\u53d6\u683c\u5f0f\u5316\u5de5\u5177\u5f02\u5e38\u3002", e);
            }
        }
        return converter;
    }
}

