/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.jiuqi.bi.office.excel.print;

import java.io.Serializable;
import org.json.JSONObject;

public final class Margin
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 5599843499944364754L;
    private double left = 1.8;
    private double right = 1.8;
    private double top = 1.9;
    private double bottom = 1.9;
    private double header = 0.8;
    private double footer = 0.8;

    public double getLeft() {
        return this.left;
    }

    public void setLeft(double left) {
        this.left = left;
    }

    public double getRight() {
        return this.right;
    }

    public void setRight(double right) {
        this.right = right;
    }

    public double getTop() {
        return this.top;
    }

    public void setTop(double top) {
        this.top = top;
    }

    public double getBottom() {
        return this.bottom;
    }

    public void setBottom(double bottom) {
        this.bottom = bottom;
    }

    public double getHeader() {
        return this.header;
    }

    public void setHeader(double header) {
        this.header = header;
    }

    public double getFooter() {
        return this.footer;
    }

    public void setFooter(double footer) {
        this.footer = footer;
    }

    public JSONObject toJson() {
        JSONObject json_margin = new JSONObject();
        json_margin.put("left", this.left);
        json_margin.put("right", this.right);
        json_margin.put("top", this.top);
        json_margin.put("bottom", this.bottom);
        json_margin.put("header", this.header);
        json_margin.put("footer", this.footer);
        return json_margin;
    }

    public void FromJson(JSONObject json_margin) {
        this.left = json_margin.optDouble("left", 1.8);
        this.right = json_margin.optDouble("right", 1.8);
        this.top = json_margin.optDouble("top", 1.9);
        this.bottom = json_margin.optDouble("bottom", 1.9);
        this.header = json_margin.optDouble("header", 0.8);
        this.footer = json_margin.optDouble("footer", 0.8);
    }

    protected Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}

