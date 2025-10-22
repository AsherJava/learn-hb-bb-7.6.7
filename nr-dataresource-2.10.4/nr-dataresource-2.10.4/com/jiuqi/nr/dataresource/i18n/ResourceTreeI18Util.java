/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.np.core.utils.SpringBeanUtils
 */
package com.jiuqi.nr.dataresource.i18n;

import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.np.core.utils.SpringBeanUtils;
import java.util.Locale;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.StringUtils;

public class ResourceTreeI18Util {
    private static final String ROOT_GROUP = "nr.dataresource.root";

    private ResourceTreeI18Util() {
        throw new IllegalStateException("ResourceTreeI18Util class");
    }

    public static String getRootTitle() {
        return ResourceTreeI18Util.parseMsg(ROOT_GROUP);
    }

    private static String parseMsg(String code) {
        MessageSource messageSource = (MessageSource)SpringBeanUtils.getBean((String)"DataResourceMessageSource");
        return messageSource.getMessage(code, null, ResourceTreeI18Util.getCurrentLocale());
    }

    private static Locale getCurrentLocale() {
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

