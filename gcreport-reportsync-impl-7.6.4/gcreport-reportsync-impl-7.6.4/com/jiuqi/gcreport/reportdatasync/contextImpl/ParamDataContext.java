/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 *  com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO
 */
package com.jiuqi.gcreport.reportdatasync.contextImpl;

import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataParamsSyncSchemeEO;
import com.jiuqi.gcreport.reportdatasync.entity.ReportDataSyncIssuedLogEO;
import com.jiuqi.gcreport.reportdatasync.vo.ReportDataSyncReceiveTaskVO;

public class ParamDataContext
extends MultilevelSyncContext {
    private ReportDataSyncIssuedLogEO xfLogEO;
    private ReportDataParamsSyncSchemeEO paramsSyncSchemeEO;
    private ReportDataSyncReceiveTaskVO receiveTaskVO;

    public ReportDataParamsSyncSchemeEO getParamsSyncSchemeEO() {
        return this.paramsSyncSchemeEO;
    }

    public void setParamsSyncSchemeEO(ReportDataParamsSyncSchemeEO paramsSyncSchemeEO) {
        this.paramsSyncSchemeEO = paramsSyncSchemeEO;
    }

    public ReportDataSyncIssuedLogEO getXfLogEO() {
        return this.xfLogEO;
    }

    public void setXfLogEO(ReportDataSyncIssuedLogEO xfLogEO) {
        this.xfLogEO = xfLogEO;
    }

    public ReportDataSyncReceiveTaskVO getReceiveTaskVO() {
        return this.receiveTaskVO;
    }

    public void setReceiveTaskVO(ReportDataSyncReceiveTaskVO receiveTaskVO) {
        this.receiveTaskVO = receiveTaskVO;
    }
}

