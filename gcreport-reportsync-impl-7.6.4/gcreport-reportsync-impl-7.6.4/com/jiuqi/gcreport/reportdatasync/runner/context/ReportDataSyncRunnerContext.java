/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.org.api.vo.OrgVersionVO
 *  com.jiuqi.nr.dataentry.bean.ExportParam
 *  nr.single.map.configurations.bean.ISingleMappingConfig
 */
package com.jiuqi.gcreport.reportdatasync.runner.context;

import com.jiuqi.gcreport.org.api.vo.OrgVersionVO;
import com.jiuqi.gcreport.reportdatasync.runner.param.ReportDataSyncRunnerParam;
import com.jiuqi.nr.dataentry.bean.ExportParam;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import nr.single.map.configurations.bean.ISingleMappingConfig;

public class ReportDataSyncRunnerContext {
    private ReportDataSyncRunnerParam reportDataSyncRunnerParam;
    private ExportParam exportParam;
    private OrgVersionVO orgVersionVO;
    private String periodValue;
    private List<String> logs = new CopyOnWriteArrayList<String>();
    private ISingleMappingConfig mappingConfig;
    private Date endtime;
    private Date starttime;

    public List<String> getLogs() {
        return this.logs;
    }

    public ReportDataSyncRunnerParam getReportDataSyncRunnerParam() {
        return this.reportDataSyncRunnerParam;
    }

    public void setReportDataSyncRunnerParam(ReportDataSyncRunnerParam reportDataSyncRunnerParam) {
        this.reportDataSyncRunnerParam = reportDataSyncRunnerParam;
    }

    public ExportParam getExportParam() {
        return this.exportParam;
    }

    public void setExportParam(ExportParam exportParam) {
        this.exportParam = exportParam;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public OrgVersionVO getOrgVersionVO() {
        return this.orgVersionVO;
    }

    public void setOrgVersionVO(OrgVersionVO orgVersionVO) {
        this.orgVersionVO = orgVersionVO;
    }

    public ISingleMappingConfig getMappingConfig() {
        return this.mappingConfig;
    }

    public void setMappingConfig(ISingleMappingConfig mappingConfig) {
        this.mappingConfig = mappingConfig;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    public Date getEndtime() {
        return this.endtime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }

    public Date getStarttime() {
        return this.starttime;
    }
}

