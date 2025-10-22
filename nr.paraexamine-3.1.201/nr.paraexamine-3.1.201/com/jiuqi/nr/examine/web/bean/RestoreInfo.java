/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.examine.web.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u4fee\u590d\u7edf\u8ba1\u4fe1\u606f\u7c7b", description="\u4fee\u590d\u7edf\u8ba1\u4fe1\u606f\u7c7b")
public class RestoreInfo {
    @ApiModelProperty(value="\u9519\u8bef\u603b\u6570")
    public int total = 0;
    @ApiModelProperty(value="\u9519\u8bef\u7c7b\u578b\u603b\u6570")
    public int error = 0;
    @ApiModelProperty(value="\u5783\u573e\u53c2\u6570\u603b\u6570")
    public int refuse = 0;
    @ApiModelProperty(value="\u65e0\u6548\u5f15\u7528\u603b\u6570")
    public int quote = 0;
    @ApiModelProperty(value="\u4fee\u590d\u6210\u529f\u603b\u6570")
    public int success = 0;
    @ApiModelProperty(value="\u4fee\u590d\u5931\u8d25\u603b\u6570")
    public int fail = 0;
    @ApiModelProperty(value="\u6807\u8bb0\u4fee\u590d\u603b\u6570")
    public int markSuccess = 0;
    @ApiModelProperty(value="\u5ffd\u7565\u4fee\u590d\u603b\u6570")
    public int ignore = 0;
    @ApiModelProperty(value="\u672a\u4fee\u590d\u603b\u6570")
    public int unDo = 0;

    public void add(RestoreInfo restoreInfo) {
        this.total += restoreInfo.total;
        this.error += restoreInfo.error;
        this.refuse += restoreInfo.refuse;
        this.quote += restoreInfo.quote;
        this.success += restoreInfo.success;
        this.fail += restoreInfo.fail;
        this.markSuccess += restoreInfo.markSuccess;
        this.ignore += restoreInfo.ignore;
        this.unDo += restoreInfo.unDo;
    }
}

