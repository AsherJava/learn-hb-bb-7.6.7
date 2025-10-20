/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.analysisreport.chapter.common;

public enum CatalogLevel {
    h1("h1", 1),
    h2("h2", 2),
    h3("h3", 3),
    h4("h4", 4);

    private String tag;
    private int level;

    private CatalogLevel(String tag, int level) {
        this.tag = tag;
        this.level = level;
    }

    public String getTag() {
        return this.tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}

