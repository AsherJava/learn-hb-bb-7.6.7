/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 */
package com.jiuqi.va.shiro.config.extend;

import javax.servlet.http.HttpServletRequest;

public interface MyShiroAuthenticationAdaptor {
    public String getTokenId(HttpServletRequest var1);
}

