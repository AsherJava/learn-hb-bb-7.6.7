/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBatchOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import java.util.Collections;

public interface IInstanceIdOperateResult<T>
extends IBatchOperateResult<String, T> {
    default public Iterable<String> getInstanceIds() {
        return this.getInstanceKeys();
    }

    public static <T> IInstanceIdOperateResult<T> emptyResult() {
        return new EmptyInstanceIdOperateResult();
    }

    public static class EmptyInstanceIdOperateResult<T>
    implements IInstanceIdOperateResult<T> {
        private EmptyInstanceIdOperateResult() {
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Iterable<String> getInstanceKeys() {
            return Collections.emptyList();
        }

        @Override
        public IOperateResult<T> getResult(String key) {
            return null;
        }
    }
}

