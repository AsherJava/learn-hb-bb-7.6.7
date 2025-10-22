/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;

@ApiModel(value="fileKey\u548cgroupKey\u5b58\u50a8\u9644\u4ef6\u6c60\u5173\u8054\u8868\u4fe1\u606f", description="fileKey\u548cgroupKey\u5b58\u50a8\u9644\u4ef6\u6c60\u5173\u8054\u8868")
public class FileRelateGroupInfo {
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
    @ApiModelProperty(value="\u6307\u6807key", name="fieldKey", required=true)
    private String fieldKey;
    @ApiModelProperty(value="\u9644\u4ef6\u5206\u7ec4key", name="groupKey")
    private String groupKey;
    @ApiModelProperty(value="\u9644\u4ef6key\u5217\u8868", name="fileKeys", required=true)
    private List<String> fileKeys = new ArrayList<String>();

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

    public List<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(List<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

