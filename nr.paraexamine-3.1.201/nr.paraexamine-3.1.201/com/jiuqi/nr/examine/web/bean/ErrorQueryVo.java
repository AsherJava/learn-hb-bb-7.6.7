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

@ApiModel(value="\u53c2\u6570\u68c0\u67e5\u9519\u8bef\u4fe1\u606f\u67e5\u8be2\u7c7b", description="\u53c2\u6570\u68c0\u67e5\u9519\u8bef\u4fe1\u606f\u67e5\u8be2\u7c7b")
public class ErrorQueryVo {
    @ApiModelProperty(value="\u9519\u8bef\u4ee3\u7801")
    public int errorType;
    @ApiModelProperty(value="\u53c2\u6570\u6807\u8bc6")
    public String paraKey;
    @ApiModelProperty(value="\u53c2\u6570\u7c7b\u578b")
    public int paraType;
}

