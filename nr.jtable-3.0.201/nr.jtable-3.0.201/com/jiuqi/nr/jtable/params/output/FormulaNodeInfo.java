/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.data.logic.facade.param.output.RelatedTaskInfo
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.output;

import com.jiuqi.nr.data.logic.facade.param.output.RelatedTaskInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

@ApiModel(value="FormulaNodeInfo", description="\u516c\u5f0f\u8282\u70b9\u4fe1\u606f")
public final class FormulaNodeInfo
implements Serializable {
    private static final long serialVersionUID = 1119592256149230226L;
    @ApiModelProperty(value="\u6307\u6807Key", name="fieldKey")
    private String fieldKey;
    @ApiModelProperty(value="\u6307\u6807\u540d\u79f0", name="fieldTitle")
    private String fieldTitle;
    @ApiModelProperty(value="\u6307\u6807\u7c7b\u578b", name="fieldType")
    private String fieldType;
    @ApiModelProperty(value="\u94fe\u63a5key", name="dataLinkKey")
    private String dataLinkKey;
    @ApiModelProperty(value="\u62a5\u8868key", name="formKey")
    private String formKey;
    @ApiModelProperty(value="\u62a5\u8868\u540d\u79f0", name="formTitle")
    private String formTitle;
    @ApiModelProperty(value="\u533a\u57dfkey", name="regionKey")
    private String regionKey;
    @ApiModelProperty(value="\u6570\u636eID", name="id")
    private String id;
    @ApiModelProperty(value="\u8282\u70b9\u503c", name="value")
    private String value;
    @ApiModelProperty(value="\u8282\u70b9\u663e\u793a\u6807\u9898", name="nodeShow")
    private String nodeShow;
    @ApiModelProperty(value="\u4e0d\u5b9a\u4f4d", name="complex")
    private Boolean complex;
    private RelatedTaskInfo relatedTaskInfo;

    public Boolean getComplex() {
        return this.complex;
    }

    public void setComplex(Boolean complex) {
        this.complex = complex;
    }

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

    public RelatedTaskInfo getRelatedTaskInfo() {
        return this.relatedTaskInfo;
    }

    public void setRelatedTaskInfo(RelatedTaskInfo relatedTaskInfo) {
        this.relatedTaskInfo = relatedTaskInfo;
    }
}

