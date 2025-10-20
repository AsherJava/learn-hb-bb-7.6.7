/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormSchemeDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormSchemeDefine;

public class FormSchemeStream
extends AbstractParamStream<FormSchemeDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public FormSchemeStream(FormSchemeDefine param) {
        super(param);
    }

    public FormSchemeStream(FormSchemeDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
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

