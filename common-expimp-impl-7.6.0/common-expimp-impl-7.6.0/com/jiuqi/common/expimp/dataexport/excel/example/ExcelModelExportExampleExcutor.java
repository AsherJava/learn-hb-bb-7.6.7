/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.common.expimp.dataexport.excel.example;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.example.ExcelModelExportExample;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class ExcelModelExportExampleExcutor
extends AbstractExportExcelModelExecutor<ExcelModelExportExample> {
    public ExcelModelExportExampleExcutor() {
        super(ExcelModelExportExample.class);
    }

    @Override
    public String getName() {
        return "ExcelModelExportExampleExcutor";
    }

    @Override
    protected List<ExcelModelExportExample> exportExcelModels(ExportContext context) {
        ArrayList<ExcelModelExportExample> excelModels = new ArrayList<ExcelModelExportExample>();
        for (int i = 0; i < 10; ++i) {
            excelModels.add(new ExcelModelExportExample("name" + i, "code" + i, i));
        }
        return excelModels;
    }
}

