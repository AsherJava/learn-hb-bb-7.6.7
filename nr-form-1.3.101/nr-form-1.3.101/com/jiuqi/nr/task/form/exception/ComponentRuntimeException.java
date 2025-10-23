/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.form.exception;

import com.jiuqi.nr.task.form.exception.FormRuntimeException;

public class ComponentRuntimeException
extends FormRuntimeException {
    public ComponentRuntimeException() {
    }

    public ComponentRuntimeException(String message) {
        super(message);
    }

    public ComponentRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public ComponentRuntimeException(Throwable cause) {
        super(cause);
    }
}

