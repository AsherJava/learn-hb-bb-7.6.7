/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.rest.param;

import java.util.HashSet;
import java.util.Set;

public class FormulaSearchPM {
    private String language;
    private String formKey;
    private String formulascheme;
    private Set<Integer> formulaType;
    private Set<Integer> auditType;
    private Set<String> conditionCode;

    public String getLanguage() {
        return this.language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public String getFormulascheme() {
        return this.formulascheme;
    }

    public void setFormulascheme(String formulascheme) {
        this.formulascheme = formulascheme;
    }

    public Set<Integer> getFormulaType() {
        if (this.formulaType == null) {
            this.formulaType = new HashSet<Integer>();
        }
        return this.formulaType;
    }

    public void setFormulaType(Set<Integer> formulaType) {
        this.formulaType = formulaType;
    }

    public Set<Integer> getAuditType() {
        if (this.auditType == null) {
            this.auditType = new HashSet<Integer>();
        }
        return this.auditType;
    }

    public void setAuditType(Set<Integer> auditType) {
        this.auditType = auditType;
    }

    public Set<String> getConditionCode() {
        if (this.conditionCode == null) {
            this.conditionCode = new HashSet<String>();
        }
        return this.conditionCode;
    }

    public void setConditionCode(Set<String> conditionCode) {
        this.conditionCode = conditionCode;
    }
}

