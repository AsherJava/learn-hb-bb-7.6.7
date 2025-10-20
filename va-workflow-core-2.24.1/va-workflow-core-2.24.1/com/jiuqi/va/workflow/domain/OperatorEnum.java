/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.workflow.domain;

public enum OperatorEnum {
    GREATER_THAN("GREATERTHAN", "\u5927\u4e8e"),
    LESS_THAN("LESSTHAN", "\u5c0f\u4e8e"),
    EQUALS("EQUALS", "\u7b49\u4e8e"),
    NOT_EQUALS("NOTEQUALS", "\u4e0d\u7b49\u4e8e"),
    CONTAINS("CONTAINS", "\u5305\u542b"),
    NOT_CONTAINS("NOTCONTAINS", "\u4e0d\u5305\u542b"),
    GREATER_THAN_OR_EQUALS("GREATERTHANOREQUALS", "\u5927\u4e8e\u7b49\u4e8e"),
    LESS_THAN_OR_EQUALS("LESSTHANOREQUALS", "\u5c0f\u4e8e\u7b49\u4e8e"),
    ISNULL("ISNULL", "\u4e3a\u7a7a"),
    IS_NOTNULL("ISNOTNULL", "\u4e0d\u4e3a\u7a7a");

    private final String name;
    private final String title;

    private OperatorEnum(String name, String title) {
        this.name = name;
        this.title = title;
    }

    public String getName() {
        return this.name;
    }

    public String getTitle() {
        return this.title;
    }
}

