/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.entity.engine.exception;

import java.io.Serializable;

public final class IllegalPageConditionException
extends RuntimeException
implements Serializable {
    private static final long serialVersionUID = -4760335476375889452L;
    public static final String ILLEGAL_PAGE_CONDITION_ERROR = "\u9519\u8bef\u7684\u5206\u9875\u6570\u636e";

    public IllegalPageConditionException() {
        super(ILLEGAL_PAGE_CONDITION_ERROR);
    }

    public IllegalPageConditionException(String arg0) {
        super(arg0);
    }

    public IllegalPageConditionException(Exception ex) {
        super(ex);
    }
}

