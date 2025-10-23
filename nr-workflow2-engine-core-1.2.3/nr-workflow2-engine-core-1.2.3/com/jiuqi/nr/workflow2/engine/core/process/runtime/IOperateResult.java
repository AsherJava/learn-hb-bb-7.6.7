/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.engine.core.process.runtime;

import com.jiuqi.nr.workflow2.engine.core.exception.Error;
import com.jiuqi.nr.workflow2.engine.core.exception.ErrorCode;

public interface IOperateResult<T> {
    public boolean isSuccessful();

    public T getResult();

    public Error getError();

    default public ErrorCode getErrorCode() {
        if (!this.isSuccessful()) {
            return this.getError().getErrorCode();
        }
        throw new RuntimeException("\u65b9\u6cd5\u4f7f\u7528\u9519\u8bef\uff1a\u4e0d\u5141\u8bb8\u4ece\u64cd\u4f5c\u6210\u529f\u7ed3\u679c\u4e0a\u83b7\u53d6\u9519\u8bef\u7801\u3002");
    }
}

