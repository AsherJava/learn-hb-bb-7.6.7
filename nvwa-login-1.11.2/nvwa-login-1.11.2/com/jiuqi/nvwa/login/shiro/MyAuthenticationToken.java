/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.shiro.authc.AuthenticationToken
 */
package com.jiuqi.nvwa.login.shiro;

import com.jiuqi.nvwa.login.domain.NvwaContext;
import org.apache.shiro.authc.AuthenticationToken;

public class MyAuthenticationToken
implements AuthenticationToken {
    private static final long serialVersionUID = 1L;
    private NvwaContext context;
    private String pwd;

    public MyAuthenticationToken(NvwaContext context, String pwd) {
        this.context = context;
        this.pwd = pwd;
    }

    public NvwaContext getContext() {
        return this.context;
    }

    public Object getPrincipal() {
        return this.context.getTenantName() + "#" + this.context.getUsername();
    }

    public Object getCredentials() {
        return this.pwd;
    }
}

