/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.formulapenetration.defines;

public class ExpressionNode {
    public static final String EXPANDNODEID = "expand";
    private String id;
    private String text;
    private String color;
    private String borderColor;
    private int nodeShape = 1;
    private int width;
    private int height;
    private boolean isHide;
    private boolean disableDrag = true;
    private Object data;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public int getNodeShape() {
        return this.nodeShape;
    }

    public void setNodeShape(int nodeShape) {
        this.nodeShape = nodeShape;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHide() {
        return this.isHide;
    }

    public void setHide(boolean isHide) {
        this.isHide = isHide;
    }

    public boolean isDisableDrag() {
        return this.disableDrag;
    }

    public void setDisableDrag(boolean disableDrag) {
        this.disableDrag = disableDrag;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

