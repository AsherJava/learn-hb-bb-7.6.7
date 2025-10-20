/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.query.client.detail.data;

public class DetailQueryContext {
    private Integer acctYear;
    private Integer startPeriod;
    private Integer endPeriod;
    private Integer pageNum;
    private Integer pageSize;
    private Boolean showOrgnFlag;
    private Object[] dimensionData;
    private Boolean pagination;
    private Boolean reportFlag;
    private Boolean orgn;
    private boolean cfFlag;

    public DetailQueryContext() {
    }

    public DetailQueryContext(Integer acctYear, Integer startPeriod, Integer endPeriod, Integer pageNum, Integer pageSize, Boolean showOrgnFlag, Object[] dimensionData, Boolean pagination, Boolean reportFlag, Boolean orgn) {
        this.acctYear = acctYear;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.showOrgnFlag = showOrgnFlag;
        this.dimensionData = dimensionData;
        this.pagination = pagination;
        this.reportFlag = reportFlag;
        this.orgn = orgn;
    }

    public DetailQueryContext(Integer acctYear, Integer startPeriod, Integer endPeriod, Integer pageNum, Integer pageSize, Boolean showOrgnFlag, Object[] dimensionData, Boolean pagination, Boolean reportFlag, Boolean orgn, boolean cfFlag) {
        this.acctYear = acctYear;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.showOrgnFlag = showOrgnFlag;
        this.dimensionData = dimensionData;
        this.pagination = pagination;
        this.reportFlag = reportFlag;
        this.orgn = orgn;
        this.cfFlag = cfFlag;
    }

    public Integer getAcctYear() {
        return this.acctYear;
    }

    public void setAcctYear(Integer acctYear) {
        this.acctYear = acctYear;
    }

    public Integer getStartPeriod() {
        return this.startPeriod;
    }

    public void setStartPeriod(Integer startPeriod) {
        this.startPeriod = startPeriod;
    }

    public Integer getEndPeriod() {
        return this.endPeriod;
    }

    public void setEndPeriod(Integer endPeriod) {
        this.endPeriod = endPeriod;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Boolean getShowOrgnFlag() {
        return this.showOrgnFlag;
    }

    public void setShowOrgnFlag(Boolean showOrgnFlag) {
        this.showOrgnFlag = showOrgnFlag;
    }

    public Object[] getDimensionData() {
        return this.dimensionData;
    }

    public void setDimensionData(Object[] dimensionData) {
        this.dimensionData = dimensionData;
    }

    public Boolean getPagination() {
        return this.pagination;
    }

    public void setPagination(Boolean pagination) {
        this.pagination = pagination;
    }

    public Boolean getReportFlag() {
        return this.reportFlag;
    }

    public void setReportFlag(Boolean reportFlag) {
        this.reportFlag = reportFlag;
    }

    public Boolean getOrgn() {
        return this.orgn;
    }

    public void setOrgn(Boolean orgn) {
        this.orgn = orgn;
    }

    public boolean getCfFlag() {
        return this.cfFlag;
    }

    public void setCfFlag(boolean cfFlag) {
        this.cfFlag = cfFlag;
    }
}

