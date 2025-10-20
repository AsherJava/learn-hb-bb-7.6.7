/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  feign.RequestInterceptor
 *  feign.RequestTemplate
 */
package com.jiuqi.va.query.interceptor;

import com.jiuqi.va.query.interceptor.VaQueryFeignHeadersContextHolder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Order(value=0x7FFFFFFF)
public class VaQueryFeignAuthInterceptor
implements RequestInterceptor {
    public void apply(RequestTemplate template) {
        if (!template.path().contains("/api/datacenter/v1/userDefined") && !template.path().contains("/api/gcreport/v1/fetch/userDefined")) {
            return;
        }
        String token = VaQueryFeignHeadersContextHolder.getUserToken();
        if (!StringUtils.hasText(token)) {
            return;
        }
        template.removeHeader("Authorization");
        template.header("Authorization", new String[]{token});
    }
}

