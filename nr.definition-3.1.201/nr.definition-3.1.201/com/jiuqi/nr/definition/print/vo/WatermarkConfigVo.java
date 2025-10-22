/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.databind.annotation.JsonSerialize
 *  com.fasterxml.jackson.databind.ser.std.ToStringSerializer
 *  com.jiuqi.xg.process.watermark.WatermarkConfig
 *  com.jiuqi.xlib.utils.StringUtil
 */
package com.jiuqi.nr.definition.print.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.jiuqi.xg.process.watermark.WatermarkConfig;
import com.jiuqi.xlib.utils.StringUtil;

@Deprecated
public class WatermarkConfigVo {
    private String content;
    private String fontname;
    @JsonSerialize(using=ToStringSerializer.class)
    private double fontsize;
    private int fontcolor;

    public WatermarkConfigVo() {
    }

    public WatermarkConfigVo(WatermarkConfig config) {
        this.content = config.getContent();
        this.fontname = config.getFontname();
        this.fontsize = config.getFontsize();
        this.fontcolor = config.getFontcolor();
    }

    public static WatermarkConfig toWatermarkConfig(WatermarkConfigVo vo) {
        if (null == vo) {
            return null;
        }
        WatermarkConfig config = new WatermarkConfig();
        config.setContent(vo.getContent());
        config.setFontname(vo.getFontname());
        config.setFontcolor(vo.getFontcolor());
        config.setFontsize(vo.getFontsize());
        return config;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFontname() {
        return this.fontname;
    }

    public void setFontname(String fontname) {
        this.fontname = fontname;
    }

    public double getFontsize() {
        return this.fontsize;
    }

    public void setFontsize(double fontsize) {
        this.fontsize = fontsize;
    }

    @JsonIgnore
    public int getFontcolor() {
        return this.fontcolor;
    }

    public String getFontcolorStr() {
        return "#" + Integer.toHexString(this.fontcolor);
    }

    public void setFontcolorStr(String fontcolor) {
        String substring;
        if (StringUtil.isNotEmpty((String)fontcolor) && (substring = fontcolor.substring(1)).length() > 0) {
            this.fontcolor = Integer.parseInt(substring, 16);
        }
    }

    @JsonIgnore
    public void setFontcolor(int fontcolor) {
        this.fontcolor = fontcolor;
    }
}

