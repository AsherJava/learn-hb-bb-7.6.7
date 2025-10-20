/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.organization.api.vo;

import java.util.List;

public class OrgDataParam {
    private String orgType;
    private String orgVerCode;
    private String orgVerName;
    private String authType;
    private String searchText;
    private String orgCode;
    private String orgParentCode;
    private List<String> orgCodes;
    private Integer pageNum = 0;
    private Integer pageSize = 0;
    private String expression;
    private String formSchemeKey;
    private String adjustPeriodCode;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgVerCode() {
        return this.orgVerCode;
    }

    public void setOrgVerCode(String orgVerCode) {
        this.orgVerCode = orgVerCode;
    }

    public String getOrgVerName() {
        return this.orgVerName;
    }

    public void setOrgVerName(String orgVerName) {
        this.orgVerName = orgVerName;
    }

    public String getAuthType() {
        return this.authType;
    }

    public void setAuthType(String authType) {
        this.authType = authType;
    }

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getOrgParentCode() {
        return this.orgParentCode;
    }

    public void setOrgParentCode(String orgParentCode) {
        this.orgParentCode = orgParentCode;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
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

    public String getExpression() {
        return this.expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getAdjustPeriodCode() {
        return this.adjustPeriodCode;
    }

    public void setAdjustPeriodCode(String adjustPeriodCode) {
        this.adjustPeriodCode = adjustPeriodCode;
    }
}

