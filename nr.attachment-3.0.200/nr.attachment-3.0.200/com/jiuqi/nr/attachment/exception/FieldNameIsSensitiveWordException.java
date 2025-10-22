/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.exception;

import com.jiuqi.nr.attachment.exception.FileException;

public class FieldNameIsSensitiveWordException
extends FileException {
    public FieldNameIsSensitiveWordException(String message) {
        super(message);
    }

    public FieldNameIsSensitiveWordException(String message, Throwable cause) {
        super(message, cause);
    }
}

