/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.summary.vo;

import com.jiuqi.nr.summary.vo.NodeType;

public class CaliberTreeLoadParam {
    private String mainTaskKey;
    private String nodeKey;
    private String entityKey;
    private NodeType type;
    private boolean queryAll;
    private int queryLevel;
    private String nodeTitle;

    public String getMainTaskKey() {
        return this.mainTaskKey;
    }

    public void setMainTaskKey(String mainTaskKey) {
        this.mainTaskKey = mainTaskKey;
    }

    public String getNodeKey() {
        return this.nodeKey;
    }

    public void setNodeKey(String nodeKey) {
        this.nodeKey = nodeKey;
    }

    public String getEntityKey() {
        return this.entityKey;
    }

    public void setEntityKey(String entityKey) {
        this.entityKey = entityKey;
    }

    public NodeType getType() {
        return this.type;
    }

    public void setType(NodeType type) {
        this.type = type;
    }

    public boolean isQueryAll() {
        return this.queryAll;
    }

    public void setQueryAll(boolean queryAll) {
        this.queryAll = queryAll;
    }

    public int getQueryLevel() {
        return this.queryLevel;
    }

    public void setQueryLevel(int queryLevel) {
        this.queryLevel = queryLevel;
    }

    public String getNodeTitle() {
        return this.nodeTitle;
    }

    public void setNodeTitle(String nodeTitle) {
        this.nodeTitle = nodeTitle;
    }
}

