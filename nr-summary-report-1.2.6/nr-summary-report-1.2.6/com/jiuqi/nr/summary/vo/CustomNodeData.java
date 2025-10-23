/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.NodeType;

public class CustomNodeData {
    private String key;
    private String code;
    private String title;
    private NodeType type;
    private String parentTitle;
    private boolean deployed;

    public CustomNodeData(NodeType type) {
        this.type = type;
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

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public String getParentTitle() {
        return this.parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }

    public boolean isDeployed() {
        return this.deployed;
    }

    public void setDeployed(boolean deployed) {
        this.deployed = deployed;
    }
}

