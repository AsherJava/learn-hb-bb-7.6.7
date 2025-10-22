/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormGroupDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamListStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormGroupDefine;
import java.util.List;

public class FormGroupListStream
extends AbstractParamListStream<FormGroupDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public FormGroupListStream(List<FormGroupDefine> param) {
        super(param);
    }

    public FormGroupListStream(List<FormGroupDefine> param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(FormGroupDefine groupDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadFormGroup(groupDefine.getKey(), entityKeyData, entityId);
    }

    @Override
    FormGroupDefine transI18n(FormGroupDefine groupDefine) {
        I18nRunTimeFormGroupDefine i18nRunTimeFormGroupDefine = new I18nRunTimeFormGroupDefine(groupDefine);
        i18nRunTimeFormGroupDefine.setTitle(this.languageController.getFormGroupTitle(groupDefine.getKey(), null));
        return i18nRunTimeFormGroupDefine;
    }
}

