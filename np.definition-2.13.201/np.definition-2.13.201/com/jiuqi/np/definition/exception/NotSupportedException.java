/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.exception;

public class NotSupportedException
extends RuntimeException {
    private static final long serialVersionUID = 5679668608999327061L;

    public NotSupportedException() {
        super("\u65b9\u6cd5\u6ca1\u6709\u6709\u6548\u5b9e\u73b0\u3001\u6216\u8005\u5df2\u7ecf\u5f03\u7528");
    }

    public NotSupportedException(String string) {
        super(string);
    }
}

