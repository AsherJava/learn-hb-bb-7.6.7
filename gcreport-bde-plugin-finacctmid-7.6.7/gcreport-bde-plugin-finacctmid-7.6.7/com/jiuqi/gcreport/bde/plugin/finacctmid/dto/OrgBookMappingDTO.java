/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.bde.plugin.finacctmid.dto;

import java.math.BigDecimal;

public class OrgBookMappingDTO {
    private String id;
    private BigDecimal ver;
    private String dataSchemeCode;
    private String odsId;
    private String unitCode;
    private String unitName;
    private String acctOrgCode;
    private String acctOrgName;
    private String bookCode;
    private String bookName;
    private Boolean fetch;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getVer() {
        return this.ver;
    }

    public void setVer(BigDecimal ver) {
        this.ver = ver;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getOdsId() {
        return this.odsId;
    }

    public void setOdsId(String odsId) {
        this.odsId = odsId;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return this.unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getAcctOrgCode() {
        return this.acctOrgCode;
    }

    public void setAcctOrgCode(String acctOrgCode) {
        this.acctOrgCode = acctOrgCode;
    }

    public String getAcctOrgName() {
        return this.acctOrgName;
    }

    public void setAcctOrgName(String acctOrgName) {
        this.acctOrgName = acctOrgName;
    }

    public String getBookCode() {
        return this.bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public String getBookName() {
        return this.bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Boolean getFetch() {
        return this.fetch;
    }

    public void setFetch(Boolean fetch) {
        this.fetch = fetch;
    }

    public String toString() {
        return "OrgBookMappingDTO [id=" + this.id + ", ver=" + this.ver + ", dataSchemeCode=" + this.dataSchemeCode + ", odsId=" + this.odsId + ", unitCode=" + this.unitCode + ", unitName=" + this.unitName + ", acctOrgCode=" + this.acctOrgCode + ", acctOrgName=" + this.acctOrgName + ", bookCode=" + this.bookCode + ", bookName=" + this.bookName + ", fetch=" + this.fetch + "]";
    }
}

