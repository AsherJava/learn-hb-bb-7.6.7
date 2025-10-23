/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;

public interface IBatchOperateResult<K, T> {
    public int size();

    public Iterable<K> getInstanceKeys();

    public IOperateResult<T> getResult(K var1);
}

