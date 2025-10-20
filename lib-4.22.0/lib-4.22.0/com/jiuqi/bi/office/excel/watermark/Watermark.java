/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.office.excel.watermark;

import com.jiuqi.bi.util.Rect;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class Watermark {
    public static final String SPLITER = "<br>";
    private String content;
    private String color = "#000000";
    private String bgColor = "#FFFFFF";
    private String fontFamily = "\u5b8b\u4f53";
    private double width = 300.0;
    private double height = 150.0;
    private int transparency = 10;
    private int size = 12;

    public String getContent() {
        return this.content;
    }

    public String[] getMultiLineContent() {
        if (this.content == null) {
            return new String[0];
        }
        return this.content.split(SPLITER);
    }

    public String getLongestContentLine() {
        String longStr = "";
        for (String line : this.getMultiLineContent()) {
            if (line.length() <= longStr.length()) continue;
            longStr = line;
        }
        return longStr;
    }

    public Watermark setContent(String content) {
        this.content = content;
        return this;
    }

    public String getColor() {
        return this.color;
    }

    public Watermark setColor(String color) {
        this.color = color;
        return this;
    }

    public int getTransparency() {
        return this.transparency;
    }

    public Watermark setTransparency(int transparency) {
        this.transparency = transparency;
        return this;
    }

    public int getSize() {
        return this.size;
    }

    public Watermark setSize(int size) {
        this.size = size;
        return this;
    }

    public String getBgColor() {
        return this.bgColor;
    }

    public Watermark setBgColor(String bgColor) {
        this.bgColor = bgColor;
        return this;
    }

    public double getWidth() {
        return this.width;
    }

    public Watermark setWidth(double width) {
        this.width = width;
        return this;
    }

    public double getHeight() {
        return this.height;
    }

    public Watermark setHeight(double height) {
        this.height = height;
        return this;
    }

    public String getFontFamily() {
        return this.fontFamily;
    }

    public Watermark setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
        return this;
    }

    public Rect getDrawPanelRect() {
        Rect rect = new Rect();
        String[] lines = this.content == null ? new String[]{} : this.content.split(SPLITER);
        String longStr = this.getLongestContentLine();
        BufferedImage img = new BufferedImage(300, 300, 2);
        Graphics2D loGraphic = img.createGraphics();
        Font font = new Font(this.fontFamily, 1, (int)Math.ceil(4.0 * (double)this.getSize() / 3.0));
        FontMetrics loFontMetrics = loGraphic.getFontMetrics(font);
        int liStrWidth = loFontMetrics.stringWidth(longStr);
        int liStrHeight = loFontMetrics.getHeight() * lines.length + 5 * (lines.length - 1);
        rect.bottom = (int)((double)liStrHeight * Math.cos(Math.toRadians(15.0)) + Math.sin(Math.toRadians(15.0)) * (double)liStrWidth + 50.0);
        rect.right = (int)(Math.cos(Math.toRadians(15.0)) * (double)liStrWidth + (double)(2 * liStrHeight) * Math.sin(Math.toRadians(15.0)) + 50.0);
        loGraphic.dispose();
        return rect;
    }

    public Rect getOriginRect() {
        Rect rect = new Rect();
        String[] lines = this.content == null ? new String[]{} : this.content.split(SPLITER);
        String longStr = this.getLongestContentLine();
        BufferedImage img = new BufferedImage(300, 300, 2);
        Graphics2D loGraphic = img.createGraphics();
        Font font = new Font(this.fontFamily, 0, (int)Math.ceil(4.0 * (double)this.getSize() / 3.0));
        FontMetrics loFontMetrics = loGraphic.getFontMetrics(font);
        rect.bottom = loFontMetrics.getHeight() * lines.length + 5 * (lines.length - 1);
        rect.right = loFontMetrics.stringWidth(longStr);
        loGraphic.dispose();
        return rect;
    }
}

