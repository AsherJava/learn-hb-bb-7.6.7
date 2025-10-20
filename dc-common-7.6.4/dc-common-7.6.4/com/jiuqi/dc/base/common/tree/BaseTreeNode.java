/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.dc.base.common.tree;

import java.util.ArrayList;
import java.util.List;

public class BaseTreeNode {
    private String code;
    private String parentCode;
    private List<BaseTreeNode> children;
    private boolean leaf;
    private List<String> parents;

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentCode() {
        return this.parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public List<BaseTreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<BaseTreeNode> children) {
        this.children = children;
    }

    public boolean isLeaf() {
        return this.leaf;
    }

    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    public void addChild(BaseTreeNode baseTreeNode) {
        if (this.children == null) {
            this.setChildren(new ArrayList<BaseTreeNode>());
        }
        this.getChildren().add(baseTreeNode);
    }

    public List<String> getParents() {
        return this.parents;
    }

    public void setParents(List<String> parents) {
        this.parents = parents;
    }

    public void addParents(String parentid) {
        if (this.parents == null) {
            this.parents = new ArrayList<String>();
        }
        this.getParents().add(parentid);
    }
}

