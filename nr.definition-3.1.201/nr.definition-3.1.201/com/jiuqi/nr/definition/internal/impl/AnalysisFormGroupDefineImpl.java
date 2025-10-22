/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.facade.DesignAnalysisFormGroupDefine;

public class AnalysisFormGroupDefineImpl
implements DesignAnalysisFormGroupDefine {
    private String expression;

    @Override
    public String getConditionFormula() {
        return this.expression;
    }

    @Override
    public void setConditionFormula(String expression) {
        this.expression = expression;
    }
}

