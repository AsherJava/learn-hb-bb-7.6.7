/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

public class UserStateDTO {
    private String id;
    private String userName;
    private Boolean state;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getState() {
        return this.state;
    }

    public void setState(Boolean state) {
        this.state = state;
    }

    public boolean isState() {
        if (this.state == null) {
            return false;
        }
        return this.state;
    }

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}

