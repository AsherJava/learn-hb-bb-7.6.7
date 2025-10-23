/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBatchOperateResult;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IBusinessObject;
import com.jiuqi.nr.workflow2.engine.core.process.runtime.IOperateResult;
import java.util.Collections;

public interface IBizObjectOperateResult<T>
extends IBatchOperateResult<IBusinessObject, T> {
    default public Iterable<IBusinessObject> getBusinessObjects() {
        return this.getInstanceKeys();
    }

    public static <T> IBizObjectOperateResult<T> emptyResult() {
        return new EmptyBizObjectOperateResult();
    }

    public static class EmptyBizObjectOperateResult<T>
    implements IBizObjectOperateResult<T> {
        private EmptyBizObjectOperateResult() {
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Iterable<IBusinessObject> getInstanceKeys() {
            return Collections.emptyList();
        }

        @Override
        public IOperateResult<T> getResult(IBusinessObject key) {
            return null;
        }
    }
}

