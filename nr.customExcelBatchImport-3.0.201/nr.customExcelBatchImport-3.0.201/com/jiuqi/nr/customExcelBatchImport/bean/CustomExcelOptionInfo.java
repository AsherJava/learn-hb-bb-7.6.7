/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.customExcelBatchImport.bean;

import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;

public class CustomExcelOptionInfo
implements Serializable {
    private static final long serialVersionUID = 1L;
    @ApiModelProperty(value="\u4efb\u52a1key", name="taskKey", required=true)
    private String taskKey;
    @ApiModelProperty(value="\u65f6\u671f\u4fe1\u606f", name="periodInfo", required=true)
    private String periodInfo;
    @ApiModelProperty(value="\u8c03\u6574\u671f\u4fe1\u606f", name="adjust", required=true)
    private String adjust;
    @ApiModelProperty(value="\u6587\u4ef6key\u4fe1\u606f", name="fileKey", required=true)
    private String fileKey;
    @ApiModelProperty(value="\u662f\u5426\u5f00\u542f\u4e86\u6a21\u677f\u7ba1\u7406")
    private boolean enableManage;

    public String getTaskKey() {
        return this.taskKey;
    }

    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }

    public String getPeriodInfo() {
        return this.periodInfo;
    }

    public void setPeriodInfo(String periodInfo) {
        this.periodInfo = periodInfo;
    }

    public String getAdjust() {
        return this.adjust;
    }

    public void setAdjust(String adjust) {
        this.adjust = adjust;
    }

    public String getFileKey() {
        return this.fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public boolean isEnableManage() {
        return this.enableManage;
    }

    public void setEnableManage(boolean enableManage) {
        this.enableManage = enableManage;
    }
}

