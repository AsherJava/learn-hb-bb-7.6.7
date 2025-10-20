/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.i18n.language.LanguageType
 */
package com.jiuqi.nr.definition.paramlanguage.service.impl;

import com.jiuqi.nr.datascheme.i18n.language.LanguageType;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import org.springframework.stereotype.Service;

@Service
public class DefaultLanguageServiceImpl
implements DefaultLanguageService {
    @Override
    public int getDefaultLanguage() {
        return LanguageType.DEFAULT.getValue();
    }

    @Override
    public boolean checkEnableMultiLanguage() {
        return LanguageType.enableMultiLanguage();
    }
}

