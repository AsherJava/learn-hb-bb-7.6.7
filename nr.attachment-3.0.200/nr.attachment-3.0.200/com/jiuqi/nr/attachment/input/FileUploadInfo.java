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

@ApiModel(value="\u9644\u4ef6\u4e0a\u4f20\u4fe1\u606f", description="\u9644\u4ef6\u4e0a\u4f20\u4fe1\u606f")
public class FileUploadInfo {
    @ApiModelProperty(value="\u9644\u4ef6\u6d41", name="file", required=true)
    private InputStream file;
    @ApiModelProperty(value="\u540d\u79f0", name="name", required=true)
    private String name;
    @ApiModelProperty(value="\u5927\u5c0f", name="size", required=true)
    private long size;
    @ApiModelProperty(value="\u5bc6\u7ea7\uff08\u5f00\u542f\u5bc6\u7ea7\u4e0d\u4e3a\u7a7a\uff09", name="fileSecret")
    private String fileSecret;
    @ApiModelProperty(value="\u7c7b\u522b\uff08\u5f00\u542f\u7c7b\u522b\u4e0d\u4e3a\u7a7a\uff09", name="category")
    private String category;
    @ApiModelProperty(value="\u662f\u5426\u8986\u76d6", name="covered")
    private boolean covered = false;
    @ApiModelProperty(value="\u9644\u4ef6key(covered\u4e3atrue\u65f6\uff0c\u6b64\u503c\u4e3a\u88ab\u8986\u76d6\u7684\u9644\u4ef6key)", name="fileKey")
    private String fileKey;
    @ApiModelProperty(value="\u662f\u5426\u4e3ajio\u5e26fileKey\u5bfc\u5165", name="isJioImportAttachFileKey")
    private boolean isJioImportAttachFileKey = false;

    public InputStream getFile() {
        return this.file;
    }

    public void setFile(InputStream file) {
        this.file = file;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return this.size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getFileSecret() {
        return this.fileSecret;
    }

    public void setFileSecret(String fileSecret) {
        this.fileSecret = fileSecret;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isCovered() {
        return this.covered;
    }

    public void setCovered(boolean covered) {
        this.covered = covered;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public boolean isJioImportAttachFileKey() {
        return this.isJioImportAttachFileKey;
    }

    public void setJioImportAttachFileKey(boolean jioImportAttachFileKey) {
        this.isJioImportAttachFileKey = jioImportAttachFileKey;
    }
}

