/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamListStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormSchemeDefine;
import java.util.List;

public class FormSchemeListStream
extends AbstractParamListStream<FormSchemeDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public FormSchemeListStream(List<FormSchemeDefine> param) {
        super(param);
    }

    public FormSchemeListStream(List<FormSchemeDefine> param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(FormSchemeDefine schemeDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadFormScheme(schemeDefine.getKey());
    }

    @Override
    FormSchemeDefine transI18n(FormSchemeDefine schemeDefine) {
        I18nRunTimeFormSchemeDefine i18nRunTimeFormSchemeDefine = new I18nRunTimeFormSchemeDefine(schemeDefine);
        i18nRunTimeFormSchemeDefine.setTitle(this.languageController.getFormSchemeTitle(schemeDefine.getKey(), null));
        return i18nRunTimeFormSchemeDefine;
    }
}

