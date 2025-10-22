/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.CommonParamsDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u4fee\u6539\u9644\u4ef6\u7c7b\u522b\u8bf7\u6c42\u4fe1\u606f", description="\u4fee\u6539\u9644\u4ef6\u7c7b\u522b\u8bf7\u6c42\u4fe1\u606f")
public class ChangeFileCategoryInfo {
    @ApiModelProperty(value="\u9644\u4ef6key", name="fileKey", required=true)
    private String fileKey;
    @ApiModelProperty(value="\u7c7b\u522b", name="category", required=true)
    private String category;
    @ApiModelProperty(value="\u9644\u4ef6\u516c\u5171\u53c2\u6570", name="params", required=true)
    private CommonParamsDTO params;

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public CommonParamsDTO getParams() {
        return this.params;
    }

    public void setParams(CommonParamsDTO params) {
        this.params = params;
    }
}

