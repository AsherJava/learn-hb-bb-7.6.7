/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.workingpaper.vo;

import java.io.Serializable;
import java.util.List;

public class ArbitrarilyMergeDelParamsVO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String taskId;
    private String schemeId;
    private Integer periodType;
    private String periodStr;
    private String currency;
    private Integer acctYear;
    private Integer acctPeriod;
    private String orgId;
    private String orgType;
    private List<String> mrecids;
    private List<String> srcOffsetGroupIds;
    private String selectAdjustCode;

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
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

    public Integer getPeriodType() {
        return this.periodType;
    }

    public void setPeriodType(Integer periodType) {
        this.periodType = periodType;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public List<String> getMrecids() {
        return this.mrecids;
    }

    public void setMrecids(List<String> mrecids) {
        this.mrecids = mrecids;
    }

    public List<String> getSrcOffsetGroupIds() {
        return this.srcOffsetGroupIds;
    }

    public void setSrcOffsetGroupIds(List<String> srcOffsetGroupIds) {
        this.srcOffsetGroupIds = srcOffsetGroupIds;
    }
}

