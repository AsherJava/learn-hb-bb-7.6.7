/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.todo.enumeration;

public enum CommentType {
    ONLY_RETURN("\u4ec5\u9000\u56de\u8bf4\u660e"),
    ONLY_RETURN_REVIEW("\u4ec5\u9000\u5ba1\u8bf4\u660e"),
    BOTH("\u9000\u5ba1\u548c\u9000\u56de\u8bf4\u660e\u90fd\u5305\u542b"),
    NEITHER("\u9000\u5ba1\u548c\u9000\u56de\u8bf4\u660e\u90fd\u4e0d\u5305\u542b");

    public final String title;

    private CommentType(String title) {
        this.title = title;
    }
}

