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

@ApiModel(value="\u53c2\u6570\u68c0\u67e5\u6761\u4ef6\u7c7b", description="\u53c2\u6570\u68c0\u67e5\u6761\u4ef6\u7c7b")
public class ExamineTaskVo {
    @ApiModelProperty(value="\u53c2\u6570\u6807\u8bc6", required=false, allowEmptyValue=true)
    public String paraKey;
    @ApiModelProperty(value="\u53c2\u6570\u7c7b\u578b", required=false, allowEmptyValue=true)
    public int paraType;
    @ApiModelProperty(value="\u68c0\u67e5\u7c7b\u522bREFUSE(0B1, \"\u5783\u573e\u53c2\u6570\"), QUOTE(0B10, \"\u9519\u8bef\u5f15\u7528\"), ERROR(0B100, \"\u9519\u8bef\u53c2\u6570\");", required=true, example="7")
    public int examineType;
}

