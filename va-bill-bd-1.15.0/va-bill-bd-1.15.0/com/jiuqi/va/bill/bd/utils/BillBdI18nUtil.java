/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.utils.VaI18nParamUtil
 *  javax.annotation.PostConstruct
 */
package com.jiuqi.va.bill.bd.utils;

import com.jiuqi.va.utils.VaI18nParamUtil;
import java.util.Locale;
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
public class BillBdI18nUtil {
    public static final String RESOURCE_NOT_FOUND = "Resource not found";
    @Autowired
    @Qualifier(value="billBdMessageSource")
    private MessageSource messageSourceBean;
    public static MessageSource messageSource;

    @PostConstruct
    private void init() {
        messageSource = this.messageSourceBean;
    }

    public static String getMessage(String key) {
        return BillBdI18nUtil.getMessage(key, null);
    }

    public static String getMessage(String key, Object[] args) {
        try {
            Locale locale = null;
            locale = VaI18nParamUtil.getTranslationEnabled() == false || LocaleContextHolder.getLocaleContext() == null || LocaleContextHolder.getLocaleContext().getLocale() == null ? Locale.CHINA : LocaleContextHolder.getLocale();
            String message = messageSource.getMessage(key, args, locale);
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

