/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.org.api.intf.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u7ec4\u7ec7\u673a\u6784\u67e5\u8be2\u53c2\u6570")
public class GcOrgBaseApiParam {
    @ApiModelProperty(value="\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", allowEmptyValue=true)
    private String orgCode;
    @ApiModelProperty(value="\u7236\u7ea7\u7ec4\u7ec7\u673a\u6784\u4ee3\u7801", allowEmptyValue=true)
    private String orgParentCode;
    @ApiModelProperty(value="\u8fc7\u6ee4\u5b57\u7b26\u4e32", allowEmptyValue=true)
    private String searchText;
    @ApiModelProperty(value="\u662f\u5426\u542f\u7528\u6743\u9650", allowEmptyValue=true)
    private Boolean auth;
    @ApiModelProperty(value="\u662f\u5426\u5411\u4e0a\u79fb\u52a8", allowEmptyValue=true)
    private Boolean up;
    @ApiModelProperty(value="\u79fb\u52a8\u5230\u67d0\u4e2a\u5355\u4f4dCODE\u4e4b\u4e0a", allowEmptyValue=true)
    private String location;

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

    public String getSearchText() {
        return this.searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public Boolean getAuth() {
        return this.auth;
    }

    public void setAuth(Boolean auth) {
        this.auth = auth;
    }

    public Boolean getUp() {
        return this.up;
    }

    public void setUp(Boolean up) {
        this.up = up;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}

