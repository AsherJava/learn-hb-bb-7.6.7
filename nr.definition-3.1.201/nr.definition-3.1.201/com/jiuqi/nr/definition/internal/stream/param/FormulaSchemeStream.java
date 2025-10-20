/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormulaSchemeDefine;

public class FormulaSchemeStream
extends AbstractParamStream<FormulaSchemeDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public FormulaSchemeStream(FormulaSchemeDefine param) {
        super(param);
    }

    public FormulaSchemeStream(FormulaSchemeDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(FormulaSchemeDefine formulaSchemeDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadFormulaScheme(formulaSchemeDefine.getKey());
    }

    @Override
    FormulaSchemeDefine transI18n(FormulaSchemeDefine formulaSchemeDefine) {
        I18nRunTimeFormulaSchemeDefine i18nRunTimeFormulaSchemeDefine = new I18nRunTimeFormulaSchemeDefine(formulaSchemeDefine);
        i18nRunTimeFormulaSchemeDefine.setTitle(this.languageController.getFormulaSchemeTitle(formulaSchemeDefine.getKey(), null));
        return i18nRunTimeFormulaSchemeDefine;
    }
}

