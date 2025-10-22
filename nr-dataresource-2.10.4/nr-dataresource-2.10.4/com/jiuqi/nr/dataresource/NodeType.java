/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.dataresource;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.dataresource.DataResourceKind;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum NodeType {
    RESOURCE_GROUP(DataResourceKind.RESOURCE_GROUP.getValue(), "\u76ee\u5f55"),
    DIM_GROUP(DataResourceKind.DIM_GROUP.getValue(), "\u7ef4\u5ea6"),
    DIM_FMDM_GROUP(DataResourceKind.DIM_FMDM_GROUP.getValue(), "\u5c01\u9762\u4ee3\u7801\u7ef4\u5ea6"),
    TREE_GROUP(8, "\u8d44\u6e90\u6811\u5206\u7ec4"),
    TREE(16, "\u8d44\u6e90\u6811"),
    TABLE_DIM_GROUP(32, "\u8868\u5185\u7ef4\u5ea6"),
    FIELD_ZB_LINK(64, "\u6307\u6807"),
    FIELD_LINK(128, "\u5b57\u6bb5"),
    MD_INFO(DataResourceKind.MD_INFO.getValue(), "\u5355\u4f4d\u4fe1\u606f\u8868");

    private final int value;
    private final String title;
    private static final HashMap<Integer, NodeType> MAP;
    private static final HashMap<String, NodeType> TITLE_MAP;

    private NodeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public int getValue() {
        return this.value;
    }

    public String getTitle() {
        return this.title;
    }

    public static NodeType valueOf(int value) {
        return MAP.get(value);
    }

    public static NodeType titleOf(String title) {
        return TITLE_MAP.get(title);
    }

    public static NodeType[] interestType(int type) {
        if (type < 0) {
            return new NodeType[0];
        }
        ArrayList<NodeType> values = new ArrayList<NodeType>(NodeType.values().length);
        for (NodeType value : NodeType.values()) {
            if ((value.getValue() & type) == 0) continue;
            values.add(value);
        }
        return values.toArray(new NodeType[0]);
    }

    @JsonCreator
    public static NodeType forValues(@JsonProperty(value="value") Integer value, @JsonProperty(value="title") String title) {
        if (value == null && title == null) {
            return null;
        }
        if (value != null && title != null) {
            NodeType nodeType = MAP.get(value);
            NodeType byTitle = TITLE_MAP.get(title);
            if (byTitle == null) {
                return null;
            }
            return byTitle.equals((Object)nodeType) ? nodeType : null;
        }
        if (value != null) {
            return MAP.get(value);
        }
        return TITLE_MAP.get(title);
    }

    static {
        NodeType[] values = NodeType.values();
        MAP = new HashMap(values.length);
        TITLE_MAP = new HashMap(values.length);
        for (NodeType value : values) {
            MAP.put(value.value, value);
            TITLE_MAP.put(value.title, value);
        }
    }
}

