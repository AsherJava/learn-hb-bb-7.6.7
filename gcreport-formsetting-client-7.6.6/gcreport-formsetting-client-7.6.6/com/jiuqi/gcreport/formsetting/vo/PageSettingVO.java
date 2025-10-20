/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.formsetting.vo;

public class PageSettingVO {
    private String title;
    private String filterCondition;
    private String displayCondition;

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFilterCondition() {
        return this.filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    public String getDisplayCondition() {
        return this.displayCondition;
    }

    public void setDisplayCondition(String displayCondition) {
        this.displayCondition = displayCondition;
    }

    public String toString() {
        return "PageSettingVO [title=" + this.title + ", filterCondition=" + this.filterCondition + ", displayCondition=" + this.displayCondition + "]";
    }
}

