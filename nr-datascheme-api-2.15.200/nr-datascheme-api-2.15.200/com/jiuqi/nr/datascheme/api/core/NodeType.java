/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.datascheme.api.core;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.HashMap;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum NodeType {
    SCHEME_GROUP(1, "\u65b9\u6848\u5206\u7ec4"),
    DIM(2, "\u516c\u5171\u7ef4\u5ea6"),
    SCHEME(4, "\u6570\u636e\u65b9\u6848"),
    GROUP(8, "\u76ee\u5f55"),
    ACCOUNT_TABLE(16, "\u53f0\u8d26\u8868"),
    TABLE(32, "\u6307\u6807\u8868"),
    DETAIL_TABLE(64, "\u660e\u7ec6\u8868"),
    MUL_DIM_TABLE(128, "\u591a\u7ef4\u8868"),
    FMDM_TABLE(256, "\u5c01\u9762\u4ee3\u7801\u8868"),
    FIELD_ZB(512, "\u6307\u6807"),
    FIELD(1024, "\u5b57\u6bb5"),
    TABLE_DIM(2048, "\u6d6e\u52a8\u884c\u7f16\u7801"),
    ENTITY_ATTRIBUTE(8192, "\u5b9e\u4f53\u5c5e\u6027"),
    MD_INFO(16384, "\u5355\u4f4d\u4fe1\u606f\u8868");

    private final int value;
    private final String title;
    private static final HashMap<Integer, NodeType> MAP;
    private static final HashMap<String, NodeType> TITLE_MAP;

    private NodeType(int value, String title) {
        this.value = value;
        this.title = title;
    }

    public static int getInterestTypeByLeft(NodeType leaf) {
        if (leaf == null) {
            return 0;
        }
        switch (leaf) {
            case SCHEME_GROUP: {
                return SCHEME_GROUP.getValue();
            }
            case DIM: {
                return SCHEME_GROUP.getValue() | SCHEME.getValue() | DIM.getValue();
            }
            case SCHEME: {
                return SCHEME.getValue() | SCHEME_GROUP.getValue();
            }
            case GROUP: {
                return SCHEME.getValue() | SCHEME_GROUP.getValue() | GROUP.getValue() | DIM.getValue();
            }
            case TABLE: 
            case DETAIL_TABLE: 
            case ACCOUNT_TABLE: 
            case MUL_DIM_TABLE: 
            case MD_INFO: 
            case FMDM_TABLE: {
                return SCHEME.getValue() | SCHEME_GROUP.getValue() | GROUP.getValue() | TABLE.getValue() | DETAIL_TABLE.getValue() | ACCOUNT_TABLE.getValue() | MUL_DIM_TABLE.getValue() | MD_INFO.getValue() | FMDM_TABLE.getValue() | DIM.getValue();
            }
            case FIELD: 
            case FIELD_ZB: 
            case TABLE_DIM: 
            case ENTITY_ATTRIBUTE: {
                return SCHEME.getValue() | SCHEME_GROUP.getValue() | GROUP.getValue() | TABLE.getValue() | DETAIL_TABLE.getValue() | ACCOUNT_TABLE.getValue() | MUL_DIM_TABLE.getValue() | MD_INFO.getValue() | FMDM_TABLE.getValue() | FIELD.getValue() | FIELD_ZB.getValue() | TABLE_DIM.getValue() | ENTITY_ATTRIBUTE.getValue() | DIM.getValue();
            }
        }
        return 0;
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

