/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.domain.common.R
 *  javax.servlet.http.HttpServletRequest
 *  javax.servlet.http.HttpServletResponse
 *  org.springframework.web.context.request.RequestContextHolder
 *  org.springframework.web.context.request.ServletRequestAttributes
 */
package com.jiuqi.nvwa.login.sso.provider;

import com.jiuqi.nvwa.login.sso.common.NvwaSsoAR;
import com.jiuqi.va.domain.common.R;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public interface NvwaSsoOAuthProvider {
    @Deprecated
    default public NvwaSsoAR authenticate(HttpServletRequest request, HttpServletResponse response) throws Exception {
        return null;
    }

    default public NvwaSsoAR authenticate() throws Exception {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return this.authenticate(attributes.getRequest(), attributes.getResponse());
    }

    @Deprecated
    default public String getFailUrl(HttpServletRequest request, HttpServletResponse response, R r) {
        return null;
    }

    default public String getFailUrl(R r) {
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        return this.getFailUrl(attributes.getRequest(), attributes.getResponse(), r);
    }

    public String getProviderV();

    default public String getTitle() {
        return this.getProviderV();
    }

    default public boolean cacheToken() {
        return true;
    }

    default public String getCacheKey() {
        return "tokenId";
    }

    default public void loginSuccess(String curToken) {
    }

    default public void finsh() {
    }
}

