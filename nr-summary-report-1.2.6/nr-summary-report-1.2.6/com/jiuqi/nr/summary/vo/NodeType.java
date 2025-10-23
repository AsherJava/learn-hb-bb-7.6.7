/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonCreator
 *  com.fasterxml.jackson.annotation.JsonFormat
 *  com.fasterxml.jackson.annotation.JsonFormat$Shape
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.summary.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.HashMap;
import java.util.Map;

@JsonFormat(shape=JsonFormat.Shape.OBJECT)
public enum NodeType {
    SUMMARY_SOLUTION_ROOT_GROUP(0, "\u6c47\u603b\u65b9\u6848\u6839\u8282\u70b9"),
    SUMMARY_SOLUTION_GROUP(1, "\u6587\u4ef6\u5939"),
    SUMMARY_SOLUTION(2, "\u6c47\u603b\u65b9\u6848"),
    SUMMARY_REPORT(3, "\u6c47\u603b\u8868"),
    SUMMARY_FORMULA(23, "\u6c47\u603b\u516c\u5f0f"),
    TASK_GROUP(4, "\u4efb\u52a1\u5206\u7ec4"),
    TASK(5, "\u4efb\u52a1"),
    DATA_SCHEME(6, "\u6570\u636e\u65b9\u6848"),
    DATA_TABLE(7, "\u6570\u636e\u8868"),
    DATA_FIELD(8, "\u6307\u6807"),
    MAIN_DIM_ROOT(9, "\u4e3b\u7ef4\u5ea6\u6839\u8282\u70b9"),
    MAIN_FIELD_DIM_ROOT(10, "\u4e3b\u7ef4\u5ea6\u5c5e\u6027\u7ef4\u5ea6\u6839\u8282\u70b9"),
    INNER_DIM_ROOT(11, "\u8868\u5185\u7ef4\u5ea6\u6839\u8282\u70b9"),
    CALIBER_ROOT(12, "\u53e3\u5f84\u6839\u8282\u70b9"),
    CALIBER(13, "\u53e3\u5f84"),
    CALIBER_DATA(14, "\u53e3\u5f84\u9879"),
    ENTITY_DEFINE(15, "\u5b9e\u4f53"),
    REFER_NODE(16, "\u5173\u8054\u57fa\u7840\u6570\u636e\u7684\u5b57\u6bb5"),
    ENTITY_ROW(17, "\u5b9e\u4f53\u6570\u636e"),
    ORG(18, "\u7ec4\u7ec7\u673a\u6784"),
    BASEDATA(19, "\u57fa\u7840\u6570\u636e"),
    ORGITEM(20, "\u7ec4\u7ec7\u673a\u6784\u6761\u76ee"),
    BASEDATAITEM(21, "\u57fa\u7840\u6570\u636e\u6761\u76ee"),
    INNERDIM(22, "\u8868\u5185\u7ef4\u5ea6");

    int code;
    String title;
    static final Map<Integer, NodeType> valueMap;

    private NodeType(int code, String title) {
        this.code = code;
        this.title = title;
    }

    public NodeType valueOf(int code) {
        for (NodeType typeEnum : NodeType.values()) {
            if (code != typeEnum.code) continue;
            return typeEnum;
        }
        return null;
    }

    @JsonCreator
    public static NodeType getNodeType(@JsonProperty(value="code") int code, @JsonProperty(value="title") String title) {
        NodeType type = valueMap.get(code);
        if (type != null && type.getTitle().equals(title)) {
            return type;
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    static {
        valueMap = new HashMap<Integer, NodeType>();
        for (NodeType nodeType : NodeType.values()) {
            valueMap.put(nodeType.getCode(), nodeType);
        }
    }
}

