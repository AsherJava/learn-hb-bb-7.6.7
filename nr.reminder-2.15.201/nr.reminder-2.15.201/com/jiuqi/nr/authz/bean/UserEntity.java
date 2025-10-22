/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.authz.bean;

import com.jiuqi.nr.authz.IUserEntity;

public class UserEntity
implements IUserEntity {
    private String id;
    private String name;
    private String nickName;
    private String orgCode;

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

    public String getNickName() {
        return this.nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String getNickname() {
        return this.nickName;
    }

    public void setNickname(String nickname) {
        this.nickName = nickname;
    }

    @Override
    public String getOrgCode() {
        return this.orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public UserEntity() {
    }

    public UserEntity(String id, String name, String nickName) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
    }

    public UserEntity(String id, String name, String nickName, String orgCode) {
        this.id = id;
        this.name = name;
        this.nickName = nickName;
        this.orgCode = orgCode;
    }
}

