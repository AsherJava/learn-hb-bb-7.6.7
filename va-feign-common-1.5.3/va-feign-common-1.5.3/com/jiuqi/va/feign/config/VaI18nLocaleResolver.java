/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.springframework.web.servlet.i18n.SessionLocaleResolver
 *  org.springframework.web.util.WebUtils
 */
package com.jiuqi.va.feign.config;

import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentHashMap;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.util.WebUtils;

@Component(value="localeResolver")
@Lazy(value=false)
@ConditionalOnMissingClass(value={"com.jiuqi.np.i18n.dto.SessionLocaleByHeaderResolver"})
public class VaI18nLocaleResolver
extends SessionLocaleResolver
implements InitializingBean {
    private String timeZoneAttributeName = TIME_ZONE_SESSION_ATTRIBUTE_NAME;
    @Value(value="${jiuqi.nvwa.i18n.default:zh_CN}")
    private String defaultVal;
    private static Map<String, Locale> cache = new ConcurrentHashMap<String, Locale>();
    private Map<String, TimeZone> cacheTimeZone = new ConcurrentHashMap<String, TimeZone>();

    public Locale resolveLocale(HttpServletRequest request) {
        Locale local = this.getLocal(request);
        if (local != null) {
            return local;
        }
        try {
            return super.resolveLocale(request);
        }
        catch (Exception e) {
            return super.getDefaultLocale();
        }
    }

    public LocaleContext resolveLocaleContext(final HttpServletRequest request) {
        return new TimeZoneAwareLocaleContext(){

            @Override
            public Locale getLocale() {
                return VaI18nLocaleResolver.this.resolveLocale(request);
            }

            @Override
            @Nullable
            public TimeZone getTimeZone() {
                TimeZone timeZone = this.getTimeZone(request);
                if (null == timeZone) {
                    try {
                        timeZone = (TimeZone)WebUtils.getSessionAttribute((HttpServletRequest)request, (String)VaI18nLocaleResolver.this.timeZoneAttributeName);
                        if (timeZone == null) {
                            timeZone = VaI18nLocaleResolver.this.determineDefaultTimeZone(request);
                        }
                    }
                    catch (Exception e) {
                        return VaI18nLocaleResolver.this.determineDefaultTimeZone(request);
                    }
                }
                return timeZone;
            }

            private TimeZone getTimeZone(HttpServletRequest request2) {
                try {
                    String timezone = request2.getHeader("timezone");
                    if (!StringUtils.hasLength(timezone)) {
                        timezone = request2.getParameter("timezone");
                    }
                    if (StringUtils.hasLength(timezone)) {
                        TimeZone timeZone = (TimeZone)VaI18nLocaleResolver.this.cacheTimeZone.get(timezone);
                        if (null != timeZone) {
                            return timeZone;
                        }
                        timeZone = TimeZone.getTimeZone(timezone);
                        VaI18nLocaleResolver.this.cacheTimeZone.put(timezone, timeZone);
                        return timeZone;
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                return null;
            }
        };
    }

    private Locale getLocal(HttpServletRequest request) {
        try {
            String language = request.getHeader("language");
            if (!StringUtils.hasLength(language)) {
                language = request.getParameter("language");
            }
            if (StringUtils.hasLength(language)) {
                Locale locale = cache.get(language = language.replace("-", "_"));
                if (locale != null) {
                    return locale;
                }
                locale = this.getLocaleByLanguage(language);
                cache.put(language, locale);
                return locale;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return null;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Locale locale;
        String defaultValue = System.getProperty("jiuqi.nvwa.i18n.default");
        if (!StringUtils.hasLength(defaultValue)) {
            defaultValue = this.defaultVal;
        }
        if ((locale = this.getLocaleByLanguage(defaultValue)) != null) {
            this.setDefaultLocale(locale);
        }
    }

    private Locale getLocaleByLanguage(String language) {
        String[] splits = language.split("_");
        if (splits != null && splits.length == 2) {
            return new Locale(splits[0], splits[1]);
        }
        return null;
    }
}

