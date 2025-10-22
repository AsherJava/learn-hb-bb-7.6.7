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
import java.util.HashSet;
import java.util.Set;

@ApiModel(value="\u5220\u9664\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f", description="\u5220\u9664\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f")
public class AombstoneFileInfo {
    @ApiModelProperty(value="\u6570\u636e\u65b9\u6848key", name="dataSchemeKey", required=true)
    private String dataSchemeKey;
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;
    @ApiModelProperty(value="\u62a5\u8868\u65b9\u6848key", name="formSchemeKey", required=true)
    private String formSchemeKey;
    @ApiModelProperty(value="\u9644\u4ef6\u5206\u7ec4key", name="groupKey", required=true)
    private String groupKey;
    @ApiModelProperty(value="\u9644\u4ef6key\u5217\u8868", name="fileKeys", required=true)
    private Set<String> fileKeys = new HashSet<String>();

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

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getGroupKey() {
        return this.groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    public Set<String> getFileKeys() {
        return this.fileKeys;
    }

    public void setFileKeys(Set<String> fileKeys) {
        this.fileKeys = fileKeys;
    }
}

