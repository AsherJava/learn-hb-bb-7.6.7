/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.designer.web.rest.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u62a5\u8868\u79fb\u52a8", description="\u62a5\u8868\u79fb\u52a8")
public class MoveFormVO {
    @ApiModelProperty(value="\u62a5\u8868\u6807\u8bc6")
    public String formKey;
    @ApiModelProperty(value="\u6e90\u5206\u7ec4key")
    public String sourceGroupKey;
    @ApiModelProperty(value="\u76ee\u6807\u5206\u7ec4key")
    public String targetGroupKey;
}

