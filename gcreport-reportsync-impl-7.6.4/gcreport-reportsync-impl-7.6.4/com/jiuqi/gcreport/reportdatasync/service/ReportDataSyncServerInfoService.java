/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO
 */
package com.jiuqi.gcreport.reportdatasync.service;

import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncServerInfoVO;

public interface ReportDataSyncServerInfoService {
    public ReportDataSyncServerInfoVO saveServerInfo(ReportDataSyncServerInfoVO var1);

    public void checkAndEncryptServerInfo(ReportDataSyncServerInfoVO var1);

    public ReportDataSyncServerInfoVO queryServerInfo();

    public ReportDataSyncServerInfoVO register(ReportDataSyncServerInfoVO var1);

    public Boolean connection(ReportDataSyncServerInfoVO var1);
}

