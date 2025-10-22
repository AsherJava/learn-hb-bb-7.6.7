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

@ApiModel(value="\u6aa2\u67e5\u7d50\u679c\u4fe1\u606f\u7c7b", description="\u6aa2\u67e5\u7d50\u679c\u4fe1\u606f\u7c7b")
public class ExamineDetailInfoVO {
    @ApiModelProperty(value="\u68c0\u67e5\u7c7b\u578b", example="1-\u5783\u573e\u53c2\u6570\uff0c2-\u9519\u8bef\u5f15\u7528\uff0c3-\u53c2\u6570\u9519\u8bef")
    public int examineType;
    @ApiModelProperty(value="\u5177\u4f53\u9519\u8bef\u7c7b\u578b", example="1101\uff0c1102")
    public int errorType;
    @ApiModelProperty(value="\u5177\u4f53\u9519\u8bef\u63cf\u8ff0", example="\u8fd0\u884c\u671f\u5b58\u5728\u65e0\u6548\u4efb\u52a1\u53c2\u6570")
    public String errorTitle;
    @ApiModelProperty(value="\u53c2\u6570\u7c7b\u578b", example="1-\u4efb\u52a1\uff0c2-\u62a5\u8868\u65b9\u6848\uff0c3-\u516c\u5f0f\u65b9\u6848,4-\u6253\u5370\u65b9\u6848\uff0c5-\u516c\u5f0f,6-\u62a5\u8868\u5206\u7ec4\uff0c7-\u62a5\u8868,8-\u533a\u57df\uff0c9-\u6570\u636e\u94fe\u63a5")
    public int paraType;
    @ApiModelProperty(value="\u53c2\u6570\u6807\u8bc6")
    public String paraTitle;
    @ApiModelProperty(value="\u53c2\u6570\u63cf\u8ff0")
    public String paraKey;
    @ApiModelProperty(value="\u4fee\u590d\u72b6\u6001", example="0-\u672a\u4fee\u590d, 1-\u4fee\u590d\u6210\u529f,2-\u6807\u8bb0\u6210\u529f, 3-\u5ffd\u7565, 4-\u5931\u8d25")
    public int restoreStatus;
    @ApiModelProperty(value="\u63cf\u8ff0\u4fe1\u606f")
    public String description;
}

