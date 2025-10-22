/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import com.jiuqi.nr.attachment.input.FileUploadByFileKeyInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.InputStream;

@ApiModel(value="\u9644\u4ef6\u4e0a\u4f20\u4fe1\u606f", description="\u9644\u4ef6\u4e0a\u4f20\u4fe1\u606f")
public class FileUploadMsg
extends FileUploadByFileKeyInfo {
    @ApiModelProperty(value="\u662f\u5426\u8986\u76d6(\u503c\u4e3atrue\u65f6\uff0cfileKey\u4e3a\u88ab\u8986\u76d6\u7684\u9644\u4ef6key)", name="covered")
    private boolean covered;

    public FileUploadMsg(InputStream file, String fileKey, String name, long size, String fileSecret, String category, boolean covered) {
        super(file, fileKey, name, size, fileSecret, category);
        this.covered = covered;
    }

    public boolean isCovered() {
        return this.covered;
    }
}

