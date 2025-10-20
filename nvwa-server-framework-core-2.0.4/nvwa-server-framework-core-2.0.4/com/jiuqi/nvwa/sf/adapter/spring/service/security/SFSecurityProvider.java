/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf.adapter.spring.service.security;

import java.util.Map;

public interface SFSecurityProvider {
    default public void authentication(String moduleName) throws SecurityException {
        this.authentication(moduleName, "sf-security");
    }

    public void authentication(String var1, String var2) throws SecurityException;

    default public Map<String, String> getSecurityHeaders() {
        return this.getSecurityHeaders("sf-common", "sf-security");
    }

    default public Map<String, String> getSecurityHeaders(String moduleName) {
        return this.getSecurityHeaders(moduleName, "sf-security");
    }

    default public String getSecurityHeadersValue() {
        return this.getSecurityHeaders("sf-common", "sf-security").get("sf-security");
    }

    default public String getSecurityHeaderValue(String moduleName) {
        return this.getSecurityHeaders(moduleName, "sf-security").get("sf-security");
    }

    default public String getSecurityHeaderValue(String moduleName, String headerName) {
        return this.getSecurityHeaders(moduleName, headerName).get(headerName);
    }

    public Map<String, String> getSecurityHeaders(String var1, String var2);
}

