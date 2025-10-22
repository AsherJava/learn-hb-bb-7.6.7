/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.customExcelBatchImport.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="CustomExcelReturnInfo", description="\u81ea\u5b9a\u4e49excel\u5bfc\u5165\u8fd4\u56de\u503c")
public class CustomExcelReturnInfo {
    @ApiModelProperty(value="\u662f\u5426\u751f\u6210\u4e86\u5355\u4e2a\u6a21\u677f")
    private boolean singleFile;
    @ApiModelProperty(value="\u5355\u4e2a\u6a21\u677f\u7684\u6587\u4ef6\u540d")
    private String fileName;
    @ApiModelProperty(value="\u591a\u4e2a\u6a21\u677f\u7684\u6587\u4ef6key\uff0c\u6bcf\u4e2akey\u7528\u5206\u53f7\u9694\u5f00")
    private String fileKeys;
    @ApiModelProperty(value="\u8fd4\u56de\u4fe1\u606f")
    private String message;

    public CustomExcelReturnInfo() {
    }

    public CustomExcelReturnInfo(boolean singleFile, String fileName, String fileKeys, String message) {
        this.singleFile = singleFile;
        this.fileName = fileName;
        this.fileKeys = fileKeys;
        this.message = message;
    }

    public boolean isSingleFile() {
        return this.singleFile;
    }

    public void setSingleFile(boolean singleFile) {
        this.singleFile = singleFile;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(String fileKeys) {
        this.fileKeys = fileKeys;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

