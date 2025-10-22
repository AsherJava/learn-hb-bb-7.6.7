/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.i18n.helper.I18nHelper
 */
package com.jiuqi.nr.dataentry.internal.overview;

import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.i18n.helper.I18nHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

@Component
@DependsOn(value={"i18nHelperSupport"})
public class OverviewVirtualNodeI18n {
    @Autowired
    @Qualifier(value="process_btn")
    private I18nHelper i18nHelper;
    private final String CHINESE = "zh";
    private final String EMPTY_STR = "";

    private String getLanguage() {
        String language = NpContextHolder.getContext().getLocale().getLanguage();
        if (language == null || language.equals("zh")) {
            return "zh";
        }
        return language;
    }

    public String getLanguageName(String actionCode) {
        String language = this.getLanguage();
        if (language.equals("") || language.equals("zh")) {
            return "";
        }
        if (actionCode == null || this.i18nHelper.getMessage(actionCode).equals("")) {
            return "";
        }
        return this.i18nHelper.getMessage(actionCode);
    }
}

