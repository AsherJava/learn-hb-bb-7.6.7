/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource;

import com.jiuqi.np.authz2.privilege.Authority;

public interface NrPrivilegeAuthority {
    public String getPrivilegeId();

    public String getPrivilegeName();

    public String getPrivilegeTitle();

    public Authority getAuthority();

    public Boolean isInherit();

    public Boolean isReadOnly();
}

