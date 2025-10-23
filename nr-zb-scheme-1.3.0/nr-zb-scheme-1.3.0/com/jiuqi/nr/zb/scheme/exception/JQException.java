/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.common.exception.AbstractJQException
 *  com.jiuqi.np.common.exception.ErrorEnum
 */
package com.jiuqi.nr.zb.scheme.exception;

import com.jiuqi.np.common.exception.AbstractJQException;
import com.jiuqi.np.common.exception.ErrorEnum;

public class JQException
extends AbstractJQException {
    private ErrorEnum error;
    private Object obj;

    public ErrorEnum getError() {
        return this.error;
    }

    public Object getObj() {
        return this.obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public JQException(ErrorEnum error) {
        this.error = error;
    }

    public JQException(ErrorEnum error, String message) {
        super(message);
        this.error = error;
    }

    public JQException(ErrorEnum error, Object obj) {
        this.error = error;
        this.obj = obj;
    }

    public JQException(ErrorEnum error, Throwable cause) {
        super(cause);
        this.error = error;
    }

    public JQException(ErrorEnum error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public String getMessage() {
        String message = super.getMessage();
        return this.error.getMessage() + (message != null ? "\uff1a" + message : "");
    }

    public String getErrorCode() {
        return this.error.getCode();
    }

    public String getErrorMessage() {
        return this.getMessage();
    }

    public Object getErrorData() {
        return this.getObj();
    }
}

