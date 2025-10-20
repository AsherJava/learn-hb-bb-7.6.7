/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.organization.common;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class OrgCoreI18nUtil {
    private static Logger logger = LoggerFactory.getLogger(OrgCoreI18nUtil.class);
    private static MessageSource messageSource;

    private static MessageSource getMessageSource() {
        if (messageSource == null) {
            messageSource = (MessageSource)ApplicationContextRegister.getBean((String)"vaOrganizationCoreMessageSource");
        }
        return messageSource;
    }

    public static String getMessage(String key, Object ... args) {
        try {
            Locale locale = null;
            LocaleContext context = LocaleContextHolder.getLocaleContext();
            locale = context == null || context.getLocale() == null ? EnvConfig.getDefaultLocale() : LocaleContextHolder.getLocale();
            String message = OrgCoreI18nUtil.getMessageSource().getMessage(key, args, locale);
            if (!StringUtils.hasText(message)) {
                message = key;
            }
            return message;
        }
        catch (Exception e) {
            logger.error("Get localized language exception", e);
            return key;
        }
    }
}

