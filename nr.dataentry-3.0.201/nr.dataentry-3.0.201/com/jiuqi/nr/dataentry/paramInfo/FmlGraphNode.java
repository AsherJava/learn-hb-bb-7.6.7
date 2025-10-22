/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.paramInfo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.UUID;

public class FmlGraphNode
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id = UUID.randomUUID().toString();
    private String text = "  /r/n ";
    private String borderColor = null;
    private String color = null;
    private HashMap<String, Boolean> data;
    private boolean disableDrag = true;
    private int height = 0;
    private boolean hide = false;
    private int nodeShape = 1;
    private int width = 0;
    private int expandHolderPosition;
    private boolean expanded = false;

    public FmlGraphNode(boolean isLeaf) {
        this.expandHolderPosition = isLeaf ? 0 : 1;
        HashMap<String, Boolean> map = new HashMap<String, Boolean>();
        map.put("hasDebugMessage", false);
        this.data = map;
    }

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

    public String getBorderColor() {
        return this.borderColor;
    }

    public void setBorderColor(String borderColor) {
        this.borderColor = borderColor;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public HashMap<String, Boolean> getData() {
        return this.data;
    }

    public void setData(HashMap<String, Boolean> data) {
        this.data = data;
    }

    public boolean isDisableDrag() {
        return this.disableDrag;
    }

    public void setDisableDrag(boolean disableDrag) {
        this.disableDrag = disableDrag;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public boolean isHide() {
        return this.hide;
    }

    public void setHide(boolean hide) {
        this.hide = hide;
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

    public int getExpandHolderPosition() {
        return this.expandHolderPosition;
    }

    public void setExpandHolderPosition(int expandHolderPosition) {
        this.expandHolderPosition = expandHolderPosition;
    }

    public boolean isExpanded() {
        return this.expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}

