/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.batch.summary.storage.entity.impl;

import com.jiuqi.nr.batch.summary.storage.entity.ShareSummaryGroup;

public class ShareSummaryGroupDefine
implements ShareSummaryGroup {
    private String code;
    private String title;
    private String task;

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getTitle() {
        return this.title;
    }

    @Override
    public String getParent() {
        return "00000000-0000-0000-0000-000000000000";
    }

    @Override
    public String getTask() {
        return this.task;
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
}

