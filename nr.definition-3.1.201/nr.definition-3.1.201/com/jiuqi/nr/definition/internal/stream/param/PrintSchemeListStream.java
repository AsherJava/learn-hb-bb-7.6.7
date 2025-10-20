/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.stream.param;

import com.jiuqi.nr.definition.api.IParamLanguageController;
import com.jiuqi.nr.definition.auth.DefinitionAuthorityProvider;
import com.jiuqi.nr.definition.facade.PrintTemplateSchemeDefine;
import com.jiuqi.nr.definition.internal.stream.param.AbstractParamListStream;
import com.jiuqi.nr.definition.paramlanguage.bean.I18nRunTimePrintSchemeDefine;
import java.util.List;

public class PrintSchemeListStream
extends AbstractParamListStream<PrintTemplateSchemeDefine> {
    private DefinitionAuthorityProvider authorityProvider;
    private IParamLanguageController languageController;

    public PrintSchemeListStream(List<PrintTemplateSchemeDefine> param) {
        super(param);
    }

    public PrintSchemeListStream(List<PrintTemplateSchemeDefine> param, DefinitionAuthorityProvider authorityProvider, IParamLanguageController languageController) {
        super(param);
        this.authorityProvider = authorityProvider;
        this.languageController = languageController;
    }

    @Override
    boolean hasAuth(PrintTemplateSchemeDefine printTemplateSchemeDefine, String entityKeyData, String entityId) {
        return this.authorityProvider.canReadPrintScheme(printTemplateSchemeDefine.getKey());
    }

    @Override
    PrintTemplateSchemeDefine transI18n(PrintTemplateSchemeDefine printTemplateSchemeDefine) {
        I18nRunTimePrintSchemeDefine i18nRunTimePrintSchemeDefine = new I18nRunTimePrintSchemeDefine(printTemplateSchemeDefine);
        return i18nRunTimePrintSchemeDefine;
    }
}

