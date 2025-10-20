/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bde.penetrate.client.dto;

import java.util.Map;
import java.util.Set;

public class PenetrateFetchSettingInfo {
    private String bblx;
    private String unitCode;
    private String periodStr;
    private String beginDate;
    private String endDate;
    private Map<String, Set<String>> fetchSettingInfoMap;

    public String getBblx() {
        return this.bblx;
    }

    public void setBblx(String bblx) {
        this.bblx = bblx;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getPeriodStr() {
        return this.periodStr;
    }

    public void setPeriodStr(String periodStr) {
        this.periodStr = periodStr;
    }

    public String getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Map<String, Set<String>> getFetchSettingInfoMap() {
        return this.fetchSettingInfoMap;
    }

    public void setFetchSettingInfoMap(Map<String, Set<String>> fetchSettingInfoMap) {
        this.fetchSettingInfoMap = fetchSettingInfoMap;
    }

    public String toString() {
        return "PenetrateFetchSettingInfo{bblx='" + this.bblx + '\'' + ", unitCode='" + this.unitCode + '\'' + ", periodStr='" + this.periodStr + '\'' + ", beginDate='" + this.beginDate + '\'' + ", endDate='" + this.endDate + '\'' + ", fetchSettingInfoMap=" + this.fetchSettingInfoMap + '}';
    }
}

