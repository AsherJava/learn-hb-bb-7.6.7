/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.validation.constraints.Min
 */
package com.jiuqi.gcreport.org.api.vo;

import javax.validation.constraints.Min;

public class CheckOrgParameterVO {
    private String orgType;
    private String orgVer;
    private String orgVerName;
    private String checkType;
    @Min(value=1L, message="\u9875\u7801\u4e0d\u80fd\u5c0f\u4e8e1")
    private @Min(value=1L, message="\u9875\u7801\u4e0d\u80fd\u5c0f\u4e8e1") Integer pageNum;
    @Min(value=50L)
    private @Min(value=50L) Integer pageSize;

    public String getOrgType() {
        return this.orgType;
    }

    public void setOrgType(String orgType) {
        this.orgType = orgType;
    }

    public String getOrgVer() {
        return this.orgVer;
    }

    public void setOrgVer(String orgVer) {
        this.orgVer = orgVer;
    }

    public String getCheckType() {
        return this.checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public Integer getPageNum() {
        return this.pageNum;
    }

    public CheckOrgParameterVO setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
        return this;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public CheckOrgParameterVO setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public String getOrgVerName() {
        return this.orgVerName;
    }

    public void setOrgVerName(String orgVerName) {
        this.orgVerName = orgVerName;
    }
}

