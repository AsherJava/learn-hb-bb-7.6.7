/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.FilePoolContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u67e5\u8be2\u9644\u4ef6\u6c60\u6240\u6709\u9644\u4ef6\u4e0a\u4e0b\u6587", description="\u67e5\u8be2\u9644\u4ef6\u6c60\u6240\u6709\u9644\u4ef6\u4e0a\u4e0b\u6587")
public class FilePoolAllFileContext
extends FilePoolContext {
    @ApiModelProperty(value="\u9644\u4ef6\u6c60\u6807\u8bc6", name="filepoolKey", required=true)
    private String filepoolKey;

    public String getFilepoolKey() {
        return this.filepoolKey;
    }

    public void setFilepoolKey(String filepoolKey) {
        this.filepoolKey = filepoolKey;
    }
}

