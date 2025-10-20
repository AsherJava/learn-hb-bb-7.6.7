/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.result;

import java.util.ArrayList;
import java.util.List;

public class WritebackMapInfo {
    private String targetFieldName;
    private List<String> srcFieldNames = new ArrayList<String>();
    private List<Formula> formulas = new ArrayList<Formula>();

    public String getTargetFieldName() {
        return this.targetFieldName;
    }

    public void setTargetFieldName(String targetFieldName) {
        this.targetFieldName = targetFieldName;
    }

    public List<String> getSrcFieldNames() {
        return this.srcFieldNames;
    }

    public List<Formula> getFormulas() {
        return this.formulas;
    }

    public static class Formula {
        public final String expression;
        public final String explain;

        public Formula(String expression, String explain) {
            this.expression = expression;
            this.explain = explain;
        }
    }
}

