/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.domain;

import com.jiuqi.va.formula.domain.FormulaDescription;
import java.util.List;
import java.util.Map;

public class FormulasVO {
    private String name;
    private String title;
    private String description;
    private FormulaDescription formulaDescription;
    private List<Map<String, Object>> paramGuide;
    private boolean infiniteParameter;
    private String functionName;
    private String formulaText;

    public FormulasVO() {
    }

    public FormulasVO(String name, String title, String description) {
        this.name = name;
        this.title = title;
        this.description = description;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Map<String, Object>> getParamGuide() {
        return this.paramGuide;
    }

    public void setParamGuide(List<Map<String, Object>> paramGuide) {
        this.paramGuide = paramGuide;
    }

    public boolean isInfiniteParameter() {
        return this.infiniteParameter;
    }

    public void setInfiniteParameter(boolean infiniteParameter) {
        this.infiniteParameter = infiniteParameter;
    }

    public String getFunctionName() {
        return this.functionName;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public FormulaDescription getFormulaDescription() {
        return this.formulaDescription;
    }

    public void setFormulaDescription(FormulaDescription formulaDescription) {
        this.formulaDescription = formulaDescription;
    }

    public String getFormulaText() {
        return this.formulaText;
    }

    public void setFormulaText(String formulaText) {
        this.formulaText = formulaText;
    }
}

