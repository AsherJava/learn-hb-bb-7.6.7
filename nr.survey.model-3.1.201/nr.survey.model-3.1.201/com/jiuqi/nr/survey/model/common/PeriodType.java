/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.survey.model.common;

public enum PeriodType {
    DATE("2", "\u65e5\u671f"),
    DATETIME("102", "\u65e5\u671f\u65f6\u95f4");

    private String value;
    private String title;

    private PeriodType(String value, String title) {
        this.value = value;
        this.title = title;
    }

    public String getValue() {
        return this.value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

