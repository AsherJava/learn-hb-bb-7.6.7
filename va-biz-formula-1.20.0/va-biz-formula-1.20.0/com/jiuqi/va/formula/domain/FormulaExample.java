/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.domain;

public class FormulaExample {
    private String scenario;
    private String formula;
    private String returnValue;
    private String definition;

    public String getScenario() {
        return this.scenario;
    }

    public void setScenario(String scenario) {
        this.scenario = scenario;
    }

    public String getFormula() {
        return this.formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public String getReturnValue() {
        return this.returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public String getDefinition() {
        return this.definition;
    }

    public void setDefinition(String definition) {
        this.definition = definition;
    }

    public FormulaExample(String scenario, String formula, String returnValue, String definition) {
        this.scenario = scenario;
        this.formula = formula;
        this.returnValue = returnValue;
        this.definition = definition;
    }

    public FormulaExample(String scenario, String formula, String returnValue) {
        this.scenario = scenario;
        this.formula = formula;
        this.returnValue = returnValue;
    }

    public FormulaExample() {
    }
}

