/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import java.util.Objects;

public class FormulaConditionLinkObj {
    private String key;
    private String formulaKey;
    private String schemeKey;
    private String code;
    private String title;
    private Boolean binding;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getFormulaKey() {
        return this.formulaKey;
    }

    public void setFormulaKey(String formulaKey) {
        this.formulaKey = formulaKey;
    }

    public String getSchemeKey() {
        return this.schemeKey;
    }

    public void setSchemeKey(String schemeKey) {
        this.schemeKey = schemeKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getBinding() {
        return this.binding;
    }

    public void setBinding(Boolean binding) {
        this.binding = binding;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        FormulaConditionLinkObj linkObj = (FormulaConditionLinkObj)o;
        return Objects.equals(this.formulaKey, linkObj.formulaKey) && Objects.equals(this.schemeKey, linkObj.schemeKey) && Objects.equals(this.code, linkObj.code);
    }

    public int hashCode() {
        return Objects.hash(this.formulaKey, this.schemeKey, this.code);
    }
}

