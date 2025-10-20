/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.common.exception;

import com.jiuqi.np.common.exception.AbstractJQException;
import com.jiuqi.np.common.exception.ErrorEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JQException
extends AbstractJQException {
    public final Logger LOGGER = LoggerFactory.getLogger(this.getClass());
    private static final long serialVersionUID = 307509585129171191L;
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

    @Override
    public String getMessage() {
        String message = super.getMessage();
        return this.error.getMessage() + (message != null ? ":" + message : "");
    }

    @Override
    public String getErrorCode() {
        return this.error.getCode();
    }

    @Override
    public String getErrorMessage() {
        return this.getMessage();
    }

    @Override
    public Object getErrorData() {
        return this.getObj();
    }
}

