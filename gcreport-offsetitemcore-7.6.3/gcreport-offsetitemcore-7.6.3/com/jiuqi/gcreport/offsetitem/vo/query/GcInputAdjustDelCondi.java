/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.offsetitem.vo.query;

import java.util.List;

public class GcInputAdjustDelCondi {
    public List<String> srcidsYear;
    public List<String> srcidsMonth;
    public int acctYear;
    public int acctPeriod;
    public String orgType;
    public String taskId;

    public String getTaskId() {
        return this.taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public List<String> getSrcidsYear() {
        return this.srcidsYear;
    }

    public void setSrcidsYear(List<String> srcidsYear) {
        this.srcidsYear = srcidsYear;
    }

    public List<String> getSrcidsMonth() {
        return this.srcidsMonth;
    }

    public void setSrcidsMonth(List<String> srcidsMonth) {
        this.srcidsMonth = srcidsMonth;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public int getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(int acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }
}

