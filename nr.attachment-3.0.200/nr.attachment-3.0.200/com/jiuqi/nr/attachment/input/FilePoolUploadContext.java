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

@ApiModel(value="\u9644\u4ef6\u6c60\u4e0a\u4f20\u9644\u4ef6\u4e0a\u4e0b\u6587", description="\u9644\u4ef6\u6c60\u4e0a\u4f20\u9644\u4ef6\u4e0a\u4e0b\u6587")
public class FilePoolUploadContext {
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCombination", required=true)
    private DimensionCombination dimensionCombination;
    @ApiModelProperty(value="\u4e0a\u4f20\u9644\u4ef6\u4fe1\u606f", name="fileUploadInfos", required=true)
    private List<FileUploadInfo> fileUploadInfos = new ArrayList<FileUploadInfo>();

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

    public List<FileUploadInfo> getFileUploadInfos() {
        return this.fileUploadInfos;
    }

    public void setFileUploadInfos(List<FileUploadInfo> fileUploadInfos) {
        this.fileUploadInfos = fileUploadInfos;
    }
}

