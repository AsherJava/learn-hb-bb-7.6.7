/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.access.common;

public enum AccessLevel {
    UNIT,
    FORM;


    public static enum FormAccessLevel {
        FORM_READ,
        FORM_DATA_READ,
        FORM_DATA_WRITE,
        FORM_DATA_SYSTEM_WRITE;

    }
}

