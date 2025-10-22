/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.configuration.entity;

import java.time.Instant;

public class ProfileEntity {
    private String userKey;
    private String code;
    private Instant updateTime;
    private String profileContent;

    public String getUserKey() {
        return this.userKey;
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Instant getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(Instant updateTime) {
        this.updateTime = updateTime;
    }

    public String getProfileContent() {
        return this.profileContent;
    }

    public void setProfileContent(String profileContent) {
        this.profileContent = profileContent;
    }
}

