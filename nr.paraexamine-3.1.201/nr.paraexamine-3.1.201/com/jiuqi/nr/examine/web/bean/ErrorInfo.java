/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.examine.web.bean;

import com.jiuqi.nr.examine.common.ErrorType;
import com.jiuqi.nr.examine.web.bean.ParaNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u53c2\u6570\u68c0\u67e5\u9519\u8bef\u4fe1\u606f\u8bf4\u660e\u7c7b", description="\u53c2\u6570\u68c0\u67e5\u9519\u8bef\u4fe1\u606f\u8bf4\u660e\u7c7b")
public class ErrorInfo {
    @ApiModelProperty(value="\u9519\u8bef\u4ee3\u7801")
    public int code;
    @ApiModelProperty(value="\u9519\u8bef\u6807\u9898")
    public String title;
    @ApiModelProperty(value="\u9519\u8bef\u63cf\u8ff0")
    public String description;
    @ApiModelProperty(value="\u4fee\u590d\u5efa\u8bae")
    public String proposal;
    @ApiModelProperty(value="\u53c2\u6570\u8def\u5f84")
    public ParaNode pathNode;

    public ErrorInfo() {
    }

    public ErrorInfo(ErrorType errorType) {
        this.code = errorType.getValue();
        this.title = errorType.getDescription();
        this.proposal = errorType.getProPosal();
    }
}

