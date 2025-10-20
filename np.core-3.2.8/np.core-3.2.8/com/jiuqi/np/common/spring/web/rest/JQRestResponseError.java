/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.common.spring.web.rest;

import com.jiuqi.np.common.exception.AbstractJQException;
import java.io.Serializable;

class JQRestResponseError
implements Serializable {
    private static final long serialVersionUID = 3073217954315718884L;
    private String code;
    private String message;
    private Object data;

    public JQRestResponseError(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public JQRestResponseError(AbstractJQException jqException) {
        this(jqException.getErrorCode(), jqException.getErrorMessage(), jqException.getErrorData());
    }

    public JQRestResponseError(Exception exception) {
        this("unknow server error", exception.getMessage(), null);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

