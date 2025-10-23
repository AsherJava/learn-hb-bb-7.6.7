/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.events.executor.msg.enumeration;

public enum MessageType {
    ALL("all", "\u5168\u90e8"),
    MESSAGE("message", "\u6d88\u606f"),
    EMAIL("email", "\u90ae\u4ef6"),
    SHORT_MESSAGE("shortMessage", "\u77ed\u4fe1");

    public final String code;
    public final String title;

    private MessageType(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

