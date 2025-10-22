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
import com.jiuqi.gcreport.clbr.executor.model.ClbrReceiveSettingExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrReceiveSettingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrReceiveSettingImportExecutor
extends AbstractImportExcelModelExecutor<ClbrReceiveSettingExcelModel> {
    @Autowired
    private ClbrReceiveSettingService clbrReceiveSettingService;

    protected ClbrReceiveSettingImportExecutor() {
        super(ClbrReceiveSettingExcelModel.class);
    }

    public String getName() {
        return "ClbrReceiveSettingImportExecutor";
    }

    protected Object importExcelModels(ImportContext context, List<ClbrReceiveSettingExcelModel> rowDatas) {
        return this.clbrReceiveSettingService.settingImport(rowDatas);
    }
}

