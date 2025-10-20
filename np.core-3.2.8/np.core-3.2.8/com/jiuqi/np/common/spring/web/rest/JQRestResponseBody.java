/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.common.spring.web.rest;

import com.jiuqi.np.common.exception.AbstractJQException;
import com.jiuqi.np.common.spring.web.rest.JQRestResponseError;
import java.io.Serializable;

class JQRestResponseBody
implements Serializable {
    private static final long serialVersionUID = 8300267146885277210L;
    private boolean success;
    private Object data;
    private JQRestResponseError error;

    public static JQRestResponseBody success(Object data) {
        return new JQRestResponseBody(true, data, null);
    }

    public static JQRestResponseBody error(JQRestResponseError error) {
        return new JQRestResponseBody(false, null, error);
    }

    public static JQRestResponseBody error(AbstractJQException error) {
        return new JQRestResponseBody(false, null, new JQRestResponseError(error));
    }

    public static JQRestResponseBody error(Exception error) {
        return new JQRestResponseBody(false, null, new JQRestResponseError(error));
    }

    private JQRestResponseBody(boolean success, Object data, JQRestResponseError error) {
        this.success = success;
        this.data = data;
        this.error = error;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getData() {
        return this.data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public JQRestResponseError getError() {
        return this.error;
    }

    public void setError(JQRestResponseError error) {
        this.error = error;
    }
}

