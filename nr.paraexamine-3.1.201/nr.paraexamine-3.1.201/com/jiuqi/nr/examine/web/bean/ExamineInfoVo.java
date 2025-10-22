/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.examine.web.bean;

import com.jiuqi.nr.examine.web.bean.ExamineData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="\u53c2\u6570\u68c0\u67e5\u8fd4\u56de\u7c7b", description="\u53c2\u6570\u68c0\u67e5\u8fd4\u56de\u7c7b")
public class ExamineInfoVo {
    @ApiModelProperty(value="\u68c0\u67e5\u72b6\u6001", example="0-\u6b63\u5728\u68c0\u67e5\uff0c1-\u68c0\u67e5\u4e2d\u65ad\uff0c2-\u68c0\u67e5\u5b8c\u6210")
    public int examineStatus = 2;
    @ApiModelProperty(value="\u5177\u4f53\u9519\u8bef\u4fe1\u606f")
    public ExamineData datas;
}

