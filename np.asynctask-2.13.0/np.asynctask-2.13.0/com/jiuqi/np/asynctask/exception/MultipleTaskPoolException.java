/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.exception;

import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;

public class MultipleTaskPoolException
extends NpAsyncTaskExecption {
    public MultipleTaskPoolException(String message) {
        super(message);
    }

    public MultipleTaskPoolException(String message, Throwable cause) {
        super(message, cause);
    }
}

