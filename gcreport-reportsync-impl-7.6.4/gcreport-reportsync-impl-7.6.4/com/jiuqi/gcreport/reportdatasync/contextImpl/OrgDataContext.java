/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext
 */
package com.jiuqi.gcreport.reportdatasync.contextImpl;

import com.jiuqi.gcreport.reportdatasync.context.MultilevelSyncContext;

public class OrgDataContext
extends MultilevelSyncContext {
    private String taskId;
    private String taskTitle;
    private String periodStr;
    private String periodValue;
    private String orgType;
    private String orgCode;
    private String orgTypeTitle;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskTitle() {
        return this.taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getPeriodValue() {
        return this.periodValue;
    }

    public void setPeriodValue(String periodValue) {
        this.periodValue = periodValue;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgTypeTitle() {
        return this.orgTypeTitle;
    }

    public void setOrgTypeTitle(String orgTypeTitle) {
        this.orgTypeTitle = orgTypeTitle;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
}

