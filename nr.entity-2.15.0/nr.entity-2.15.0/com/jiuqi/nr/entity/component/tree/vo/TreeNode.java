/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.common.itree.INode
 */
package com.jiuqi.nr.entity.component.tree.vo;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.entity.common.NodeType;
import com.jiuqi.nr.entity.model.IEntityDefine;
import com.jiuqi.nr.entity.model.IEntityGroup;
import java.io.Serializable;

public class TreeNode
implements INode,
Serializable {
    private static final long serialVersionUID = 8144500624279366763L;
    private String key;
    private String code;
    private String title;
    private String parentId;
    private String[] path;
    private NodeType nodeType;
    private String bizKey;
    private boolean tree;

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

    public String getParentId() {
        return this.parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String[] getPath() {
        return this.path;
    }

    public void setPath(String[] path) {
        this.path = path;
    }

    public NodeType getNodeType() {
        return this.nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public String getBizKey() {
        return this.bizKey;
    }

    public void setBizKey(String bizKey) {
        this.bizKey = bizKey;
    }

    public boolean isTree() {
        return this.tree;
    }

    public void setTree(boolean tree) {
        this.tree = tree;
    }

    public static TreeNode copyFromGroup(IEntityGroup entityGroup) {
        TreeNode node = new TreeNode();
        node.setKey(entityGroup.getId());
        node.setTitle(entityGroup.getTitle());
        node.setParentId(entityGroup.getParentId());
        node.setNodeType(NodeType.GROUP);
        return node;
    }

    public static TreeNode copyFromEntity(IEntityDefine entityDefine) {
        TreeNode node = new TreeNode();
        node.setKey(entityDefine.getId());
        node.setTitle(entityDefine.getTitle());
        node.setCode(entityDefine.getCode());
        node.setTree(entityDefine.isTree());
        node.setNodeType(NodeType.SUB_NODE);
        return node;
    }

    public static String buildEntityTitle(String title, String code) {
        return title + "[" + code + "]";
    }
}

