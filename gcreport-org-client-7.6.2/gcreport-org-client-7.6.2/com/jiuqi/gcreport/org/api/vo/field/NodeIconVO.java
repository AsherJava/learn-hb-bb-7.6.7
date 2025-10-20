/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.org.api.vo.field;

public class NodeIconVO {
    private String icon;
    private String color;

    public NodeIconVO(String icon, String color) {
        this.setColor(color);
        this.setIcon(icon);
    }

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
}

