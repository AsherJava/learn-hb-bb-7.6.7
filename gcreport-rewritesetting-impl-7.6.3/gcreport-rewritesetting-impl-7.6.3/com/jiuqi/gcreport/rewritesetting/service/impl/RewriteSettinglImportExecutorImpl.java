/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.core.type.TypeReference
 *  com.jiuqi.common.base.util.JsonUtils
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor
 */
package com.jiuqi.gcreport.rewritesetting.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jiuqi.common.base.util.JsonUtils;
import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.common.ImportExcelSheet;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelMultiSheetExecutor;
import com.jiuqi.gcreport.rewritesetting.service.RewriteSettingService;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RewriteSettinglImportExecutorImpl
extends AbstractImportExcelMultiSheetExecutor {
    @Autowired
    private RewriteSettingService rewriteSettingService;

    public String getName() {
        return "RewriteSettingImportExcutor";
    }

    protected Object importExcelSheets(ImportContext context, List<ImportExcelSheet> excelSheets) {
        Map params = (Map)JsonUtils.readValue((String)context.getParam(), (TypeReference)new TypeReference<Map<String, String>>(){});
        String schemeId = (String)params.get("schemeId");
        String taskId = (String)params.get("taskId");
        String systemId = (String)params.get("systemId");
        StringBuilder log = new StringBuilder(128);
        log.append((CharSequence)this.rewriteSettingService.rewriteSettingImport(systemId, taskId, schemeId, excelSheets));
        return log;
    }

    protected int[] getReadSheetNos() {
        return new int[]{0, 1};
    }
}

