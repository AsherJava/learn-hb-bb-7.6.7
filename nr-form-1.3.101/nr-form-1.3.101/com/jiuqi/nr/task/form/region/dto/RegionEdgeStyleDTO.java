/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.task.form.region.dto;

import com.jiuqi.bi.util.StringUtils;

public class RegionEdgeStyleDTO {
    private int startIndex;
    private int endIndex;
    private int edgeStyle;
    private String edgeLineColor;

    public int getStartIndex() {
        return this.startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getEndIndex() {
        return this.endIndex;
    }

    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }

    public int getEdgeStyle() {
        return this.edgeStyle;
    }

    public void setEdgeStyle(int edgeStyle) {
        this.edgeStyle = edgeStyle;
    }

    public String getEdgeLineColor() {
        return this.edgeLineColor;
    }

    public String getEdgeLineColorToString(int edgeLineColor) {
        return RegionEdgeStyleDTO.intToHtmlColor(edgeLineColor, null);
    }

    public int getEdgeLineColorToInt() {
        return this.htmlColorToInt(this.getEdgeLineColor());
    }

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

