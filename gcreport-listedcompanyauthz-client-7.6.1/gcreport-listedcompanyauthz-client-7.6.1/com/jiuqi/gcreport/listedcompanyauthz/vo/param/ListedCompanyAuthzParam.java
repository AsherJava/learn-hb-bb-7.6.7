/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.listedcompanyauthz.vo.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u4e0a\u5e02\u516c\u53f8\u6743\u9650\u7528\u6237\u67e5\u8be2\u53c2\u6570")
public class ListedCompanyAuthzParam {
    @ApiModelProperty(value="\u516c\u53f8\u4ee3\u7801", allowEmptyValue=false, example="'001245'")
    private String orgCode;
    @ApiModelProperty(value="\u6a21\u7cca\u8fc7\u6ee4\u6761\u4ef6\uff08\u516c\u53f8\u4ee3\u7801/\u516c\u53f8\u540d\u79f0/\u7528\u6237\u767b\u5f55\u540d/\u7528\u6237\u540d\u79f0\uff09", allowEmptyValue=true, example="'\u8fc7\u6ee4\u5b57\u7b26\u4e32'")
    private String searchText;

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
}

