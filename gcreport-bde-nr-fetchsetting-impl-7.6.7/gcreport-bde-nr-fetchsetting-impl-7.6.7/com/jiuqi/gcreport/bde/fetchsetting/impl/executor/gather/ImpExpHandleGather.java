/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.fetchsetting.impl.executor.gather;

import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.handler.ImpExpInnerColumnHandler;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.IFetchSettingExportService;
import com.jiuqi.gcreport.bde.fetchsetting.impl.executor.service.IFetchSettingImportService;
import java.util.List;

public interface ImpExpHandleGather {
    public List<ImpExpInnerColumnHandler> listColumnHandler();

    public ImpExpInnerColumnHandler getColumnHandler(String var1);

    public IFetchSettingImportService getImpServiceByBizType(String var1);

    public IFetchSettingExportService getExpServiceByBizType(String var1);
}

