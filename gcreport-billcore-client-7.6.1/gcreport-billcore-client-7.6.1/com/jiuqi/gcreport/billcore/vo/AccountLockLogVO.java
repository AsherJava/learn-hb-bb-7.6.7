/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.gcreport.billcore.vo;

import java.util.Date;

public class AccountLockLogVO {
    private String accountName;
    private String modifiedUser;
    private Date modifiedTime;
    private String operating;

    public String getAccountName() {
        return this.accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getModifiedUser() {
        return this.modifiedUser;
    }

    public void setModifiedUser(String modifiedUser) {
        this.modifiedUser = modifiedUser;
    }

    public Date getModifiedTime() {
        return this.modifiedTime;
    }

    public void setModifiedTime(Date modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public String getOperating() {
        return this.operating;
    }

    public void setOperating(String operating) {
        this.operating = operating;
    }
}

