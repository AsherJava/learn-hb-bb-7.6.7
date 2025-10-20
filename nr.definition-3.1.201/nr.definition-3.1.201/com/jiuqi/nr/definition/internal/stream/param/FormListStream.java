/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.FormDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamListStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimeFormDefine;
import java.util.List;

public class FormListStream
extends AbstractParamListStream<FormDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public FormListStream(List<FormDefine> param) {
        super(param);
    }

    public FormListStream(List<FormDefine> param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
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
        I18nRunTimeFormDefine i18nRunTimeFormDefine = new I18nRunTimeFormDefine(formDefine);
        i18nRunTimeFormDefine.setTitle(this.languageController.getFormTitle(formDefine.getKey(), null));
        i18nRunTimeFormDefine.setBinaryData(this.languageController.getFormStyle(formDefine.getKey(), null));
        i18nRunTimeFormDefine.setFillingGuide(this.languageController.getFullingGuide(formDefine.getKey(), null));
        return i18nRunTimeFormDefine;
    }
}

