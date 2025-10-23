/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.task.api.dto;

import com.jiuqi.nr.task.api.tree.TreeData;

public class IFormTreeNode
implements TreeData {
    private String key;
    private String title;
    private String code;
    private String parent;
    private NodeType type;

    @Override
    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
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

    @Override
    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public static enum NodeType {
        FORMGROUP,
        FORM;

    }
}

