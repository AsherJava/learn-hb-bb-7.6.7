/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.exception;

import com.jiuqi.nr.definition.exception.DesignCheckException;

public class DesignTitleCheckException
extends DesignCheckException {
    private static final String MESSAGE_TEMPLATE1 = "[%s]\u540d\u79f0\u65e0\u6548\u6216\u8005\u4e0d\u552f\u4e00";

    public DesignTitleCheckException(String title) {
        super(String.format(MESSAGE_TEMPLATE1, title));
    }

    public DesignTitleCheckException(String title, Throwable cause) {
        super(String.format(MESSAGE_TEMPLATE1, title), cause);
    }
}

