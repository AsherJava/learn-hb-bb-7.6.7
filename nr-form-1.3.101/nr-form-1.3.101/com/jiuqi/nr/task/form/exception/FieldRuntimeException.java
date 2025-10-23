/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.exception;

import com.jiuqi.nr.task.form.exception.FormRuntimeException;

public class FieldRuntimeException
extends FormRuntimeException {
    public FieldRuntimeException() {
    }

    public FieldRuntimeException(String message) {
        super(message);
    }

    public FieldRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FieldRuntimeException(Throwable cause) {
        super(cause);
    }
}

