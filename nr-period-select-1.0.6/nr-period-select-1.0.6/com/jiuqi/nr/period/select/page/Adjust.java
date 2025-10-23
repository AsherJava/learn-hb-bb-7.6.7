/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.period.select.page;

public class Adjust {
    private String code;
    private String title;
    private String period;
    private boolean selectable = true;
    private boolean selected = false;

    public Adjust() {
    }

    public Adjust(String code, String title, String period) {
        this.code = code;
        this.title = title;
        this.period = period;
    }

    public boolean isSelectable() {
        return this.selectable;
    }

    public void setSelectable(boolean selectable) {
        this.selectable = selectable;
    }

    public String getPeriod() {
        return this.period;
    }

    public void setPeriod(String period) {
        this.period = period;
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

