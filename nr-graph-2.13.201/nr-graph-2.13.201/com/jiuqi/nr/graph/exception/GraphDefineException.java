/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.exception;

public class GraphDefineException
extends RuntimeException {
    private static final long serialVersionUID = -3649465985639942750L;

    public GraphDefineException() {
        super("\u5b9a\u4e49\u5f02\u5e38");
    }

    public GraphDefineException(String message) {
        super(message);
    }

    public GraphDefineException(Throwable e) {
        super(e.getMessage(), e);
    }

    public GraphDefineException(String message, Throwable e) {
        super(message, e);
    }
}

