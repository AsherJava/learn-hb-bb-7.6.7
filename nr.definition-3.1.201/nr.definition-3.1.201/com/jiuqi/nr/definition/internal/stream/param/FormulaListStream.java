/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormulaDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamListStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormulaDefine;
import java.util.List;

public class FormulaListStream
extends AbstractParamListStream<FormulaDefine> {
    private IParamLanguageController languageController;

    public FormulaListStream(List<FormulaDefine> param) {
        super(param);
    }

    public FormulaListStream(List<FormulaDefine> param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
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

