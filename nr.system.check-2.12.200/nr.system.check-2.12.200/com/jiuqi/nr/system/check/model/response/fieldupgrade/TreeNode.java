/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 */
package com.jiuqi.nr.system.check.model.response.fieldupgrade;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

public class TreeNode {
    private String key;
    private String parent;
    private String title;
    private List<TreeNode> children;
    private Boolean expand = false;
    @JsonIgnore
    private TreeNode parentNode;
    private Boolean isLeaf = true;
    private Type type;

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParent() {
        return this.parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public Boolean getExpand() {
        return this.expand;
    }

    public void setExpand(Boolean expand) {
        this.expand = expand;
    }

    public void addChildren(TreeNode treeNode) {
        if (this.children == null) {
            this.children = new ArrayList<TreeNode>();
        }
        this.children.add(treeNode);
        this.setLeaf(false);
        treeNode.setParentNode(this);
    }

    public TreeNode getParentNode() {
        return this.parentNode;
    }

    public void setParentNode(TreeNode parentNode) {
        this.parentNode = parentNode;
    }

    @JsonProperty(value="isLeaf")
    public Boolean getLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(Boolean leaf) {
        this.isLeaf = leaf;
    }

    public Type getType() {
        return this.type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public static enum Type {
        TASK,
        GROUP;

    }
}

