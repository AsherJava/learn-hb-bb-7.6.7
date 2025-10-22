/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.logic.facade.extend.param;

public class CheckDesContext {
    private final String formSchemeKey;
    private final String formulaSchemeKey;

    public CheckDesContext(String formSchemeKey, String formulaSchemeKey) {
        this.formSchemeKey = formSchemeKey;
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public String getFormSchemeKey() {
        return this.formSchemeKey;
    }

    public String getFormulaSchemeKey() {
        return this.formulaSchemeKey;
    }
}

