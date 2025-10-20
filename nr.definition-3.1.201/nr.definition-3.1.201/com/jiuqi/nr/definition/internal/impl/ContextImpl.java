/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.impl;

import com.jiuqi.nr.definition.facade.Context;

public class ContextImpl
implements Context {
    private String taskKey;

    @Override
    public String getTaskKey() {
        return this.taskKey;
    }

    @Override
    public void setTaskKey(String taskKey) {
        this.taskKey = taskKey;
    }
}

