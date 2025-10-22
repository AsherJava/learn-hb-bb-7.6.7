/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.FileUploadMsg;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="\u9644\u4ef6\u4e0a\u4f20\u4e0a\u4e0b\u6587", description="\u9644\u4ef6\u4e0a\u4f20\u4e0a\u4e0b\u6587")
public class FileUploadCtx {
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCombination", required=true)
    private DimensionCombination dimensionCombination;
    @ApiModelProperty(value="\u6307\u6807key(\u4ec5\u652f\u6301\u9644\u4ef6\u578b\u6307\u6807)", name="fieldKey", required=true)
    private String fieldKey;
    @ApiModelProperty(value="\u9644\u4ef6\u578b\u6307\u6807\u503c(\u5982\u4e0d\u4f20\uff0c\u7a0b\u5e8f\u4f1a\u751f\u6210\u65b0\u7684\u5e76\u8fd4\u56de)(\u6700\u5927\u957f\u5ea6\u4e3a40\u7684UUID)", name="groupKey")
    private String groupKey;
    @ApiModelProperty(value="\u4e0a\u4f20\u9644\u4ef6\u4fe1\u606f", name="fileUploadInfos", required=true)
    private List<FileUploadMsg> fileUploadMsgs;

    public FileUploadCtx(String formSchemeKey, DimensionCombination dimensionCombination, String fieldKey, String groupKey, List<FileUploadMsg> fileUploadMsgs) {
        this.formSchemeKey = formSchemeKey;
        this.dimensionCombination = dimensionCombination;
        this.fieldKey = fieldKey;
        this.groupKey = groupKey;
        this.fileUploadMsgs = fileUploadMsgs;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public List<FileUploadMsg> getFileUploadMsgs() {
        return this.fileUploadMsgs;
    }
}

