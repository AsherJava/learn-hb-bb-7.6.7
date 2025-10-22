/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.dafafill.util;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class NrDataFillI18nUtil {
    public static String buildCode(String code) {
        return String.format("{{%s}}", code);
    }

    public static String parseMsg(String msg) {
        MessageSource messageSource = (MessageSource)SpringBeanUtils.getBean((String)"DataFillMessageSource");
        String regex = "\\{\\{[\\w\\.]+\\}\\}";
        Pattern pattern = Pattern.compile("\\{\\{[\\w\\.]+\\}\\}", 8);
        Matcher matcher = pattern.matcher(msg);
        while (matcher.find()) {
            String i18nContent = messageSource.getMessage(matcher.group(0).substring(2, matcher.group(0).length() - 2), null, NrDataFillI18nUtil.getCurrentLocale());
            msg = msg.replace(matcher.group(0), i18nContent);
        }
        return msg;
    }

    public static Locale getCurrentLocale() {
        Locale currentLocale = Locale.SIMPLIFIED_CHINESE;
        String npLang = Optional.ofNullable(NpContextHolder.getContext()).map(NpContext::getLocale).map(Locale::getLanguage).orElse(null);
        if (StringUtils.hasLength(npLang) && npLang.contains("en")) {
            currentLocale = Locale.US;
        } else {
            String language = Optional.of(LocaleContextHolder.getLocale()).map(Locale::getLanguage).orElse(null);
            if (StringUtils.hasLength(language) && language.contains("en")) {
                currentLocale = Locale.US;
            }
        }
        return currentLocale;
    }
}

