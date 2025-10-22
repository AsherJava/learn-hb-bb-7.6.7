/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.asynctask.exception;

import com.jiuqi.np.asynctask.exception.NpAsyncTaskExecption;

public class OutOfQueueException
extends NpAsyncTaskExecption {
    public OutOfQueueException(String message) {
        super(message);
    }

    public OutOfQueueException(String message, Throwable cause) {
        super(message, cause);
    }
}

