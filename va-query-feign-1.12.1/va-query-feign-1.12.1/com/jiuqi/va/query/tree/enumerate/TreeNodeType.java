/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.query.tree.enumerate;

public enum TreeNodeType {
    QUERY("query", "\u67e5\u8be2\u6a21\u677f"),
    GROUP("group", "\u5206\u7ec4");

    private String code;
    private String title;

    private TreeNodeType(String code, String title) {
        this.code = code;
        this.title = title;
    }

    public String toString() {
        return this.code;
    }
}

