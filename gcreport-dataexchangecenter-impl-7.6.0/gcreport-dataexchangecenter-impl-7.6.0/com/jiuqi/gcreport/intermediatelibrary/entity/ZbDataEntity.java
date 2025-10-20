/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.intermediatelibrary.entity;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

public class ZbDataEntity {
    private String id;
    private Integer year;
    private Integer period;
    private String unitCode;
    private String currencyCode;
    private String zbCode;
    private List<String> zbCodeList;
    private String zbValue_T;
    private BigDecimal zbValue_N;
    private Timestamp createTime;
    private Timestamp upTimeStamp;
    private String dataType;

    public List<String> getZbCodeList() {
        return this.zbCodeList;
    }

    public void setZbCodeList(List<String> zbCodeList) {
        this.zbCodeList = zbCodeList;
    }

    public String getDataType() {
        return this.dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getPeriod() {
        return this.period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getCurrencyCode() {
        return this.currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getZbCode() {
        return this.zbCode;
    }

    public void setZbCode(String zbCode) {
        this.zbCode = zbCode;
    }

    public String getZbValue_T() {
        return this.zbValue_T;
    }

    public void setZbValue_T(String zbValue_T) {
        this.zbValue_T = zbValue_T;
    }

    public BigDecimal getZbValue_N() {
        return this.zbValue_N;
    }

    public void setZbValue_N(BigDecimal zbValue_N) {
        this.zbValue_N = zbValue_N;
    }

    public Timestamp getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Timestamp getUpTimeStamp() {
        return this.upTimeStamp;
    }

    public void setUpTimeStamp(Timestamp upTimeStamp) {
        this.upTimeStamp = upTimeStamp;
    }
}

