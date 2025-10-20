/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.gcreport.listedcompanyauthz.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;

@ApiModel(value="\u4e0a\u5e02\u516c\u53f8\u57fa\u672c\u4fe1\u606f\u8868")
public class ListedCompanyVO {
    @ApiModelProperty(value="\u6570\u636e\u4e3b\u952e", allowEmptyValue=true, example="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")
    private String id;
    @ApiModelProperty(value="\u516c\u53f8\u4ee3\u7801", allowEmptyValue=false, example="001245")
    private String orgCode;
    @ApiModelProperty(value="\u516c\u53f8\u540d\u79f0", allowEmptyValue=false, example="XXX\u516c\u53f8")
    private String orgTitle;
    @ApiModelProperty(value="\u7528\u6237\u4e3b\u952e", allowEmptyValue=true, example="xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx")
    private String userId;
    @ApiModelProperty(value="\u7528\u6237\u767b\u5f55\u540d", allowEmptyValue=false, example="admin")
    private String userName;
    @ApiModelProperty(value="\u7528\u6237\u540d\u79f0", allowEmptyValue=true, example="\u7cfb\u7edf\u7ba1\u7406\u5458")
    private String userTitle;
    @ApiModelProperty(value="\u521b\u5efa\u65f6\u95f4", allowEmptyValue=true, example="yyyy-MM-dd HH:mm:ss.SSSS")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSS")
    private Date createTime;
    @ApiModelProperty(value="\u521b\u5efa\u4eba", allowEmptyValue=true, example="admin")
    private String createUser;
    @ApiModelProperty(value="\u4fee\u6539\u65f6\u95f4", allowEmptyValue=true, example="yyyy-MM-dd HH:mm:ss.SSSS")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss.SSSS")
    private Date modifyTime;
    @ApiModelProperty(value="\u4fee\u6539\u4eba", allowEmptyValue=true, example="admin")
    private String modifyUser;
    @ApiModelProperty(hidden=true)
    private String searchText;
    @ApiModelProperty(value="\u6570\u636e\u68c0\u67e5\u7ed3\u679c", allowEmptyValue=true, example="\u7528\u6237\u4e0d\u5b58\u5728")
    private String errorMsg;

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

    public String getOrgTitle() {
        return this.orgTitle;
    }

    public void setOrgTitle(String orgTitle) {
        this.orgTitle = orgTitle;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserTitle() {
        return this.userTitle;
    }

    public void setUserTitle(String userTitle) {
        this.userTitle = userTitle;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return this.createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Date getModifyTime() {
        return this.modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return this.modifyUser;
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}

