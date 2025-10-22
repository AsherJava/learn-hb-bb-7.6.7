/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.setting.client.dto;

import java.util.ArrayList;
import java.util.List;

public class BillFetchCondiDTO {
    private String fetchSchemeId;
    private String unitCode;
    private String startDate;
    private String endDate;
    private String currency;
    private String reportPeriod;
    private String gcUnitType;
    private List<String> customFetchCondi = new ArrayList<String>();

    public BillFetchCondiDTO() {
    }

    public BillFetchCondiDTO(String fetchSchemeId, String unitCode, String startDate, String endDate, String currency, String reportPeriod, String gcUnitType, List<String> customFetchCondi) {
        this.fetchSchemeId = fetchSchemeId;
        this.unitCode = unitCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.currency = currency;
        this.reportPeriod = reportPeriod;
        this.gcUnitType = gcUnitType;
        this.customFetchCondi = customFetchCondi;
    }

    public BillFetchCondiDTO(String fetchSchemeId, String unitCode, String startDate, String endDate) {
        this.fetchSchemeId = fetchSchemeId;
        this.unitCode = unitCode;
        this.startDate = startDate;
        this.endDate = endDate;
        this.customFetchCondi = new ArrayList<String>();
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCurrency() {
        return this.currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getReportPeriod() {
        return this.reportPeriod;
    }

    public void setReportPeriod(String reportPeriod) {
        this.reportPeriod = reportPeriod;
    }

    public String getGcUnitType() {
        return this.gcUnitType;
    }

    public void setGcUnitType(String gcUnitType) {
        this.gcUnitType = gcUnitType;
    }

    public List<String> getCustomFetchCondi() {
        return this.customFetchCondi;
    }

    public void setCustomFetchCondi(List<String> customFetchCondi) {
        this.customFetchCondi = customFetchCondi;
    }

    public String getFetchSchemeId() {
        return this.fetchSchemeId;
    }

    public void setFetchSchemeId(String fetchSchemeId) {
        this.fetchSchemeId = fetchSchemeId;
    }
}

