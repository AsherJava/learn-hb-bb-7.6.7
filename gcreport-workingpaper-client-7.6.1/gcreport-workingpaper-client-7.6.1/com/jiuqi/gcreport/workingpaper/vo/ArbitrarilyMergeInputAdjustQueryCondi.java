/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.gcreport.common.GCOrgTypeEnum
 */
package com.jiuqi.gcreport.workingpaper.vo;

import com.jiuqi.gcreport.common.GCOrgTypeEnum;
import org.springframework.util.StringUtils;

public class ArbitrarilyMergeInputAdjustQueryCondi {
    public String inputUnitId;
    public String taskId;
    public String schemeId;
    public String formId;
    public String zbId;
    public int acctYear;
    public int acctPeriod;
    public String adjType;
    public String effectType;
    public String selectAdjustCode;
    public String gcBusinessTypeCode;
    public String mRecid;
    public boolean showHistoryPeriod;
    private String defaultPeriod;
    public String currencyCode;
    private String orgTypeId;
    private String unitVersion;
    public String periodStr;
    public String currencyId;
    public String adjTypeId;
    public int pageSize;
    public int pageNum;

    public String getInputUnitId() {
        return this.inputUnitId;
    }

    public void setInputUnitId(String inputUnitId) {
        this.inputUnitId = inputUnitId;
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

    public String getAdjType() {
        return this.adjType;
    }

    public void setAdjType(String adjType) {
        this.adjType = adjType;
    }

    public String getEffectType() {
        return this.effectType;
    }

    public void setEffectType(String effectType) {
        this.effectType = effectType;
    }

    public String getSelectAdjustCode() {
        return this.selectAdjustCode;
    }

    public void setSelectAdjustCode(String selectAdjustCode) {
        this.selectAdjustCode = selectAdjustCode;
    }

    public String getGcBusinessTypeCode() {
        return this.gcBusinessTypeCode;
    }

    public void setGcBusinessTypeCode(String gcBusinessTypeCode) {
        this.gcBusinessTypeCode = gcBusinessTypeCode;
    }

    public String getmRecid() {
        return this.mRecid;
    }

    public void setmRecid(String mRecid) {
        this.mRecid = mRecid;
    }

    public boolean isShowHistoryPeriod() {
        return this.showHistoryPeriod;
    }

    public void setShowHistoryPeriod(boolean showHistoryPeriod) {
        this.showHistoryPeriod = showHistoryPeriod;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public String getDefaultPeriod() {
        return this.defaultPeriod;
    }

    public void setDefaultPeriod(String defaultPeriod) {
        this.defaultPeriod = defaultPeriod;
    }

    public String getCurrency() {
        return this.currencyCode;
    }

    public String getCurrencyUpperCase() {
        if (StringUtils.isEmpty(this.currencyCode)) {
            this.currencyCode = "CNY";
        }
        return this.currencyCode.toUpperCase();
    }

    public void setCurrency(String currency) {
        this.currencyCode = currency;
    }

    public String getOrgTypeId() {
        GCOrgTypeEnum typeEnum;
        if (null == this.orgTypeId && !StringUtils.isEmpty(this.unitVersion) && null != (typeEnum = GCOrgTypeEnum.getEnumByCode((String)this.unitVersion))) {
            this.orgTypeId = typeEnum.getId();
        }
        if (null == this.orgTypeId) {
            this.orgTypeId = GCOrgTypeEnum.CORPORATE.getId();
        }
        return this.orgTypeId;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public void setUnitVersion(String unitVersion) {
        this.unitVersion = unitVersion;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getCurrencyId() {
        return this.currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getAdjTypeId() {
        return this.adjTypeId;
    }

    public void setAdjTypeId(String adjTypeId) {
        this.adjTypeId = adjTypeId;
    }
}

