/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.designer.web.facade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.bi.util.StringUtils;

public class RegionEdgeStyleObj {
    @JsonProperty(value="startIndex")
    private int startIndex;
    @JsonProperty(value="endIndex")
    private int endIndex;
    @JsonProperty(value="edgeStyle")
    private int edgeStyle;
    @JsonProperty(value="edgeLineColor")
    private String edgeLineColor;

    @JsonIgnore
    public int getStartIndex() {
        return this.startIndex;
    }

    @JsonIgnore
    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    @JsonIgnore
    public int getEndIndex() {
        return this.endIndex;
    }

    @JsonIgnore
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    @JsonIgnore
    public int getEdgeStyle() {
        return this.edgeStyle;
    }

    @JsonIgnore
    public void setEdgeStyle(int edgeStyle) {
        this.edgeStyle = edgeStyle;
    }

    @JsonIgnore
    public String getEdgeLineColor() {
        return this.edgeLineColor;
    }

    @JsonIgnore
    public String getEdgeLineColorToString(int edgeLineColor) {
        return RegionEdgeStyleObj.intToHtmlColor(edgeLineColor, null);
    }

    @JsonIgnore
    public int getEdgeLineColorToInt() {
        return this.htmlColorToInt(this.getEdgeLineColor());
    }

    @JsonIgnore
    public void setEdgeLineColor(String edgeLineColor) {
        this.edgeLineColor = edgeLineColor;
    }

    public static String intToHtmlColor(int color, String defaultColor) {
        if (color < 0) {
            if (StringUtils.isNotEmpty((String)defaultColor)) {
                return defaultColor;
            }
            return "";
        }
        return "#" + StringUtils.leftPad((String)Integer.toHexString(color), (int)6, (String)"0");
    }

    public int htmlColorToInt(String color) {
        if (StringUtils.isEmpty((String)color)) {
            return -1;
        }
        return Integer.parseInt(color.substring(1), 16);
    }
}

