/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.organization.impl.bean;

import java.util.Date;

public class PeriodDO {
    private int year;
    private int period;
    private int type;
    private char typeCode;
    private Date beginDate;
    private Date endDate;
    private String formatValue;

    public int getYear() {
        return this.year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPeriod() {
        return this.period;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Date getBeginDate() {
        return this.beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getFormatValue() {
        return this.formatValue;
    }

    public void setFormatValue(String formatValue) {
        this.formatValue = formatValue;
    }

    public String toString() {
        return this.formatValue;
    }

    public char getTypeCode() {
        return this.typeCode;
    }

    public void setTypeCode(char typeCode) {
        this.typeCode = typeCode;
    }
}

