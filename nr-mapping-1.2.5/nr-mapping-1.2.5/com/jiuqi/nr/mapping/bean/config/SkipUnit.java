/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.mapping.bean.config;

import java.util.List;

public class SkipUnit {
    public static final String UNITKEY_CODE = "unitKey";
    public static final String FORMULA_CODE = "formula";
    private List<String> unitKey;
    private String formula;

    public List<String> getUnitKey() {
        return this.unitKey;
    }

    public void setUnitKey(List<String> unitKey) {
        this.unitKey = unitKey;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }
}

