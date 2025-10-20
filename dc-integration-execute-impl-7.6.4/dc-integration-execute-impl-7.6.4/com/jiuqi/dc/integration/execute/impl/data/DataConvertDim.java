/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.integration.execute.impl.data;

import java.util.Date;
import java.util.List;

public class DataConvertDim {
    private String odsUnitCode;
    private String unitCode;
    private int acctYear;
    private List<Integer> acctYearList;
    private String acctPeriod;
    private int count;
    private int batchNum;
    private Date createDate;
    private String bookCode;
    private int dcAcctPeriod;
    private String dataSchemeCode;

    public String getOdsUnitCode() {
        return this.odsUnitCode;
    }

    public void setOdsUnitCode(String odsUnitCode) {
        this.odsUnitCode = odsUnitCode;
    }

    public String getUnitCode() {
        return this.unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public int getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(int acctYear) {
        this.acctYear = acctYear;
    }

    public List<Integer> getAcctYearList() {
        return this.acctYearList;
    }

    public void setAcctYearList(List<Integer> acctYearList) {
        this.acctYearList = acctYearList;
    }

    public String getAcctPeriod() {
        return this.acctPeriod;
    }

    public void setAcctPeriod(String acctPeriod) {
        this.acctPeriod = acctPeriod;
    }

    public int getCount() {
        return this.count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getBatchNum() {
        return this.batchNum;
    }

    public void setBatchNum(int batchNum) {
        this.batchNum = batchNum;
    }

    public Date getCreateDate() {
        return this.createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getBookCode() {
        return this.bookCode;
    }

    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    public int getDcAcctPeriod() {
        return this.dcAcctPeriod;
    }

    public void setDcAcctPeriod(int dcAcctPeriod) {
        this.dcAcctPeriod = dcAcctPeriod;
    }

    public String getDataSchemeCode() {
        return this.dataSchemeCode;
    }

    public void setDataSchemeCode(String dataSchemeCode) {
        this.dataSchemeCode = dataSchemeCode;
    }
}

