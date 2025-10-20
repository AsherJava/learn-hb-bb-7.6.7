/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 */
package com.jiuqi.gcreport.reportdatasync.scheduler;

import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.scheduler.MultilevelExtendHandler;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;

public interface ISyncMethodScheduler {
    public MultilevelExtendHandler getHandler();

    public String code();

    public String name();

    public boolean sync(MultilevelSyncContext var1);

    public void afterSync(boolean var1, MultilevelSyncContext var2);

    public boolean testConnection(ReportDataSyncServerInfoVO var1);
}

