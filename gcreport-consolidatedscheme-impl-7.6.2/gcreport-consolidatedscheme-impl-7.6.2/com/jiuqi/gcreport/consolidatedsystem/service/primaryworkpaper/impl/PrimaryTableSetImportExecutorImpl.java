/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.expimp.dataimport.common.ImportContext
 *  com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor
 *  com.jiuqi.gcreport.common.util.JsonUtils
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.impl;

import com.jiuqi.common.expimp.dataimport.common.ImportContext;
import com.jiuqi.common.expimp.dataimport.excel.executor.AbstractImportExcelExecutor;
import com.jiuqi.gcreport.common.util.JsonUtils;
import com.jiuqi.gcreport.consolidatedsystem.service.primaryworkpaper.PrimaryWorkpaperService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class PrimaryTableSetImportExecutorImpl
extends AbstractImportExcelExecutor {
    @Autowired
    private PrimaryWorkpaperService primaryWorkpaperService;

    public String getName() {
        return "PrimaryTableSetImportExecutor";
    }

    public Object dataImport(MultipartFile importFile, ImportContext context) {
        Map queryParamsVO = (Map)JsonUtils.readValue((String)context.getParam(), Map.class);
        return this.primaryWorkpaperService.importData(importFile, queryParamsVO);
    }
}

