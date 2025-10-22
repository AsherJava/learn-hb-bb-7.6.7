/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.batchupload.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public enum UploadException implements ErrorEnum
{
    ERROR;

    String code;
    String message;

    private UploadException() {
    }

    private UploadException(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}

