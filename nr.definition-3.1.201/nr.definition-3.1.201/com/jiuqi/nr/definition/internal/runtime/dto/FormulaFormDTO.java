/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.internal.runtime.dto.FormulaDTO;
import java.util.List;
import java.util.Map;

public class FormulaFormDTO {
    private final String formKey;
    private final List<FormulaDTO> formulas;
    private List<String> calDatalinks;
    private Map<String, String> blanceExpressions;
    private String js;

    public FormulaFormDTO(String formKey, List<FormulaDTO> formulas) {
        this.formKey = formKey;
        this.formulas = formulas;
    }

    public void setCalDatalinks(List<String> calDatalinks) {
        this.calDatalinks = calDatalinks;
    }

    public void setBlanceExpressions(Map<String, String> blanceExpressions) {
        this.blanceExpressions = blanceExpressions;
    }

    public void setJs(String js) {
        this.js = js;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public List<String> getCalDatalinks() {
        return this.calDatalinks;
    }

    public Map<String, String> getBlanceExpressions() {
        return this.blanceExpressions;
    }

    public String getJs() {
        return this.js;
    }

    public List<FormulaDTO> getFormulas() {
        return this.formulas;
    }
}

