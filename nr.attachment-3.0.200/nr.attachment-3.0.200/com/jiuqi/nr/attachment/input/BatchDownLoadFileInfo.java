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
import java.util.Set;

@ApiModel(value="\u6279\u91cf\u4e0b\u8f7d\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f", description="\u6279\u91cf\u4e0b\u8f7d\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f")
public class BatchDownLoadFileInfo {
    @ApiModelProperty(value="\u4efb\u52a1key", name="task", required=true)
    private String task;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formscheme", required=true)
    private String formscheme;
    @ApiModelProperty(value="\u9644\u4ef6key", name="fileKeys", required=true)
    private Set<String> fileKeys;

    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getFormscheme() {
        return this.formscheme;
    }

    public void setFormscheme(String formscheme) {
        this.formscheme = formscheme;
    }

    public Set<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(Set<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

