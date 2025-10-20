/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.controller.IFormulaRunTimeController;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.facade.ICmpFmlProvider;
import java.util.List;

public class DefCmpFmlProvider
implements ICmpFmlProvider {
    private final IFormulaRunTimeController formulaRunTimeController;
    private final String formulaSchemeKey;
    private String formKey;
    private boolean useForm;

    public DefCmpFmlProvider(IFormulaRunTimeController formulaRunTimeController, String formulaSchemeKey) {
        this.formulaRunTimeController = formulaRunTimeController;
        this.formulaSchemeKey = formulaSchemeKey;
    }

    public DefCmpFmlProvider(IFormulaRunTimeController formulaRunTimeController, String formulaSchemeKey, String formKey) {
        this.formulaRunTimeController = formulaRunTimeController;
        this.formulaSchemeKey = formulaSchemeKey;
        this.formKey = formKey;
        this.useForm = true;
    }

    @Override
    public FormulaSchemeDefine getFmlScheme() {
        return this.formulaRunTimeController.queryFormulaSchemeDefine(this.formulaSchemeKey);
    }

    @Override
    public List<FormulaDefine> getCmpFml() {
        if (this.useForm) {
            return this.formulaRunTimeController.getAllFormulasInForm(this.formulaSchemeKey, this.formKey);
        }
        return this.formulaRunTimeController.getAllFormulasInScheme(this.formulaSchemeKey);
    }
}

