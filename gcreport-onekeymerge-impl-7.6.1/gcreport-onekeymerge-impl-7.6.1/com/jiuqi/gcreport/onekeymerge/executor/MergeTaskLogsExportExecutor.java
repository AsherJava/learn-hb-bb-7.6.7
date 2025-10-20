/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataexport.common.ExportContext
 *  com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor
 */
package com.jiuqi.gcreport.onekeymerge.executor;

import com.jiuqi.common.expimp.dataexport.common.ExportContext;
import com.jiuqi.common.expimp.dataexport.excel.executor.AbstractExportExcelModelExecutor;
import com.jiuqi.gcreport.onekeymerge.executor.model.MergeTaskLogsExcelModel;
import com.jiuqi.gcreport.onekeymerge.service.GcMergeTaskService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MergeTaskLogsExportExecutor
extends AbstractExportExcelModelExecutor<MergeTaskLogsExcelModel> {
    @Autowired
    private GcMergeTaskService gcMergeTaskService;

    protected MergeTaskLogsExportExecutor() {
        super(MergeTaskLogsExcelModel.class);
    }

    public String getName() {
        return "MergeTaskLogsExportExecutor";
    }

    protected List<MergeTaskLogsExcelModel> exportExcelModels(ExportContext context) {
        String sn = context.getSn();
        return this.gcMergeTaskService.exportLogs(sn);
    }
}

