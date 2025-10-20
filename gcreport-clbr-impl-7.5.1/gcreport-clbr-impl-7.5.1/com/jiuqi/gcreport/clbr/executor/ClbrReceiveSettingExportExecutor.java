/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 */
package com.jiuqi.gcreport.clbr.executor;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.clbr.executor.model.ClbrReceiveSettingExcelModel;
import com.jiuqi.gcreport.clbr.service.ClbrReceiveSettingService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClbrReceiveSettingExportExecutor
extends AbstractExportExcelModelExecutor<ClbrReceiveSettingExcelModel> {
    @Autowired
    private ClbrReceiveSettingService clbrReceiveSettingService;

    protected ClbrReceiveSettingExportExecutor() {
        super(ClbrReceiveSettingExcelModel.class);
    }

    protected List<ClbrReceiveSettingExcelModel> exportExcelModels(ExportContext context) {
        return this.clbrReceiveSettingService.settingExport();
    }

    public String getName() {
        return "ClbrReceiveSettingExportExecutor";
    }
}

