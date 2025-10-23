/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.core.NodeType
 */
package com.jiuqi.nr.param.transfer.datascheme;

import com.jiuqi.nr.datascheme.api.core.NodeType;

public class TransferId {
    private NodeType nodeType;
    private String key;
    private boolean business;

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public boolean isBusiness() {
        return this.business;
    }

    public void setBusiness(boolean business) {
        this.business = business;
    }
}

