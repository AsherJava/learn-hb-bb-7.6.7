/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import org.json.JSONObject;

public abstract class Watermark {
    public static final String SPLITER = "<br>";
    private String color = "#000000";
    private int size = 12;
    private double transparency = 0.5;
    private double spacing;
    private double verticalSpacing;
    private int angle;
    private String fontFamily = "\u5fae\u8f6f\u96c5\u9ed1";
    private boolean enabled = false;
    private boolean misaligned = false;

    public abstract int getMode();

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public double getTransparency() {
        return this.transparency;
    }

    public void setTransparency(double transparency) {
        this.transparency = transparency;
    }

    public double getSpacing() {
        return this.spacing;
    }

    public void setSpacing(double spacing) {
        this.spacing = spacing;
    }

    public double getVerticalSpacing() {
        return this.verticalSpacing;
    }

    public void setVerticalSpacing(double verticalSpacing) {
        this.verticalSpacing = verticalSpacing;
    }

    public int getAngle() {
        return this.angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getFontFamily() {
        return this.fontFamily;
    }

    public void setFontFamily(String fontFamily) {
        this.fontFamily = fontFamily;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isMisaligned() {
        return this.misaligned;
    }

    public void setMisaligned(boolean misaligned) {
        this.misaligned = misaligned;
    }

    public JSONObject toJson() throws Exception {
        JSONObject json = new JSONObject();
        json.put("color", (Object)this.color);
        json.put("size", this.size);
        json.put("transparency", this.transparency);
        json.put("spacing", this.spacing);
        json.put("verticalSpacing", this.verticalSpacing);
        json.put("angle", this.angle);
        json.put("enabled", this.enabled);
        json.put("misaligned", this.misaligned);
        return json;
    }

    public void fromJson(JSONObject json) {
        this.color = json.optString("color");
        this.size = json.optInt("size");
        this.transparency = json.optDouble("transparency");
        this.spacing = json.optInt("spacing");
        this.verticalSpacing = json.optInt("verticalSpacing");
        this.angle = json.optInt("angle");
        this.enabled = json.optBoolean("enabled");
        this.misaligned = json.optBoolean("misaligned");
    }
}

