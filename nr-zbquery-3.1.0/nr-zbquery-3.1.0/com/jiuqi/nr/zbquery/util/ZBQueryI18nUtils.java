/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.nr.zbquery.util;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Lazy(value=false)
public class ZBQueryI18nUtils {
    public static final String ResourceNotFound = "Resource not found";
    @Autowired
    @Qualifier(value="zbqueryMessageSource")
    private MessageSource zbqueryMessageSource;
    private static MessageSource messageSource;

    @PostConstruct
    private void init() {
        messageSource = this.zbqueryMessageSource;
    }

    public static String getMessage(String key, Object ... args) {
        try {
            String message = messageSource.getMessage(key, args, LocaleContextHolder.getLocale());
            if (!StringUtils.hasText(message)) {
                message = ResourceNotFound;
            }
            return message;
        }
        catch (Exception e) {
            return ResourceNotFound;
        }
    }
}

