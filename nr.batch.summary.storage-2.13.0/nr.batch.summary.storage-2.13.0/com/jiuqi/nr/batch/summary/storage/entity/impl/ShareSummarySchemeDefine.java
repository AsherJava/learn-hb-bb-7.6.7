/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryScheme;
import java.util.Date;

public class ShareSummarySchemeDefine
implements ShareSummaryScheme {
    private String code;
    private String title;
    private String toUser;
    private String fromUser;
    private String task;
    private Date shareTime;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getTask() {
        return this.task;
    }

    @Override
    public Date getShareTime() {
        return this.shareTime;
    }

    @Override
    public String getToUser() {
        return this.toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    @Override
    public String getFromUser() {
        return this.fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public void setShareTime(Date shareTime) {
        this.shareTime = shareTime;
    }
}

