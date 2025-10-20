/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.MD5Util
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.domain.TenantDO
 *  feign.RequestInterceptor
 *  feign.RequestTemplate
 */
package com.jiuqi.va.feign.config;

import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.MD5Util;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.domain.TenantDO;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component(value="vaFeignAuthInterceptor")
@Lazy(value=false)
@ConfigurationProperties(prefix="va-auth-shiro")
public class FeignAuthInterceptor
implements RequestInterceptor {
    private static String vaTenantName = "VaTenantName";
    private static String language = "language";
    private static String timezone = "timezone";
    private static String trustCode = null;
    @Autowired(required=false)
    private DataSourceProperties dataSourceProperties;
    private Set<String> excludeClass = null;

    @Value(value="${va-auth-shiro.trust-code:}")
    public void setTrustCode(String code) {
        if (trustCode != null) {
            return;
        }
        if (StringUtils.hasText(code)) {
            trustCode = code;
            return;
        }
        try {
            if (this.dataSourceProperties != null) {
                String dspCode = this.dataSourceProperties.getUrl().trim() + this.dataSourceProperties.getUsername().trim() + this.dataSourceProperties.getPassword().trim();
                trustCode = MD5Util.encrypt((String)dspCode);
                return;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        trustCode = "fd76df302ef9471ab0fcbad25317ded9";
    }

    @Value(value="${feignClient.global.interceptor.exclude:}")
    public void setExcludeClass(String pakage) {
        if (this.excludeClass != null) {
            return;
        }
        this.excludeClass = new HashSet<String>();
        if (StringUtils.hasText(pakage)) {
            for (String str : pakage.split("\\,")) {
                String tstr = str.trim();
                if (tstr.isEmpty()) continue;
                this.excludeClass.add(tstr);
            }
        }
    }

    public void apply(RequestTemplate requestTemplate) {
        String traceid;
        if (this.excludeClass != null && !this.excludeClass.isEmpty() && this.excludeClass.contains(requestTemplate.feignTarget().type().getName())) {
            return;
        }
        if (!requestTemplate.headers().containsKey(language)) {
            Locale locale = LocaleContextHolder.getLocale();
            String languageTag = locale.toLanguageTag();
            requestTemplate.header(language, new String[]{languageTag});
        }
        if (!requestTemplate.headers().containsKey(timezone)) {
            TimeZone timeZone = LocaleContextHolder.getTimeZone();
            requestTemplate.header(timezone, new String[]{timeZone.getID()});
        }
        if (StringUtils.hasText(traceid = MDC.get("traceid"))) {
            requestTemplate.header("X-NVWA-TRACE-ID", new String[]{traceid});
        }
        requestTemplate.header("FeignClient", new String[]{"true"});
        requestTemplate.header("TrustCode", new String[]{trustCode});
        if (this.ingoreAnon(requestTemplate.url())) {
            return;
        }
        this.applyToken(requestTemplate);
    }

    private void applyToken(RequestTemplate requestTemplate) {
        if (requestTemplate.headers().containsKey("Authorization")) {
            return;
        }
        String token = ShiroUtil.getToken();
        if (StringUtils.hasText(token)) {
            requestTemplate.header("Authorization", new String[]{token});
            requestTemplate.header(vaTenantName, new String[]{ShiroUtil.getTenantName()});
            return;
        }
        Collection contentType = (Collection)requestTemplate.headers().get("Content-Type");
        TenantDO tenant = null;
        if (contentType.contains("application/msgpack")) {
            tenant = (TenantDO)JSONUtil.parseObject((byte[])((byte[])requestTemplate.body().clone()), TenantDO.class);
        } else {
            String json = new String(requestTemplate.body());
            tenant = (TenantDO)JSONUtil.parseObject((String)json, TenantDO.class);
        }
        if (tenant != null) {
            token = (String)tenant.getExtInfo("JTOKENID");
            requestTemplate.header(vaTenantName, new String[]{tenant.getTenantName()});
        }
        requestTemplate.header("Authorization", new String[]{token == null ? "" : token});
    }

    private boolean ingoreAnon(String url) {
        return url.contains("/anon/");
    }
}

