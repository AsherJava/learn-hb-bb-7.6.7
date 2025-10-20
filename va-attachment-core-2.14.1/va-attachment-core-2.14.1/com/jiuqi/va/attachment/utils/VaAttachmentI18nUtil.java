/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.attachment.utils;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class VaAttachmentI18nUtil {
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    private static MessageSource messageSource = null;

    private static MessageSource getMessageSource() {
        if (messageSource == null) {
            messageSource = (MessageSource)ApplicationContextRegister.getBean((String)"vaAttachmentMessageSource");
        }
        return messageSource;
    }

    public static String getMessage(String key, Object ... args) {
        try {
            Locale locale = null;
            LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
            locale = localeContext == null || localeContext.getLocale() == null ? EnvConfig.getDefaultLocale() : LocaleContextHolder.getLocale();
            String message = VaAttachmentI18nUtil.getMessageSource().getMessage(key, args, locale);
            if (!StringUtils.hasText(message)) {
                message = key;
            }
            return message;
        }
        catch (Exception e) {
            return RESOURCE_NOT_FOUND;
        }
    }
}

