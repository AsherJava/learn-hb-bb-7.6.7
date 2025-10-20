/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 */
package com.jiuqi.common.expimp.dataimport.excel.executor;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractImportExcelOneSheetExecutor
extends AbstractImportExcelMultiSheetExecutor {
    @Override
    protected final Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        Optional<ImportExcelSheet> readSheetOptional = excelSheets.stream().filter(readSheet -> readSheet.getSheetNo().intValue() == this.getSheetNo()).findFirst();
        if (!readSheetOptional.isPresent()) {
            throw new BusinessRuntimeException("\u5bfc\u5165\u6587\u4ef6\u6ca1\u6709\u627e\u5230\u5e8f\u53f7\u4e3a" + this.getSheetNo() + "\u7684\u9875\u7b7e\u6570\u636e\u3002");
        }
        ImportExcelSheet excelSheet = readSheetOptional.get();
        Object result = this.importExcelSheet(context, excelSheet);
        return result;
    }

    protected Object importExcelSheet(ImportContext context, ImportExcelSheet excelSheet) {
        List<ImportExcelSheet.ImportExcelSheetRowData> excelDataRows = excelSheet.getExcelSheetRowDatas();
        Object result = this.importExcelSheetRowDatas(context, excelDataRows);
        return result;
    }

    protected Object importExcelSheetRowDatas(ImportContext context, List<ImportExcelSheet.ImportExcelSheetRowData> excelDataRows) {
        List<Object[]> excelDatas = excelDataRows.stream().map(ImportExcelSheet.ImportExcelSheetRowData::getRowData).collect(Collectors.toList());
        return this.importExcelSheet(context, excelDatas);
    }

    protected Object importExcelSheet(ImportContext context, List<Object[]> excelDatas) {
        return null;
    }

    protected int getSheetNo() {
        return 0;
    }

    @Override
    protected final int[] getReadSheetNos() {
        return new int[]{this.getSheetNo()};
    }
}

