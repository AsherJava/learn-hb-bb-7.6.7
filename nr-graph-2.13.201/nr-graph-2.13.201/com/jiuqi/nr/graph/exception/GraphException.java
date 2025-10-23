/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.exception;

public class GraphException
extends RuntimeException {
    private static final long serialVersionUID = -1959092021003552591L;

    public GraphException() {
        super("\u67e5\u8be2\u5f02\u5e38");
    }

    public GraphException(String message) {
        super(message);
    }

    public GraphException(Throwable e) {
        super(e.getMessage(), e);
    }

    public GraphException(String message, Throwable e) {
        super(message, e);
    }
}

