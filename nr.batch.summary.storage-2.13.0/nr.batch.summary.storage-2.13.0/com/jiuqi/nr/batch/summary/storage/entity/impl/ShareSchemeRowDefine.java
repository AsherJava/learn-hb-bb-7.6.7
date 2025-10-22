/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.jiuqi.nr.batch.summary.storage.entity.ShareSchemeRow;
import java.util.Date;

public class ShareSchemeRowDefine
implements ShareSchemeRow {
    private String scheme;
    private String fromUser;
    private String toUser;
    private Date shareTime;
    private String task;

    @Override
    public String getScheme() {
        return this.scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    @Override
    public String getFromUser() {
        return this.fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    @Override
    public String getToUser() {
        return this.toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public Date getShareTime() {
        return this.shareTime;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }

    @Override
    public String getTask() {
        return this.task;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

