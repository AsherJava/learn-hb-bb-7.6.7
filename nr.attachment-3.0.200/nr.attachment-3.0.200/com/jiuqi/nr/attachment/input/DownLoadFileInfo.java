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
import java.util.Set;

@ApiModel(value="\u4e0b\u8f7d\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f", description="\u4e0b\u8f7d\u9644\u4ef6\u8bf7\u6c42\u4fe1\u606f")
public class DownLoadFileInfo {
    @ApiModelProperty(value="\u4efb\u52a1key", name="task", required=true)
    private String task;
    @ApiModelProperty(value="\u7ef4\u5ea6\u4fe1\u606f", name="dimensionCombination", required=true)
    private DimensionCombination dimensionCombination;
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

    public DimensionCombination getDimensionCombination() {
        return this.dimensionCombination;
    }

    public void setDimensionCombination(DimensionCombination dimensionCombination) {
        this.dimensionCombination = dimensionCombination;
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

