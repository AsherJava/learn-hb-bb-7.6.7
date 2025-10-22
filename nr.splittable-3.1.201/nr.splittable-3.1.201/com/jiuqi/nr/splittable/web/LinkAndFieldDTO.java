/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.splittable.web;

import io.swagger.annotations.ApiModelProperty;

public class LinkAndFieldDTO {
    @ApiModelProperty(value="\u6307\u6807Key")
    private String fieldKey;
    @ApiModelProperty(value="\u6307\u6807\u540d\u79f0")
    private String fieldTitle;
    @ApiModelProperty(value="\u6307\u6807\u7c7b\u578b")
    private String fieldType;
    @ApiModelProperty(value="\u94fe\u63a5key")
    private String dataLinkKey;
    @ApiModelProperty(value="\u62a5\u8868key")
    private String formKey;
    @ApiModelProperty(value="\u62a5\u8868\u540d\u79f0")
    private String formTitle;
    @ApiModelProperty(value="\u533a\u57dfkey")
    private String regionKey;
    @ApiModelProperty(value="\u6570\u636eID")
    private String id;
    @ApiModelProperty(value="\u8282\u70b9\u503c")
    private String value;
    @ApiModelProperty(value="\u8282\u70b9\u663e\u793a\u6807\u9898")
    private String nodeShow;

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getFieldTitle() {
        return this.fieldTitle;
    }

    public void setFieldTitle(String fieldTitle) {
        this.fieldTitle = fieldTitle;
    }

    public String getFieldType() {
        return this.fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormTitle() {
        return this.formTitle;
    }

    public void setFormTitle(String formTitle) {
        this.formTitle = formTitle;
    }

    public String getRegionKey() {
        return this.regionKey;
    }

    public void setRegionKey(String regionKey) {
        this.regionKey = regionKey;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNodeShow() {
        return this.nodeShow;
    }

    public void setNodeShow(String nodeShow) {
        this.nodeShow = nodeShow;
    }
}

