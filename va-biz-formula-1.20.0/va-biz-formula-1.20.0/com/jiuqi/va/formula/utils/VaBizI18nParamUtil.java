/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.formula.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="jiuqi.i18n.va")
@Lazy(value=false)
public class VaBizI18nParamUtil {
    private static Boolean translationEnabled = false;

    public static Boolean getTranslationEnabled() {
        return translationEnabled;
    }

    public void setTranslationEnabled(Boolean translationEnabled) {
        VaBizI18nParamUtil.translationEnabled = translationEnabled;
    }
}

