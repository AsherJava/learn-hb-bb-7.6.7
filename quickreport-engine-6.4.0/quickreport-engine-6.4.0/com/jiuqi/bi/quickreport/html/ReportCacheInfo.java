/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.html;

import com.jiuqi.bi.quickreport.engine.IReportEngine;
import com.jiuqi.bi.quickreport.model.QuickReport;
import java.util.Date;

public class ReportCacheInfo {
    private String guid;
    private Date lastModifyTime;
    private IReportEngine reportEngine;
    private QuickReport reportModel;

    public String getGuid() {
        return this.guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Date getLastModifyTime() {
        if (this.lastModifyTime == null) {
            return null;
        }
        return (Date)this.lastModifyTime.clone();
    }

    public void setLastModifyTime(Date lastModifyTime) {
        if (lastModifyTime == null) {
            return;
        }
        this.lastModifyTime = (Date)lastModifyTime.clone();
    }

    public IReportEngine getReportEngine() {
        return this.reportEngine;
    }

    public void setReportEngine(IReportEngine reportEngine) {
        this.reportEngine = reportEngine;
    }

    public QuickReport getReportModel() {
        return this.reportModel;
    }

    public void setReportModel(QuickReport reportModel) {
        this.reportModel = reportModel;
    }
}

