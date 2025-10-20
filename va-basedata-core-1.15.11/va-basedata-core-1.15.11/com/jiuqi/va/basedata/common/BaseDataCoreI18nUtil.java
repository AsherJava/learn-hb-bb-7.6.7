/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 */
package com.jiuqi.va.basedata.common;

import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class BaseDataCoreI18nUtil {
    private static Logger logger = LoggerFactory.getLogger(BaseDataCoreI18nUtil.class);
    private static MessageSource messageSource;
    public static final String LANGUAGE_TRANS_FLAG = "languageTransFlag";

    private static MessageSource getMessageSource() {
        if (messageSource == null) {
            messageSource = (MessageSource)ApplicationContextRegister.getBean((String)"vaBaseDataCoreMessageSource");
        }
        return messageSource;
    }

    public static String getMessage(String key, Object ... args) {
        try {
            Locale locale = null;
            LocaleContext localeContext = LocaleContextHolder.getLocaleContext();
            locale = localeContext == null || localeContext.getLocale() == null ? EnvConfig.getDefaultLocale() : LocaleContextHolder.getLocale();
            String message = BaseDataCoreI18nUtil.getMessageSource().getMessage(key, args, locale);
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

    public static String getOptSuccessMsg() {
        return BaseDataCoreI18nUtil.getMessage("basedata.success.common.operate", new Object[0]);
    }

    public static String getOptFailureMsg() {
        return BaseDataCoreI18nUtil.getMessage("basedata.error.common.operate", new Object[0]);
    }

    public static String getParamMissingMsg() {
        return BaseDataCoreI18nUtil.getMessage("basedata.error.parameter.missing", new Object[0]);
    }
}

