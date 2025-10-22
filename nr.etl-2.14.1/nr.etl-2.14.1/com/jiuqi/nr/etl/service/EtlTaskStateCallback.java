/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.etl.service;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface EtlTaskStateCallback {
    @Nullable
    public void processTaskState(int var1);
}

