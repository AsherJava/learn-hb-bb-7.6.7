/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dataimport.excel.example;

import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.example.ExcelModelImportExample;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExcelModelImportExampleExcutor
extends AbstractImportExcelModelExecutor<ExcelModelImportExample> {
    protected ExcelModelImportExampleExcutor() {
        super(ExcelModelImportExample.class);
    }

    @Override
    public String getName() {
        return "ExcelModelImportExampleExcutor";
    }

    @Override
    protected Object importExcelModels(ImportContext context, List<ExcelModelImportExample> rowDatas) {
        return null;
    }
}

