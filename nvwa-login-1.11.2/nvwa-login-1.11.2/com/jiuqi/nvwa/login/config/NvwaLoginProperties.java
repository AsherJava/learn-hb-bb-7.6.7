/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.login.config;

import com.jiuqi.nvwa.login.config.CookieProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="jiuqi.nvwa.login")
public class NvwaLoginProperties {
    private boolean ignoreCase = true;
    private String clientIpHeader = "";
    private boolean logStackTrace = false;
    private boolean encrypt = false;
    private CookieProperties cookie = new CookieProperties();

    public boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    public void setIgnoreCase(boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
    }

    public String getClientIpHeader() {
        return this.clientIpHeader;
    }

    public void setClientIpHeader(String clientIpHeader) {
        this.clientIpHeader = clientIpHeader;
    }

    public boolean isLogStackTrace() {
        return this.logStackTrace;
    }

    public void setLogStackTrace(boolean logStackTrace) {
        this.logStackTrace = logStackTrace;
    }

    public boolean isEncrypt() {
        return this.encrypt;
    }

    public void setEncrypt(boolean encrypt) {
        this.encrypt = encrypt;
    }

    public CookieProperties getCookie() {
        return this.cookie;
    }

    public void setCookie(CookieProperties cookie) {
        this.cookie = cookie;
    }
}

