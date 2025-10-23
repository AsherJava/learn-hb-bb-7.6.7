/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.api.param;

import java.util.Date;

public class FormLockHistory {
    String id;
    String userID;
    String formKey;
    int oper;
    Date operTime;

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserID() {
        return this.userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getFormKey() {
        return this.formKey;
    }

    public void setFormKey(String formKey) {
        this.formKey = formKey;
    }

    public int getOper() {
        return this.oper;
    }

    public void setOper(int oper) {
        this.oper = oper;
    }

    public Date getOperTime() {
        return this.operTime;
    }

    public void setOperTime(Date operTime) {
        this.operTime = operTime;
    }
}

