/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.extract.client.dto;

import java.util.List;
import java.util.Map;

public class BillExtractDTO {
    private String billListDefine;
    private List<String> billDefines;
    private Boolean extractAllUnit;
    private List<String> unitCodes;
    private String startDateStr;
    private String endDateStr;
    private List<String> billCodeList;
    private Boolean includeUncharged;
    private Map<String, String> extInfo;

    public String getBillListDefine() {
        return this.billListDefine;
    }

    public void setBillListDefine(String billListDefine) {
        this.billListDefine = billListDefine;
    }

    public List<String> getBillDefines() {
        return this.billDefines;
    }

    public void setBillDefines(List<String> billDefines) {
        this.billDefines = billDefines;
    }

    public Boolean getExtractAllUnit() {
        return this.extractAllUnit;
    }

    public void setExtractAllUnit(Boolean extractAllUnit) {
        this.extractAllUnit = extractAllUnit;
    }

    public List<String> getUnitCodes() {
        return this.unitCodes;
    }

    public void setUnitCodes(List<String> unitCodes) {
        this.unitCodes = unitCodes;
    }

    public String getStartDateStr() {
        return this.startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return this.endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public List<String> getBillCodeList() {
        return this.billCodeList;
    }

    public void setBillCodeList(List<String> billCodeList) {
        this.billCodeList = billCodeList;
    }

    public Boolean getIncludeUncharged() {
        return this.includeUncharged;
    }

    public void setIncludeUncharged(Boolean includeUncharged) {
        this.includeUncharged = includeUncharged;
    }

    public Map<String, String> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, String> extInfo) {
        this.extInfo = extInfo;
    }

    public String toString() {
        return "BillExtractDTO [billListDefine=" + this.billListDefine + ", billDefines=" + this.billDefines + ", extractAllUnit=" + this.extractAllUnit + ", unitCodes=" + this.unitCodes + ", startDateStr=" + this.startDateStr + ", endDateStr=" + this.endDateStr + ", billCodeList=" + this.billCodeList + ", includeUncharged=" + this.includeUncharged + "]";
    }
}

