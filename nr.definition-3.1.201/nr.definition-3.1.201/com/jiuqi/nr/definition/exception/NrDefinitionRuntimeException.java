/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.definition.exception;

import com.jiuqi.np.common.exception.ErrorEnum;

public class NrDefinitionRuntimeException
extends RuntimeException {
    private static final long serialVersionUID = 5258486732208838802L;
    private ErrorEnum error;

    public ErrorEnum getError() {
        return this.error;
    }

    public NrDefinitionRuntimeException(ErrorEnum error) {
        this.error = error;
    }

    public NrDefinitionRuntimeException(ErrorEnum error, String message) {
        super(message);
        this.error = error;
    }

    public NrDefinitionRuntimeException(ErrorEnum error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    public NrDefinitionRuntimeException(ErrorEnum error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return this.error.getMessage() + (message != null ? ":" + message : "");
    }

    public String getErrorCode() {
        return this.error.getCode();
    }

    public String getErrorMessage() {
        return this.getMessage();
    }
}

