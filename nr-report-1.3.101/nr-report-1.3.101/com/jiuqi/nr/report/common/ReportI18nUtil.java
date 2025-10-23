/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Resource
 */
package com.jiuqi.nr.report.common;

import java.util.Locale;
import javax.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class ReportI18nUtil
implements InitializingBean {
    private static Logger logger = LoggerFactory.getLogger(ReportI18nUtil.class);
    @Resource(name="reportMessageSource")
    private MessageSource messageSourceBean;
    private static MessageSource messageSource;
    @Value(value="${jiuqi.nvwa.i18n.default:zh_CN}")
    private String localeStr;
    private static Locale locale;

    @Override
    public void afterPropertiesSet() throws Exception {
        messageSource = this.messageSourceBean;
        String[] strs = this.localeStr.split("_");
        locale = new Locale(strs[0], strs[1]);
    }

    public static String getMessage(String key, Object ... args) {
        String message = key;
        try {
            Locale localeLanguage = null;
            localeLanguage = LocaleContextHolder.getLocaleContext() == null || LocaleContextHolder.getLocaleContext().getLocale() == null ? locale : LocaleContextHolder.getLocale();
            String i18nMessage = messageSource.getMessage(key, args, localeLanguage);
            if (StringUtils.hasText(i18nMessage)) {
                message = i18nMessage;
            }
        }
        catch (NoSuchMessageException e) {
            logger.error("error read i18n message", e);
        }
        return message;
    }
}

