/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormGroupDefine;

public class FormGroupStream
extends AbstractParamStream<FormGroupDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public FormGroupStream(FormGroupDefine param) {
        super(param);
    }

    public FormGroupStream(FormGroupDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(FormGroupDefine formGroupDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadFormGroup(formGroupDefine.getKey(), entityKeyData, entityId);
    }

    @Override
    FormGroupDefine transI18n(FormGroupDefine formGroupDefine) {
        I18nRunTimeFormGroupDefine i18nRunTimeTaskDefine = new I18nRunTimeFormGroupDefine(formGroupDefine);
        i18nRunTimeTaskDefine.setTitle(this.languageController.getFormGroupTitle(formGroupDefine.getKey(), null));
        return i18nRunTimeTaskDefine;
    }
}

