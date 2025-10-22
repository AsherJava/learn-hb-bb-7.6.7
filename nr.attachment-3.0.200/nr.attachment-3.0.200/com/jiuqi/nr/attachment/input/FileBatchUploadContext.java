/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.FileUploadInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="\u6279\u91cf\u9644\u4ef6\u4e0a\u4f20\u4e0a\u4e0b\u6587", description="\u6279\u91cf\u9644\u4ef6\u4e0a\u4f20\u4e0a\u4e0b\u6587")
public class FileBatchUploadContext {
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u62a5\u8868key", name="formKey", required=true)
    private String formKey;
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey", required=true)
    private String fieldKey;
    @ApiModelProperty(value="\u4e0a\u4f20\u9644\u4ef6\u4fe1\u606f", name="fileUploadInfos", required=true)
    private List<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
    @ApiModelProperty(value="\u9644\u4ef6\u5206\u7ec4key", name="groupKey")
    private String groupKey;
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848key", name="dataSchemeKey", required=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;

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

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public List<FileUploadInfo> getFileUploadInfos() {
        return this.fileUploadInfos;
    }

    public void setFileUploadInfos(List<FileUploadInfo> fileUploadInfos) {
        this.fileUploadInfos = fileUploadInfos;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public String getDataSchemeKey() {
        return this.dataSchemeKey;
    }

    public void setDataSchemeKey(String dataSchemeKey) {
        this.dataSchemeKey = dataSchemeKey;
    }

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

