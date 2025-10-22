/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataentry.lockDetail.param;

public class LockDetailReturnInfo {
    private String lockDetailId;
    private String lockFormKeyTitle;
    private String lockUserName;
    private String lockDateTime;
    private int lockType;

    public String getLockDetailId() {
        return this.lockDetailId;
    }

    public void setLockDetailId(String lockDetailId) {
        this.lockDetailId = lockDetailId;
    }

    public String getLockFormKeyTitle() {
        return this.lockFormKeyTitle;
    }

    public void setLockFormKeyTitle(String lockFormKeyTitle) {
        this.lockFormKeyTitle = lockFormKeyTitle;
    }

    public String getLockUserName() {
        return this.lockUserName;
    }

    public void setLockUserName(String lockUserName) {
        this.lockUserName = lockUserName;
    }

    public String getLockDateTime() {
        return this.lockDateTime;
    }

    public void setLockDateTime(String lockDateTime) {
        this.lockDateTime = lockDateTime;
    }

    public int getLockType() {
        return this.lockType;
    }

    public void setLockType(int lockType) {
        this.lockType = lockType;
    }
}

