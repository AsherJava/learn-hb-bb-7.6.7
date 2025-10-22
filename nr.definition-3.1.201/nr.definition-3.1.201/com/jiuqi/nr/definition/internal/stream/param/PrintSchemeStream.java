/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimePrintSchemeDefine;

public class PrintSchemeStream
extends AbstractParamStream<PrintTemplateSchemeDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public PrintSchemeStream(PrintTemplateSchemeDefine param) {
        super(param);
    }

    public PrintSchemeStream(PrintTemplateSchemeDefine param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(PrintTemplateSchemeDefine printTemplateSchemeDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadPrintScheme(printTemplateSchemeDefine.getKey());
    }

    @Override
    PrintTemplateSchemeDefine transI18n(PrintTemplateSchemeDefine printSchemeDefine) {
        I18nRunTimePrintSchemeDefine i18nRunTimeTaskDefine = new I18nRunTimePrintSchemeDefine(printSchemeDefine);
        return i18nRunTimeTaskDefine;
    }
}

