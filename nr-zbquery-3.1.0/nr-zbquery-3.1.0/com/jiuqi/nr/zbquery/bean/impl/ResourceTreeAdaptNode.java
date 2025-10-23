/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.dataresource.DataResourceDefine
 *  com.jiuqi.nr.dataresource.DataResourceNode
 *  com.jiuqi.nr.dataresource.NodeType
 *  com.jiuqi.nr.datascheme.api.core.INode
 */
package com.jiuqi.nr.zbquery.bean.impl;

import com.jiuqi.nr.dataresource.DataResourceDefine;
import com.jiuqi.nr.dataresource.DataResourceNode;
import com.jiuqi.nr.dataresource.NodeType;
import com.jiuqi.nr.datascheme.api.core.INode;

public class ResourceTreeAdaptNode
implements INode {
    private String key;
    private String code;
    private String title;
    private String owner;
    private String parentKey;
    private int type;
    private int nodeType;

    public ResourceTreeAdaptNode() {
    }

    public ResourceTreeAdaptNode(ResourceTreeAdaptNode adaptNode) {
        this.key = adaptNode.getKey();
        this.code = adaptNode.getCode();
        this.title = adaptNode.getTitle();
        this.owner = adaptNode.getOwner();
        this.parentKey = adaptNode.getParentKey();
        this.type = adaptNode.getType();
        this.nodeType = adaptNode.getNodeType();
    }

    public ResourceTreeAdaptNode(DataResourceNode node) {
        this.key = node.getKey();
        this.code = node.getCode();
        this.title = node.getTitle();
        this.parentKey = node.getParentKey();
        this.nodeType = node.getType();
    }

    public ResourceTreeAdaptNode(DataResourceDefine treeDefine) {
        this.key = treeDefine.getKey();
        this.title = treeDefine.getTitle();
        this.parentKey = treeDefine.getGroupKey();
        this.nodeType = NodeType.TREE.getValue();
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOwner() {
        return this.owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getParentKey() {
        return this.parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getKey() {
        return this.key;
    }

    public String getCode() {
        return this.code;
    }

    public String getTitle() {
        return this.title;
    }
}

