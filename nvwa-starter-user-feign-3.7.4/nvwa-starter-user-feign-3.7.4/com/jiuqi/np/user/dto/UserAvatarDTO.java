/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.user.UserAvatar
 */
package com.jiuqi.np.user.dto;

import com.jiuqi.np.user.UserAvatar;
import java.io.Serializable;
import java.time.Instant;

public class UserAvatarDTO
implements UserAvatar,
Serializable {
    private static final long serialVersionUID = -8646185565422436920L;
    private String userId;
    private byte[] avatar;
    private Instant modifyTime;
    private Instant createTime;

    public String getUserId() {
        return this.userId;
    }

    public byte[] getAvatar() {
        return this.avatar;
    }

    public Instant getModifyTime() {
        return this.modifyTime;
    }

    public Instant getCreateTime() {
        return this.createTime;
    }

    public void setModifyTime(Instant modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setCreateTime(Instant createTime) {
        this.createTime = createTime;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }
}

