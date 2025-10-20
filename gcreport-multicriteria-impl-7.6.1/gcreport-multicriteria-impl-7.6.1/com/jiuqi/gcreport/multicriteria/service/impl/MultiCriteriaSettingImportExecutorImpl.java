/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 */
package com.jiuqi.gcreport.multicriteria.service.impl;

import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelOneSheetExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.multicriteria.service.GcMultiCriteriaService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiCriteriaSettingImportExecutorImpl
extends AbstractImportExcelOneSheetExecutor {
    @Autowired
    private GcMultiCriteriaService multiCriteriaService;

    public String getName() {
        return "MultiCriteriaSettingImportExcutor";
    }

    protected Object importExcelSheet(ImportContext context, List<Object[]> excelDatas) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), HashMap.class);
        String schemeId = (String)params.get("schemeId");
        String taskId = (String)params.get("taskId");
        StringBuilder log = new StringBuilder(128);
        log.append((CharSequence)this.multiCriteriaService.multiCriteriaSettingImport(taskId, schemeId, excelDatas));
        return log;
    }
}

