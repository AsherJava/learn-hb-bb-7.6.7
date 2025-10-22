/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.dataservice.core.access;

public enum AuthType {
    VISIBLE(1),
    READABLE(2),
    WRITEABLE(3),
    SYS_WRITEABLE(4);

    final int code;

    private AuthType(int code) {
        this.code = code;
    }
}

