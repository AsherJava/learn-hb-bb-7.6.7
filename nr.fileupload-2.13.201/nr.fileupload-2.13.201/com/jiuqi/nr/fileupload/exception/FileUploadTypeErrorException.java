/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.fileupload.exception;

import com.jiuqi.nr.fileupload.exception.FileUploadException;

public class FileUploadTypeErrorException
extends FileUploadException {
    public FileUploadTypeErrorException(String errorCode) {
        super(errorCode);
    }

    public FileUploadTypeErrorException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
}

