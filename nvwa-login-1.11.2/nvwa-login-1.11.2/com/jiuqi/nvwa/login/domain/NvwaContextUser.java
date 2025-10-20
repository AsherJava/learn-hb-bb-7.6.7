/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextUser
 */
package com.jiuqi.nvwa.login.domain;

import com.jiuqi.np.core.context.ContextUser;
import java.util.Objects;

public class NvwaContextUser
implements ContextUser {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String fullname;
    private String description;
    private String orgCode;
    private String securitylevel;
    private int type;
    private boolean avatar;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullname() {
        return this.fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSecuritylevel() {
        return this.securitylevel;
    }

    public int getType() {
        return this.type;
    }

    public void setSecuritylevel(String securitylevel) {
        this.securitylevel = securitylevel;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isAvatar() {
        return this.avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }

    public int hashCode() {
        return Objects.hash(this.avatar, this.description, this.fullname, this.id, this.name, this.orgCode, this.securitylevel, this.type);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        NvwaContextUser other = (NvwaContextUser)obj;
        return this.avatar == other.avatar && Objects.equals(this.description, other.description) && Objects.equals(this.fullname, other.fullname) && Objects.equals(this.id, other.id) && Objects.equals(this.name, other.name) && Objects.equals(this.orgCode, other.orgCode) && Objects.equals(this.securitylevel, other.securitylevel) && this.type == other.type;
    }
}

