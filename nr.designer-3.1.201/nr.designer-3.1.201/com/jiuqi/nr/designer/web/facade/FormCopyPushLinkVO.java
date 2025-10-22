/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.designer.web.facade;

import java.util.List;

public class FormCopyPushLinkVO {
    private String srcFormSchemeKey;
    private boolean fiFormula;
    private List<String> desFormSchemeKeys;
    private List<String> desFormSchemeNoExistIfFiFormulas;

    public String getSrcFormSchemeKey() {
        return this.srcFormSchemeKey;
    }

    public void setSrcFormSchemeKey(String srcFormSchemeKey) {
        this.srcFormSchemeKey = srcFormSchemeKey;
    }

    public boolean isFiFormula() {
        return this.fiFormula;
    }

    public void setFiFormula(boolean fiFormula) {
        this.fiFormula = fiFormula;
    }

    public List<String> getDesFormSchemeKeys() {
        return this.desFormSchemeKeys;
    }

    public void setDesFormSchemeKeys(List<String> desFormSchemeKeys) {
        this.desFormSchemeKeys = desFormSchemeKeys;
    }

    public List<String> getDesFormSchemeNoExistIfFiFormulas() {
        return this.desFormSchemeNoExistIfFiFormulas;
    }

    public void setDesFormSchemeNoExistIfFiFormulas(List<String> desFormSchemeNoExistIfFiFormulas) {
        this.desFormSchemeNoExistIfFiFormulas = desFormSchemeNoExistIfFiFormulas;
    }
}

