/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.analysis.define;

public class AnalysisCaliber {
    private int index;
    private String condition;

    public AnalysisCaliber(int index, String condition) {
        this.index = index;
        this.condition = condition;
    }

    public int getIndex() {
        return this.index;
    }

    public String getCondition() {
        return this.condition;
    }

    public String toString() {
        return "AnalysisCaliber [index=" + this.index + ":" + this.condition + "]";
    }
}

