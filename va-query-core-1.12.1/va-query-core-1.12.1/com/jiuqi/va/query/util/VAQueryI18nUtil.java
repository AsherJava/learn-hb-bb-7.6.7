/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.util;

import com.jiuqi.va.query.util.DCQuerySpringContextUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Lazy(value=false)
public class VAQueryI18nUtil {
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    public static MessageSource messageSource;

    public static String getMessage(String key) {
        return VAQueryI18nUtil.getMessage(key, null);
    }

    public static String getMessage(String key, Object[] args) {
        if (messageSource == null) {
            messageSource = (MessageSource)DCQuerySpringContextUtils.getBean("vaQueryMessageSource");
        }
        try {
            String message = messageSource.getMessage(key, null, LocaleContextHolder.getLocale());
            if (!StringUtils.hasText(message)) {
                message = RESOURCE_NOT_FOUND;
            }
            return message;
        }
        catch (Exception e) {
            return RESOURCE_NOT_FOUND;
        }
    }
}

