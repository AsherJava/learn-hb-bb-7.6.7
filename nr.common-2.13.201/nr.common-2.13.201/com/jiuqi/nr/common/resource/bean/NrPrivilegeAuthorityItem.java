/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.authz2.privilege.Authority
 */
package com.jiuqi.nr.common.resource.bean;

import com.jiuqi.np.authz2.privilege.Authority;
import com.jiuqi.nr.common.resource.NrPrivilegeAuthority;

public class NrPrivilegeAuthorityItem
implements NrPrivilegeAuthority {
    private String privilegeId;
    private String privilegeName;
    private String privilegeTitle;
    private Authority authority;
    private Boolean inherit;
    private Boolean readOnly;

    @Override
    public String getPrivilegeId() {
        return this.privilegeId;
    }

    @Override
    public String getPrivilegeTitle() {
        return this.privilegeTitle;
    }

    @Override
    public Authority getAuthority() {
        return this.authority;
    }

    @Override
    public Boolean isInherit() {
        return this.inherit;
    }

    @Override
    public Boolean isReadOnly() {
        return this.readOnly;
    }

    @Override
    public String getPrivilegeName() {
        return this.privilegeName;
    }

    public void setPrivilegeName(String privilegeName) {
        this.privilegeName = privilegeName;
    }

    public NrPrivilegeAuthorityItem() {
    }

    public NrPrivilegeAuthorityItem(String privilegeId, String privilegeName, String privilegeTitle, Authority authority, Boolean inherit, Boolean readOnly) {
        this.privilegeId = privilegeId;
        this.privilegeName = privilegeName;
        this.privilegeTitle = privilegeTitle;
        this.authority = authority;
        this.inherit = inherit;
        this.readOnly = readOnly;
    }

    public void setPrivilegeId(String privilegeId) {
        this.privilegeId = privilegeId;
    }

    public void setAuthority(Authority authority) {
        this.authority = authority;
    }

    public void setInherit(Boolean inherit) {
        this.inherit = inherit;
    }

    public void setReadOnly(Boolean readOnly) {
        this.readOnly = readOnly;
    }

    public void setPrivilegeTitle(String privilegeTitle) {
        this.privilegeTitle = privilegeTitle;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || this.getClass() != o.getClass()) {
            return false;
        }
        NrPrivilegeAuthorityItem that = (NrPrivilegeAuthorityItem)o;
        return this.privilegeId.equals(that.privilegeId);
    }

    public int hashCode() {
        return this.privilegeId.hashCode();
    }
}

