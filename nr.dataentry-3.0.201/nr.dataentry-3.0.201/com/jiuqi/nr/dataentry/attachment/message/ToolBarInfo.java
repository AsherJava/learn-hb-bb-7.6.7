/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

public class ToolBarInfo {
    private String code;
    private String title;
    private String icon;
    private Boolean disable = true;

    public ToolBarInfo() {
    }

    public ToolBarInfo(String code, String title, String icon) {
        this.code = code;
        this.title = title;
        this.icon = icon;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getDisable() {
        return this.disable;
    }

    public void setDisable(Boolean disable) {
        this.disable = disable;
    }
}

