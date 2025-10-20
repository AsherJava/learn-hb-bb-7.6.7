/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormulaDefine;

public class FormulaStream
extends AbstractParamStream<FormulaDefine> {
    private IParamLanguageController languageController;

    public FormulaStream(FormulaDefine param) {
        super(param);
    }

    public FormulaStream(FormulaDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(FormulaDefine formulaDefine, String entityKeyData, String entityId) {
        return true;
    }

    @Override
    FormulaDefine transI18n(FormulaDefine formulaDefine) {
        I18nRunTimeFormulaDefine i18nRunTimeFormulaDefine = new I18nRunTimeFormulaDefine(formulaDefine);
        i18nRunTimeFormulaDefine.setDescription(this.languageController.getFormulaDescript(formulaDefine.getKey(), null));
        return i18nRunTimeFormulaDefine;
    }
}

