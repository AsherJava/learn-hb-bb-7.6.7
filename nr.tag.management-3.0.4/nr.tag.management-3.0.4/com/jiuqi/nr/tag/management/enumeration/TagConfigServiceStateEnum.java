/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.tag.management.enumeration;

public enum TagConfigServiceStateEnum {
    SUCCESS("success", "\u64cd\u4f5c\u6210\u529f"),
    FAIL("fail", "\u64cd\u4f5c\u5931\u8d25");

    public final String code;
    public final String title;

    private TagConfigServiceStateEnum(String code, String title) {
        this.code = code;
        this.title = title;
    }
}

