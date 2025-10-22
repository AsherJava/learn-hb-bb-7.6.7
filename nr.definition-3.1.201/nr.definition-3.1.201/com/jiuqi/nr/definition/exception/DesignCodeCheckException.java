/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.exception;

import com.jiuqi.nr.definition.exception.DesignCheckException;

public class DesignCodeCheckException
extends DesignCheckException {
    private static final String MESSAGE_TEMPLATE1 = "[%s]\u6807\u8bc6\u4e0d\u552f\u4e00";
    private static final String MESSAGE_TEMPLATE2 = "%s\u7684\u6807\u8bc6\u4e0d\u552f\u4e00(%s)";
    private static final String MESSAGE_TEMPLATE3 = "%s()\u64cd\u4f5c\u5931\u8d25, [%s]\u6807\u8bc6\u4e0d\u552f\u4e00";

    public DesignCodeCheckException(String code) {
        super(String.format(MESSAGE_TEMPLATE1, code));
    }

    public DesignCodeCheckException(String code, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE1, code), cause);
    }
}

