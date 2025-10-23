/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.transmission.data.intf;

public class UserInfoParam {
    private String syncUserId;
    private String syncUserName;

    public UserInfoParam() {
    }

    public UserInfoParam(String syncUserId, String syncUserName) {
        this.syncUserId = syncUserId;
        this.syncUserName = syncUserName;
    }

    public String getSyncUserId() {
        return this.syncUserId;
    }

    public void setSyncUserId(String syncUserId) {
        this.syncUserId = syncUserId;
    }

    public String getSyncUserName() {
        return this.syncUserName;
    }

    public void setSyncUserName(String syncUserName) {
        this.syncUserName = syncUserName;
    }
}

