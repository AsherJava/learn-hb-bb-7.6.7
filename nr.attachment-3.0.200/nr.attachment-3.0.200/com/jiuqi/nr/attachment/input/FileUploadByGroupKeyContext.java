/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.FileUploadInfo;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="\u6839\u636egroupKey\u4e0a\u4f20\u9644\u4ef6\u4e0a\u4e0b\u6587", description="\u6839\u636egroupKey\u4e0a\u4f20\u9644\u4ef6\u4e0a\u4e0b\u6587")
public class FileUploadByGroupKeyContext {
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey", required=true)
    private String fieldKey;
    @ApiModelProperty(value="\u9644\u4ef6\u5206\u7ec4key", name="groupKey", required=true)
    private String groupKey;
    @ApiModelProperty(value="\u4e0a\u4f20\u9644\u4ef6\u4fe1\u606f", name="fileUploadInfos", required=true)
    private List<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848key", name="dataSchemeKey", required=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCombination", required=true)
    private DimensionCombination dimensionCombination;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u62a5\u8868key", name="formKey", required=true)
    private String formKey;

    public FileUploadByGroupKeyContext() {
    }

    public FileUploadByGroupKeyContext(String fieldKey, String groupKey, List<FileUploadInfo> fileUploadInfos, String dataSchemeKey, String taskKey, DimensionCombination dimensionCombination, String formSchemeKey, String formKey) {
        this.fieldKey = fieldKey;
        this.groupKey = groupKey;
        this.fileUploadInfos = fileUploadInfos;
        this.dataSchemeKey = dataSchemeKey;
        this.taskKey = taskKey;
        this.dimensionCombination = dimensionCombination;
        this.formSchemeKey = formSchemeKey;
        this.formKey = formKey;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public List<FileUploadInfo> getFileUploadInfos() {
        return this.fileUploadInfos;
    }

    public void setFileUploadInfos(List<FileUploadInfo> fileUploadInfos) {
        this.fileUploadInfos = fileUploadInfos;
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

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
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
}

