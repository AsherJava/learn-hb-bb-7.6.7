/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.constant.AsynctaskPoolType
 */
package com.jiuqi.nr.common.asynctask.entity;

import com.jiuqi.nr.common.asynctask.entity.AysncTaskArgsInfo;
import com.jiuqi.nr.common.constant.AsynctaskPoolType;

public class AsyncTaskPublishInfo {
    private AsynctaskPoolType type;
    private AysncTaskArgsInfo args;

    public AsynctaskPoolType getType() {
        return this.type;
    }

    public void setType(AsynctaskPoolType type) {
        this.type = type;
    }

    public AysncTaskArgsInfo getArgs() {
        return this.args;
    }

    public void setArgs(AysncTaskArgsInfo args) {
        this.args = args;
    }
}

