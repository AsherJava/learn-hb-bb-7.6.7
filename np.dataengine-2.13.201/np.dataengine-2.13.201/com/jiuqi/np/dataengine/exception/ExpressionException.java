/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.exception;

import java.io.Serializable;

public class ExpressionException
extends Exception
implements Serializable {
    private static final long serialVersionUID = -7680864175989775593L;
    private transient Object errorToken;

    public ExpressionException(String arg0) {
        super(arg0);
    }

    public ExpressionException(Exception arg0) {
        super(null, arg0);
    }

    public ExpressionException(String arg0, Exception arg1) {
        super(arg0, arg1);
    }

    public ExpressionException(Object token, String message) {
        super(message);
        this.setErrorToken(token);
    }

    public final Object getErrorToken() {
        return this.errorToken;
    }

    private void setErrorToken(Object value) {
        this.errorToken = value;
    }
}

