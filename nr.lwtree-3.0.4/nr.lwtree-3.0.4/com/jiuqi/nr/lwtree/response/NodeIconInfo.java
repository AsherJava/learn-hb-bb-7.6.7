/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.lwtree.response;

public class NodeIconInfo {
    private String icon;
    private String color;

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void assign(NodeIconInfo info) {
        if (info != null) {
            this.icon = info.getIcon();
            this.color = info.getColor();
        }
    }
}

