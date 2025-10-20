/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.dataperiod;

public enum DataPeriodDisplayType {
    ONLY_PERIOD("\u53ea\u663e\u793a\u65f6\u671f"),
    PERIOD_SCENE("\u65f6\u671f+\u60c5\u666f");

    private final String title;

    private DataPeriodDisplayType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

