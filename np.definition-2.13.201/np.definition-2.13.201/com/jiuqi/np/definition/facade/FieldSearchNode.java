/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.definition.facade;

public class FieldSearchNode {
    private String key;
    private String title;
    private String code;
    private String order;
    private String parentId;
    private NodeType type;
    private boolean showField;
    private String tableType;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public boolean getShowField() {
        return this.showField;
    }

    public void setShowField(boolean showField) {
        this.showField = showField;
    }

    public String getTableType() {
        return this.tableType;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public static enum NodeType {
        TABLEGROUP,
        TABLE,
        FIELDGROUP,
        FIELD;

    }
}

