/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.examine.web.bean;

import com.jiuqi.nr.examine.web.bean.ExamineNode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;

@ApiModel(value="\u53c2\u6570\u68c0\u67e5\u8fd4\u56de\u7c7b", description="\u53c2\u6570\u68c0\u67e5\u8fd4\u56de\u7c7b")
public class ExamineData {
    @ApiModelProperty(value="\u5c55\u793a\u65b9\u5f0f", example=" 1-\u9519\u8bef\u7c7b\u578b 2-\u53c2\u6570\u7c7b\u578b 3-\u4fee\u590d\u7c7b\u578b")
    public int viewType;
    @ApiModelProperty(value="\u8be6\u7ec6\u4fe1\u606f")
    public List<ExamineNode> nodes;
}

