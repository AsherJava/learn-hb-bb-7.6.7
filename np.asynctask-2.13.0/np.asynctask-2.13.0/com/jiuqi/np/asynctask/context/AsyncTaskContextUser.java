/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnoreProperties
 *  com.jiuqi.np.core.context.ContextUser
 */
package com.jiuqi.np.asynctask.context;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jiuqi.np.core.context.ContextUser;

@JsonIgnoreProperties(ignoreUnknown=true)
public class AsyncTaskContextUser
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
}

