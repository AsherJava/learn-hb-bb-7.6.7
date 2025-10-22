/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormDefine;

public class FormStream
extends AbstractParamStream<FormDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public FormStream(FormDefine param) {
        super(param);
    }

    public FormStream(FormDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(FormDefine formDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadForm(formDefine.getKey(), entityKeyData, entityId);
    }

    @Override
    FormDefine transI18n(FormDefine formDefine) {
        I18nRunTimeFormDefine i18nRunTimeTaskDefine = new I18nRunTimeFormDefine(formDefine);
        i18nRunTimeTaskDefine.setTitle(this.languageController.getFormTitle(formDefine.getKey(), null));
        i18nRunTimeTaskDefine.setBinaryData(this.languageController.getFormStyle(formDefine.getKey(), null));
        i18nRunTimeTaskDefine.setFillingGuide(this.languageController.getFullingGuide(formDefine.getKey(), null));
        return i18nRunTimeTaskDefine;
    }
}

