/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formulaschemeconfig.vo;

import java.util.List;

public class BillFormulaSchemeConfigCondition {
    private String billId;
    private String orgId;
    private List<String> fetchSchemes;
    private List<String> collocationMethods;
    private Boolean showBlankRow;
    private Integer page;
    private Integer pageSize;

    public String getBillId() {
        return this.billId;
    }

    public void setBillId(String billId) {
        this.billId = billId;
    }

    public String getOrgId() {
        return this.orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public List<String> getFetchSchemes() {
        return this.fetchSchemes;
    }

    public void setFetchSchemes(List<String> fetchSchemes) {
        this.fetchSchemes = fetchSchemes;
    }

    public List<String> getCollocationMethods() {
        return this.collocationMethods;
    }

    public void setCollocationMethods(List<String> collocationMethods) {
        this.collocationMethods = collocationMethods;
    }

    public Boolean getShowBlankRow() {
        return this.showBlankRow;
    }

    public void setShowBlankRow(Boolean showBlankRow) {
        this.showBlankRow = showBlankRow;
    }

    public Integer getPage() {
        return this.page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}

