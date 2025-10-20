/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.user.dto;

import java.io.Serializable;
import java.time.Instant;

public class UserLockPolicyDTO
implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private Instant unlockTime;

    public UserLockPolicyDTO() {
    }

    public UserLockPolicyDTO(String name, Instant unlockTime) {
        this.name = name;
        this.unlockTime = unlockTime;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public Instant getUnlockTime() {
        return this.unlockTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnlockTime(Instant unlockTime) {
        this.unlockTime = unlockTime;
    }
}

