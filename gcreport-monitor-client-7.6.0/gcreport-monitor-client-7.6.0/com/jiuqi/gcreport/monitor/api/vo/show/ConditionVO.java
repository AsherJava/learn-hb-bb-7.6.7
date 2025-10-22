/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.monitor.api.vo.show;

import com.jiuqi.gcreport.monitor.api.common.MonitorExcelScopeEnum;
import com.jiuqi.gcreport.monitor.api.common.MonitorExcelTypeEnum;

public class ConditionVO {
    private String exeTemplate;
    private String orgId;
    private String orgType;
    private String taskId;
    private String schemeId;
    private Integer acctYear;
    private Integer acctPeriod;
    private String periodStr;
    private String adjustCode;
    private MonitorExcelTypeEnum exportType;
    private MonitorExcelScopeEnum exportScope;

    public String getExeTemplate() {
        return this.exeTemplate;
    }

    public void setExeTemplate(String exeTemplate) {
        this.exeTemplate = exeTemplate;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(Integer acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public MonitorExcelTypeEnum getExportType() {
        return this.exportType;
    }

    public void setExportType(MonitorExcelTypeEnum exportType) {
        this.exportType = exportType;
    }

    public MonitorExcelScopeEnum getExportScope() {
        return this.exportScope;
    }

    public void setExportScope(MonitorExcelScopeEnum exportScope) {
        this.exportScope = exportScope;
    }

    public String getAdjustCode() {
        return this.adjustCode;
    }

    public void setAdjustCode(String adjustCode) {
        this.adjustCode = adjustCode;
    }

    public String toString() {
        return "ConditionVO{exeTemplate='" + this.exeTemplate + '\'' + ", orgId='" + this.orgId + '\'' + ", orgType='" + this.orgType + '\'' + ", taskId='" + this.taskId + '\'' + ", schemeId='" + this.schemeId + '\'' + ", acctYear=" + this.acctYear + ", acctPeriod=" + this.acctPeriod + ", periodStr='" + this.periodStr + '\'' + ", adjustCode='" + this.adjustCode + '\'' + ", exportType=" + (Object)((Object)this.exportType) + ", exportScope=" + (Object)((Object)this.exportScope) + '}';
    }
}

