/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.attachment.message;

public class FileCategoryInfo {
    private String code;
    private String title;
    private boolean selected = false;

    public FileCategoryInfo(String code, String title) {
        this.code = code;
        this.title = title;
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

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}

