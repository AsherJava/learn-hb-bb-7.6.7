/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.mapper.domain.TenantDO
 */
package com.jiuqi.va.domain.user;

import com.jiuqi.va.mapper.domain.TenantDO;
import java.util.List;

public class UserOrgDTO
extends TenantDO {
    private static final long serialVersionUID = 1L;
    private String username;
    private List<String> orgCodes;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getOrgCodes() {
        return this.orgCodes;
    }

    public void setOrgCodes(List<String> orgCodes) {
        this.orgCodes = orgCodes;
    }
}

