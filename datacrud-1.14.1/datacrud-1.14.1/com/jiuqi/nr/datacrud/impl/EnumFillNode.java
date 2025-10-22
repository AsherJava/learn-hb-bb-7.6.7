/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.impl;

import com.jiuqi.nr.datacrud.spi.IEnumFillNode;
import java.util.ArrayList;
import java.util.List;

public class EnumFillNode
implements IEnumFillNode {
    private String entityId;
    private String key;
    private EnumFillNode leftNode;
    private List<EnumFillNode> children = new ArrayList<EnumFillNode>();

    public EnumFillNode(String entityId, String key) {
        this.entityId = entityId;
        this.key = key;
    }

    public EnumFillNode(IEnumFillNode node) {
        this.entityId = node.getEntityId();
        this.key = node.getKey();
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public IEnumFillNode getLeft() {
        return this.leftNode;
    }

    public void setLeftNode(EnumFillNode leftNode) {
        this.leftNode = leftNode;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getEntityId() {
        return this.entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public List<EnumFillNode> getChildren() {
        return this.children;
    }
}

