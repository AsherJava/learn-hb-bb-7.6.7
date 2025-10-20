/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.vchrchange.data;

import java.util.Date;

public class VchrChangeDO {
    private String id;
    private Integer ver;
    private String unitCode;
    private Integer acctYear;
    private String acctPeriod;
    private String dataSchemeCode;
    private String scrVchrId;
    private String offsetGroupId;
    private Integer createOffsetVchr;
    private Integer vchrCleanFlag;
    private Date createDate;
    private Integer dcAcctPeriod;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVer() {
        return this.ver;
    }

    public void setVer(Integer ver) {
        this.ver = ver;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }

    public String getScrVchrId() {
        return this.scrVchrId;
    }

    public void setScrVchrId(String scrVchrId) {
        this.scrVchrId = scrVchrId;
    }

    public String getOffsetGroupId() {
        return this.offsetGroupId;
    }

    public void setOffsetGroupId(String offsetGroupId) {
        this.offsetGroupId = offsetGroupId;
    }

    public Integer getCreateOffsetVchr() {
        return this.createOffsetVchr;
    }

    public void setCreateOffsetVchr(Integer createOffsetVchr) {
        this.createOffsetVchr = createOffsetVchr;
    }

    public Integer getVchrCleanFlag() {
        return this.vchrCleanFlag;
    }

    public void setVchrCleanFlag(Integer vchrCleanFlag) {
        this.vchrCleanFlag = vchrCleanFlag;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getDcAcctPeriod() {
        return this.dcAcctPeriod;
    }

    public void setDcAcctPeriod(Integer dcAcctPeriod) {
        this.dcAcctPeriod = dcAcctPeriod;
    }
}

