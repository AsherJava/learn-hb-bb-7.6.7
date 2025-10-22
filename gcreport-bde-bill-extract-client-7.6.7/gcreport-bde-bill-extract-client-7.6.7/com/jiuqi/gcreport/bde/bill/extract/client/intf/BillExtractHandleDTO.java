/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.bill.extract.client.intf;

import java.util.List;
import java.util.Map;

public class BillExtractHandleDTO {
    private String billListDefine;
    private String billDefine;
    private String billModel;
    private String masterTableName;
    private String rpUnitType;
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

    public String getBillDefine() {
        return this.billDefine;
    }

    public void setBillDefine(String billDefine) {
        this.billDefine = billDefine;
    }

    public String getBillModel() {
        return this.billModel;
    }

    public void setBillModel(String billModel) {
        this.billModel = billModel;
    }

    public String getMasterTableName() {
        return this.masterTableName;
    }

    public void setMasterTableName(String masterTableName) {
        this.masterTableName = masterTableName;
    }

    public String getRpUnitType() {
        return this.rpUnitType;
    }

    public void setRpUnitType(String rpUnitType) {
        this.rpUnitType = rpUnitType;
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
}

