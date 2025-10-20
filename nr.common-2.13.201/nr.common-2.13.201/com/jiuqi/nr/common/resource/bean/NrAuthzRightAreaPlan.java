/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.resource.bean;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum NrAuthzRightAreaPlan {
    NULL(0, "\u4e0d\u663e\u793a"),
    CHILDREN(1, "\u76f4\u63a5\u4e0b\u7ea7"),
    ROOT(2, "\u672c\u7ea7"),
    ALL(3, "\u672c\u7ea7\u52a0\u76f4\u63a5\u4e0b\u7ea7"),
    CHILDREN_PAGE(4, "\u5206\u79bb\u5206\u9875\u52a0\u8f7d\u6743\u9650\u6a21\u5f0f"),
    ALL_PAGE(5, "\u5c55\u793a\u672c\u7ea7\u52a0\u76f4\u63a5\u4e0b\u7ea7,\u5206\u9875\u4e0b\u7ea7\u6570\u636e");

    private static final Map<Integer, NrAuthzRightAreaPlan> VALUE_MAP;
    private int value;
    private String title;

    public static NrAuthzRightAreaPlan parseFrom(int value) {
        return VALUE_MAP.getOrDefault(value, ALL);
    }

    private NrAuthzRightAreaPlan(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    static {
        VALUE_MAP = Arrays.stream(NrAuthzRightAreaPlan.values()).collect(Collectors.toMap(NrAuthzRightAreaPlan::getValue, t -> t));
    }
}

