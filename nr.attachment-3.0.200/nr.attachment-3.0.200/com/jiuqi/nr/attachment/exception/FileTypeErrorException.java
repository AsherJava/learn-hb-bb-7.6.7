/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.attachment.exception;

import com.jiuqi.nr.attachment.exception.FileException;

public class FileTypeErrorException
extends FileException {
    public FileTypeErrorException(String message) {
        super(message);
    }

    public FileTypeErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}

