/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.annotation.input;

import com.jiuqi.nr.annotation.message.AnnotationInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;

@ApiModel(value="\u6279\u91cf\u6dfb\u52a0\u591a\u4e2a\u5355\u4f4d\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f", description="\u6279\u91cf\u6dfb\u52a0\u591a\u4e2a\u5355\u4f4d\u6279\u6ce8\u7684\u8bf7\u6c42\u4fe1\u606f")
public class FormAnnotationBatchSaveInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848Key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u62a5\u8868Key", name="formKey", required=true)
    private String formKey;
    @ApiModelProperty(value="\u6279\u6ce8\u76f8\u5173\u4fe1\u606f", name="annotationInfos", required=true)
    private List<AnnotationInfo> annotationInfos;
    @ApiModelProperty(value="\u5355\u5143\u683c\u6570\u636e\u94fe\u63a5key", name="dataLinkKey", required=true)
    private String dataLinkKey;
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey", required=true)
    private String fieldKey;
    @ApiModelProperty(value="\u6279\u6ce8\u7c7b\u578b", name="types")
    private List<String> types;
    @ApiModelProperty(value="\u6279\u6ce8\u5185\u5bb9", name="content", required=true)
    private String content;
    @ApiModelProperty(value="\u5f53\u524d\u767b\u5f55\u7528\u6237name", name="userName", required=true)
    private String userName;

    public FormAnnotationBatchSaveInfo() {
    }

    public FormAnnotationBatchSaveInfo(String formSchemeKey, String formKey, List<AnnotationInfo> annotationInfos, String dataLinkKey, String fieldKey, String content, String userName) {
        this.formSchemeKey = formSchemeKey;
        this.formKey = formKey;
        this.annotationInfos = annotationInfos;
        this.dataLinkKey = dataLinkKey;
        this.fieldKey = fieldKey;
        this.content = content;
        this.userName = userName;
    }

    public FormAnnotationBatchSaveInfo(String formSchemeKey, String formKey, List<AnnotationInfo> annotationInfos, String dataLinkKey, String fieldKey, List<String> types, String content, String userName) {
        this.formSchemeKey = formSchemeKey;
        this.formKey = formKey;
        this.annotationInfos = annotationInfos;
        this.dataLinkKey = dataLinkKey;
        this.fieldKey = fieldKey;
        this.types = types;
        this.content = content;
        this.userName = userName;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public List<AnnotationInfo> getAnnotationInfos() {
        return this.annotationInfos;
    }

    public void setAnnotationInfos(List<AnnotationInfo> annotationInfos) {
        this.annotationInfos = annotationInfos;
    }

    public String getDataLinkKey() {
        return this.dataLinkKey;
    }

    public void setDataLinkKey(String dataLinkKey) {
        this.dataLinkKey = dataLinkKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public List<String> getTypes() {
        return this.types;
    }

    public void setTypes(List<String> types) {
        this.types = types;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

