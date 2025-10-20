/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.core.context.impl;

import com.jiuqi.np.core.context.ContextUser;

public class NpContextUser
implements ContextUser {
    private static final long serialVersionUID = -5844841802246045652L;
    private String id;
    private String name;
    private String nickname;
    private String description;
    private String orgCode;
    private String securitylevel;
    private int type;
    private boolean avatar;

    @Override
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getFullname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    @Override
    public String getSecuritylevel() {
        return this.securitylevel;
    }

    @Override
    public int getType() {
        return this.type;
    }

    public void setSecuritylevel(String securitylevel) {
        this.securitylevel = securitylevel;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getNickname() {
        return this.nickname;
    }

    @Override
    public boolean isAvatar() {
        return this.avatar;
    }

    public void setAvatar(boolean avatar) {
        this.avatar = avatar;
    }
}

