/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.message.constants;

@Deprecated
public enum HandleModeEnum {
    SYSTEM(1, "\u7cfb\u7edf"),
    MAIL(2, "\u90ae\u4ef6"),
    SHORT_MESSAGE(3, "\u77ed\u4fe1"),
    COMPLEX_MAIL(4, "\u590d\u6742\u90ae\u4ef6");

    private final Integer code;
    private final String title;

    private HandleModeEnum(Integer code, String title) {
        this.code = code;
        this.title = title;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

