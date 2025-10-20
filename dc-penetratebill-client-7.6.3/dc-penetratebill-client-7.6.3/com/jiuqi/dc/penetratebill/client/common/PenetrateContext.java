/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.penetratebill.client.common;

import java.util.Map;

public class PenetrateContext {
    private String unitCode;
    private String acctYear;
    private String vchrId;
    private Map<String, Object> voucherData;
    private Map<String, Object> rowData;
    private Map<String, Object> extInfo;
    private String schemeId;

    public PenetrateContext() {
    }

    public PenetrateContext(String unitCode, String acctYear, String vchrId, Map<String, Object> voucherData, Map<String, Object> rowData, Map<String, Object> extInfo, String schemeId) {
        this.unitCode = unitCode;
        this.acctYear = acctYear;
        this.vchrId = vchrId;
        this.voucherData = voucherData;
        this.rowData = rowData;
        this.extInfo = extInfo;
        this.schemeId = schemeId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(String acctYear) {
        this.acctYear = acctYear;
    }

    public String getVchrId() {
        return this.vchrId;
    }

    public void setVchrId(String vchrId) {
        this.vchrId = vchrId;
    }

    public Map<String, Object> getVoucherData() {
        return this.voucherData;
    }

    public void setVoucherData(Map<String, Object> voucherData) {
        this.voucherData = voucherData;
    }

    public Map<String, Object> getExtInfo() {
        return this.extInfo;
    }

    public void setExtInfo(Map<String, Object> extInfo) {
        this.extInfo = extInfo;
    }

    public String getSchemeId() {
        return this.schemeId;
    }

    public void setSchemeId(String schemeId) {
        this.schemeId = schemeId;
    }

    public Map<String, Object> getRowData() {
        return this.rowData;
    }

    public void setRowData(Map<String, Object> rowData) {
        this.rowData = rowData;
    }
}

