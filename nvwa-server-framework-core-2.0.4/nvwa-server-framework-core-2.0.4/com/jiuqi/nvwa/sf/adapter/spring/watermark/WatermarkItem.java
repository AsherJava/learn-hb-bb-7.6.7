/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.nvwa.sf.adapter.spring.watermark;

import org.json.JSONObject;

public class WatermarkItem {
    private boolean enabled;
    private String content;
    private String color;
    private int size;
    private double transparency;
    private double spacing;
    private double verticalSpacing;
    private int angle;
    private int mode;
    private boolean misaligned;

    private WatermarkItem() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public int getMode() {
        return this.mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
    }

    public boolean isMisaligned() {
        return this.misaligned;
    }

    public void setMisaligned(boolean misaligned) {
        this.misaligned = misaligned;
    }

    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("color", (Object)this.color);
        json.put("size", this.size);
        json.put("transparency", this.transparency);
        json.put("spacing", this.spacing);
        json.put("verticalSpacing", this.verticalSpacing);
        json.put("angle", this.angle);
        json.put("enabled", this.enabled);
        json.put("content", (Object)this.content);
        json.put("mode", this.mode);
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
        this.content = json.optString("content");
        this.mode = json.optInt("mode");
        this.misaligned = json.optBoolean("misaligned");
    }
}

