/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.jiuqi.bi.grid.h5;

import org.json.JSONException;
import org.json.JSONObject;

public class H5GridStyle {
    private String borderColor = "#E6E6E6";
    private String fontColor = "red";
    private int fontSize = 12;
    private int rowHeight = 25;
    private String backColor = "transparent";
    private String headerFontColor = "#000000";
    private String headerBackColor = "#CCCCCC";
    private String designerBackColor = "transparent";

    public String getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getFontColor() {
        return this.fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

    public int getFontSize() {
        return this.fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getRowHeight() {
        return this.rowHeight;
    }

    public void setRowHeight(int rowHeight) {
        this.rowHeight = rowHeight;
    }

    public String getBackColor() {
        return this.backColor;
    }

    public void setBackColor(String backColor) {
        this.backColor = backColor;
    }

    public String getHeaderFontColor() {
        return this.headerFontColor;
    }

    public void setHeaderFontColor(String headerFontColor) {
        this.headerFontColor = headerFontColor;
    }

    public String getHeaderBackColor() {
        return this.headerBackColor;
    }

    public void setHeaderBackColor(String headerBackColor) {
        this.headerBackColor = headerBackColor;
    }

    public String getDesignerBackColor() {
        return this.designerBackColor;
    }

    public void setDesignerBackColor(String designerBackColor) {
        this.designerBackColor = designerBackColor;
    }

    public JSONObject toJson() throws JSONException {
        JSONObject json = new JSONObject();
        json.put("fontColor", (Object)this.fontColor);
        json.put("fontSize", this.fontSize);
        json.put("rowHeight", this.rowHeight);
        json.put("borderColor", (Object)this.borderColor);
        json.put("bgColor", (Object)this.backColor);
        json.put("headerFontColor", (Object)this.headerFontColor);
        json.put("headerBgColor", (Object)this.headerBackColor);
        json.put("designerBackColor", (Object)this.designerBackColor);
        return json;
    }
}

