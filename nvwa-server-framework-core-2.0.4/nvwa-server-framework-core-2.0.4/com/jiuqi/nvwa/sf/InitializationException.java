/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nvwa.sf;

public class InitializationException
extends Exception {
    private static final long serialVersionUID = -321926525177248117L;
    public static final int VERSION_NOT_MACTH = 1;
    public static final int CANNOT_CONNECTION_DATASOURCE = 2;
    public static final int CIRCLE_REFERENCE = 3;
    public static final int MODULE_LOAD_ERROR = 4;
    private int code;

    public InitializationException(int code) {
        this.code = code;
    }

    public InitializationException(String msg, int code) {
        super(msg);
        this.code = code;
    }

    public InitializationException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public InitializationException(String msg, Throwable cause, int code) {
        super(msg, cause);
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}

