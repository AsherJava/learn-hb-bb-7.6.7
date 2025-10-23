/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.INode
 */
package com.jiuqi.nr.datascheme.web.base;

import com.jiuqi.nr.datascheme.api.core.INode;

public class FieldTreeNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String order;
    private String parentId;
    private NodeType type;
    private boolean showField;

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public boolean isShowField() {
        return this.showField;
    }

    public void setShowField(boolean showField) {
        this.showField = showField;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public static enum NodeType {
        TABLEGROUP,
        TABLE,
        FIELDGROUP,
        FIELD;

    }
}

