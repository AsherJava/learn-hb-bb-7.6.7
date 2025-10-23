/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonValue
 */
package com.jiuqi.nr.zb.scheme.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.HashMap;
import java.util.Map;

public enum NodeType {
    ZB_SCHEME_GROUP(1, "\u6307\u6807\u4f53\u7cfb\u5206\u7ec4"),
    ZB_SCHEME(2, "\u6307\u6807\u4f53\u7cfb"),
    ZB_PROP(4, "\u6307\u6807\u4f53\u7cfb\u6269\u5c55\u5c5e\u6027"),
    ZB_GROUP(8, "\u6307\u6807\u5206\u7ec4"),
    ZB_INFO(16, "\u6307\u6807");

    private final int value;
    private final String title;
    private static final Map<Integer, NodeType> mappings;

    private NodeType(int i, String title) {
        this.value = i;
        this.title = title;
    }

    @JsonValue
    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    @JsonCreator
    public static NodeType valueOf(int value) {
        return mappings.get(value);
    }

    static {
        mappings = new HashMap<Integer, NodeType>();
        for (NodeType dataType : NodeType.values()) {
            mappings.put(dataType.getValue(), dataType);
        }
    }
}

