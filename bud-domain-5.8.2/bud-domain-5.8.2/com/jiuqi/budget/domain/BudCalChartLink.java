/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

public class BudCalChartLink {
    private String from;
    private String to;
    private boolean isHideArrow;
    private int lineShape;
    private String text;
    private String color;

    public void setColor(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public int getLineShape() {
        return this.lineShape;
    }

    public void setLineShape(int lineShape) {
        this.lineShape = lineShape;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean isHideArrow() {
        return this.isHideArrow;
    }

    public void setHideArrow(boolean isHideArrow) {
        this.isHideArrow = isHideArrow;
    }

    public String toString() {
        return "BudCalChartLink [from=" + this.from + ", to=" + this.to + ", isHideArrow=" + this.isHideArrow + "]";
    }
}

