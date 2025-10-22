/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote
 *  com.jiuqi.xg.print.service.IPrintService
 */
package com.jiuqi.nr.dataentry.print.impl;

import com.jiuqi.nr.common.cache.remote.CacheObjectResourceRemote;
import com.jiuqi.nr.dataentry.service.IBatchExportService;
import com.jiuqi.xg.print.service.IPrintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy(value=false)
public class NrEnhancedPrintHelper {
    private static IPrintService iPrintService;
    private static IBatchExportService iBatchExportService;
    private static CacheObjectResourceRemote cacheObjectResourceRemote;

    @Autowired
    @Qualifier(value="defaultPrintService")
    public void setiPrintService(IPrintService iPrintService) {
        NrEnhancedPrintHelper.iPrintService = iPrintService;
    }

    @Autowired
    @Qualifier(value="EXPORT_BATCH_EXCEL")
    public void setiBatchExportService(IBatchExportService iBatchExportService) {
        NrEnhancedPrintHelper.iBatchExportService = iBatchExportService;
    }

    @Autowired
    public void setCacheObjectResourceRemote(CacheObjectResourceRemote cacheObjectResourceRemote) {
        NrEnhancedPrintHelper.cacheObjectResourceRemote = cacheObjectResourceRemote;
    }

    public static IPrintService getiPrintService() {
        return iPrintService;
    }

    public static IBatchExportService getiBatchExportService() {
        return iBatchExportService;
    }

    public static CacheObjectResourceRemote getCacheObjectResourceRemote() {
        return cacheObjectResourceRemote;
    }
}

