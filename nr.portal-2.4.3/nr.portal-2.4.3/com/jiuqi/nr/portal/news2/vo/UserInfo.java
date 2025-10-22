/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.portal.news2.vo;

public class UserInfo {
    private String id;
    private String name;
    private Boolean isRole = false;
    private String nickname;

    public UserInfo(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserInfo(String id, String name, Boolean isRole) {
        this.id = id;
        this.name = name;
        this.isRole = isRole;
    }

    public UserInfo(String id, String name, Boolean isRole, String nickname) {
        this.id = id;
        this.name = name;
        this.isRole = isRole;
        this.nickname = nickname;
    }

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

    public Boolean getIsRole() {
        return this.isRole;
    }

    public void setIsRole(Boolean isRole) {
        this.isRole = isRole;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

