/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bde.common.dto.FixedAdaptSettingVO
 */
package com.jiuqi.bde.penetrate.client.dto;

import com.jiuqi.bde.common.dto.FixedAdaptSettingVO;

public class FetchSettingInfoDTO {
    private String bblx;
    private String unitCode;
    private String periodStr;
    private String beginDate;
    private String endDate;
    private FixedAdaptSettingVO fixedSettingData;

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

    public FixedAdaptSettingVO getFixedSettingData() {
        return this.fixedSettingData;
    }

    public void setFixedSettingData(FixedAdaptSettingVO fixedSettingData) {
        this.fixedSettingData = fixedSettingData;
    }

    public String toString() {
        return "FetchSettingInfoDTO{bblx='" + this.bblx + '\'' + ", unitCode='" + this.unitCode + '\'' + ", periodStr='" + this.periodStr + '\'' + ", beginDate='" + this.beginDate + '\'' + ", endDate='" + this.endDate + '\'' + ", fixedSettingData=" + this.fixedSettingData + '}';
    }
}

