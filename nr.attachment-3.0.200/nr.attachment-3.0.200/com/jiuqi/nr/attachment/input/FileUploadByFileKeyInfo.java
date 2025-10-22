/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.attachment.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.InputStream;

@ApiModel(value="\u5e26\u9644\u4ef6key\u4e0a\u4f20\u9644\u4ef6\u4fe1\u606f", description="\u5e26\u9644\u4ef6key\u4e0a\u4f20\u9644\u4ef6\u4fe1\u606f")
public class FileUploadByFileKeyInfo {
    @ApiModelProperty(value="\u9644\u4ef6\u6d41", name="file", required=true)
    private InputStream file;
    @ApiModelProperty(value="\u9644\u4ef6key", name="fileKey", required=true)
    private String fileKey;
    @ApiModelProperty(value="\u540d\u79f0", name="name", required=true)
    private String name;
    @ApiModelProperty(value="\u5927\u5c0f", name="size", required=true)
    private long size;
    @ApiModelProperty(value="\u5bc6\u7ea7\uff08\u5f00\u542f\u5bc6\u7ea7\u4e0d\u4e3a\u7a7a\uff09", name="fileSecret")
    private String fileSecret;
    @ApiModelProperty(value="\u7c7b\u522b\uff08\u5f00\u542f\u7c7b\u522b\u4e0d\u4e3a\u7a7a\uff09", name="category")
    private String category;

    public FileUploadByFileKeyInfo(InputStream file, String fileKey, String name, long size, String fileSecret, String category) {
        this.file = file;
        this.fileKey = fileKey;
        this.name = name;
        this.size = size;
        this.fileSecret = fileSecret;
        this.category = category;
    }

    public InputStream getFile() {
        return this.file;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public String getName() {
        return this.name;
    }

    public long getSize() {
        return this.size;
    }

    public String getFileSecret() {
        return this.fileSecret;
    }

    public String getCategory() {
        return this.category;
    }
}

