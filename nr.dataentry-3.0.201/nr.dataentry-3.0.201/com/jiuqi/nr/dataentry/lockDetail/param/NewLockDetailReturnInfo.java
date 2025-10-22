/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.lockDetail.param;

import com.jiuqi.nr.dataentry.lockDetail.param.LockDetailReturnInfo;
import java.util.List;

public class NewLockDetailReturnInfo {
    private List<LockDetailReturnInfo> lockDetailReturnInfos;
    private Boolean locked;
    private String lockUsers;

    public Boolean getLocked() {
        return this.locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public String getLockUsers() {
        return this.lockUsers;
    }

    public void setLockUsers(String lockUsers) {
        this.lockUsers = lockUsers;
    }

    public List<LockDetailReturnInfo> getLockDetailReturnInfos() {
        return this.lockDetailReturnInfos;
    }

    public void setLockDetailReturnInfos(List<LockDetailReturnInfo> lockDetailReturnInfos) {
        this.lockDetailReturnInfos = lockDetailReturnInfos;
    }
}

