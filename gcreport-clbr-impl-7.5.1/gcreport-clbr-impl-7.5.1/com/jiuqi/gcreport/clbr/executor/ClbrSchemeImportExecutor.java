/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor
 */
package com.jiuqi.gcreport.clbr.executor;

import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelModelExecutor;
import com.jiuqi.gcreport.clbr.executor.model.ClbrSchemeExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrSchemeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrSchemeImportExecutor
extends AbstractImportExcelModelExecutor<ClbrSchemeExcelModel> {
    @Autowired
    private ClbrSchemeService clbrSchemeService;

    protected ClbrSchemeImportExecutor() {
        super(ClbrSchemeExcelModel.class);
    }

    public String getName() {
        return "ClbrSchemeImportExecutor";
    }

    protected Object importExcelModels(ImportContext context, List<ClbrSchemeExcelModel> rowDatas) {
        return this.clbrSchemeService.clbrSchemeImport(rowDatas);
    }
}

