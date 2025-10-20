/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 */
package com.jiuqi.gcreport.conversion.conversionsystem.executor;

import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.gcreport.conversion.conversionsystem.executor.common.ConversionSystemItemExcelModel;
import com.jiuqi.gcreport.conversion.conversionsystem.service.ConversionSystemService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConversionSystemImportExecutorImpl
extends AbstractImportExcelModelExecutor<ConversionSystemItemExcelModel> {
    @Autowired
    private ConversionSystemService service;

    protected ConversionSystemImportExecutorImpl() {
        super(ConversionSystemItemExcelModel.class);
    }

    public String getName() {
        return "ConversionSystemImportExecutor";
    }

    protected Object importExcelModels(ImportContext context, List<ConversionSystemItemExcelModel> rowDatas) {
        Object result = this.service.importExcel(rowDatas);
        return result;
    }
}

