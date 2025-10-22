/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonInclude
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine
 *  io.swagger.annotations.ApiModel
 *  io.swagger.annotations.ApiModelProperty
 */
package com.jiuqi.nr.jtable.params.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.nr.definition.facade.RegionEdgeStyleDefine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="RegionEdgeStyle", description="\u533a\u57df\u672b\u884c\u8fb9\u6846\u6837\u5f0f")
@JsonInclude(value=JsonInclude.Include.NON_NULL)
public class RegionEdgeStyle {
    @ApiModelProperty(value="\u533a\u57df\u672b\u884c\u8fb9\u6846\u6837\u5f0f\u5f00\u59cb\u4f4d\u7f6e", name="start")
    private int start;
    @ApiModelProperty(value="\u533a\u57df\u672b\u884c\u8fb9\u6846\u6837\u5f0f\u7ed3\u675f\u4f4d\u7f6e", name="end")
    private int end;
    @ApiModelProperty(value="\u533a\u57df\u672b\u884c\u8fb9\u6846\u7ebf\u6837\u5f0f", name="style")
    private int style;
    @ApiModelProperty(value="\u533a\u57df\u672b\u884c\u8fb9\u6846\u989c\u8272", name="color")
    private String color;

    public RegionEdgeStyle() {
    }

    public RegionEdgeStyle(RegionEdgeStyleDefine regionEdgeStyleDefine) {
        this.start = regionEdgeStyleDefine.getStartIndex();
        this.end = regionEdgeStyleDefine.getEndIndex();
        this.style = regionEdgeStyleDefine.getEdgeLineStyle();
        this.color = regionEdgeStyleDefine.getEdgeLineColor() != -1 ? "#" + StringUtils.leftPad((String)Integer.toHexString(regionEdgeStyleDefine.getEdgeLineColor()), (int)6, (String)"0") : null;
    }

    public int getStart() {
        return this.start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return this.end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getStyle() {
        return this.style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}

