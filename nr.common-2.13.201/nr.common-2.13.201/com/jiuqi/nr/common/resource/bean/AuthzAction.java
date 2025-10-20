/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource.bean;

import com.jiuqi.np.authz2.privilege.Authority;

public class AuthzAction {
    private String privageId;
    private Authority authority;

    public String getPrivageId() {
        return this.privageId;
    }

    public void setPrivageId(String privageId) {
        this.privageId = privageId;
    }

    public Authority getAuthority() {
        return this.authority;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }
}

