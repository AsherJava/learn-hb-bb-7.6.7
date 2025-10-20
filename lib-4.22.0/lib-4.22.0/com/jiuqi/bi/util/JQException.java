/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util;

public class JQException
extends Exception {
    private static final long serialVersionUID = 857422883670435260L;
    private int errorCode;
    public static final int UNDEFINE = -1;

    public JQException() {
        this.errorCode = -1;
    }

    public JQException(int errorCode) {
        this.errorCode = errorCode;
    }

    public JQException(String message) {
        super(message);
        this.errorCode = -1;
    }

    public JQException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public JQException(Throwable cause) {
        super(cause.getMessage(), cause);
        this.errorCode = cause instanceof JQException ? ((JQException)cause).errorCode : -1;
    }

    public JQException(JQException cause) {
        super(cause.getMessage(), cause);
        this.errorCode = cause.errorCode;
    }

    public JQException(Throwable cause, int errorCode) {
        super(cause.getMessage(), cause);
        this.errorCode = errorCode;
    }

    public JQException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = cause instanceof JQException ? ((JQException)cause).errorCode : -1;
    }

    public JQException(String message, JQException cause) {
        super(message, cause);
        this.errorCode = cause.errorCode;
    }

    public JQException(String message, Throwable cause, int errorCode) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    @Override
    public String toString() {
        return super.toString() + " (" + Integer.toString(this.errorCode) + ")";
    }

    public int getErrorCode() {
        return this.errorCode;
    }
}

