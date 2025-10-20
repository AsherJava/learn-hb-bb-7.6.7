/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.domain.common;

import com.jiuqi.va.domain.common.SpringTransactionUtil;

@FunctionalInterface
public interface SpringTransactionRunnable {
    public void run(SpringTransactionUtil.Status var1);
}

