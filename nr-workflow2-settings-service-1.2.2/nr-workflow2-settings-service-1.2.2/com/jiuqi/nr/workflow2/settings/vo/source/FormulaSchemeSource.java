/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.settings.vo.source;

import java.util.List;
import java.util.Map;

public class FormulaSchemeSource {
    private String formSchemeKey;
    private String formSchemeTitle;
    private List<Map<String, Object>> formulaSchemeValues;

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public void setFormSchemeKey(String formSchemeKey) {
        this.formSchemeKey = formSchemeKey;
    }

    public String getFormSchemeTitle() {
        return this.formSchemeTitle;
    }

    public void setFormSchemeTitle(String formSchemeTitle) {
        this.formSchemeTitle = formSchemeTitle;
    }

    public List<Map<String, Object>> getFormulaSchemeValues() {
        return this.formulaSchemeValues;
    }

    public void setFormulaSchemeValues(List<Map<String, Object>> formulaSchemeValues) {
        this.formulaSchemeValues = formulaSchemeValues;
    }
}

