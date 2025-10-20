/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.component.domain;

import java.util.Map;

public class FormulaExeParam {
    private String formulaExpress;
    private String code;
    private Map<String, String> dimValMap;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getFormulaExpress() {
        return this.formulaExpress;
    }

    public void setFormulaExpress(String formulaExpress) {
        this.formulaExpress = formulaExpress;
    }

    public Map<String, String> getDimValMap() {
        return this.dimValMap;
    }

    public void setDimValMap(Map<String, String> dimValMap) {
        this.dimValMap = dimValMap;
    }
}

