/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.annotation.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u589e\u52a0\u6279\u6ce8\u8bc4\u8bba\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u589e\u52a0\u6279\u6ce8\u8bc4\u8bba\u7684\u8bf7\u6c42\u4fe1\u606f")
public class SaveFormAnnotationCommentInfo {
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u5173\u8054\u7684\u6279\u6ce8id", name="annotationId", required=true)
    private String annotationId;
    @ApiModelProperty(value="\u8bc4\u8bba\u5185\u5bb9", name="content", required=true)
    private String content;
    @ApiModelProperty(value="\u88ab\u8bc4\u8bba\u7528\u6237id", name="repyId", required=true)
    private String repyId;
    @ApiModelProperty(value="\u5f53\u524d\u767b\u5f55\u7528\u6237name", name="userName", required=true)
    private String userName;
    @ApiModelProperty(value="\u4e0a\u4e0b\u6587\u4e2d\u7684\u7528\u6237\u59d3\u540d", name="userFullname", required=true)
    private String userFullname;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getAnnotationId() {
        return this.annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRepyId() {
        return this.repyId;
    }

    public void setRepyId(String repyId) {
        this.repyId = repyId;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFullname() {
        return this.userFullname;
    }

    public void setUserFullname(String userFullname) {
        this.userFullname = userFullname;
    }
}

