/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.observer;

public enum MessageType {
    PUBLISHTABLE("\u5b58\u50a8\u8868\u53d1\u5e03"),
    NRPUBLISHTASK("\u53d1\u5e03\u62a5\u8868\u4efb\u52a1"),
    NRDROPTASK("\u53d1\u5e03\u62a5\u8868\u4efb\u52a1"),
    NRDROPSCHEME("\u53d1\u5e03\u62a5\u8868\u4efb\u52a1"),
    UNKNOWN("\u672a\u5b9a\u4e49\u7c7b\u578b");

    private String title;

    private MessageType(String title) {
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }
}

