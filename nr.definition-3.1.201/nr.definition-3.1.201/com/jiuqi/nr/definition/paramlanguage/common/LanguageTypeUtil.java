/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.definition.paramlanguage.common;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.definition.paramlanguage.service.DefaultLanguageService;
import com.jiuqi.nr.definition.paramlanguage.service.LanguageMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageTypeUtil {
    @Autowired
    private LanguageMessageService languageMessageService;
    @Autowired
    private DefaultLanguageService defaultLanguageService;

    public int getCurrentLanguageType() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        try {
            return this.languageMessageService.getLanguageTypeByMessage(language);
        }
        catch (Exception e) {
            return this.defaultLanguageService.getDefaultLanguage();
        }
    }
}

