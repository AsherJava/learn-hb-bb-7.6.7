/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonIgnore
 *  com.fasterxml.jackson.annotation.JsonProperty
 *  com.jiuqi.nr.common.itree.ITree
 */
package com.jiuqi.nr.entity.component.tree.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jiuqi.nr.common.itree.ITree;
import com.jiuqi.nr.entity.component.tree.vo.TreeNode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JUITreeNode {
    private String key;
    private String parent;
    private String title;
    private boolean expand;
    private String order;
    private boolean selected;
    private boolean disabled;
    private boolean checked;
    private TreeNode data;
    private String icon;
    private boolean isLeaf = true;
    private List<JUITreeNode> children;

    public JUITreeNode() {
    }

    public JUITreeNode(ITree<TreeNode> data) {
        if (data != null) {
            this.key = data.getKey();
            if (data.getParent() != null) {
                this.parent = data.getParent().getKey();
            }
            this.title = data.getTitle();
            this.expand = data.isExpanded();
            this.selected = data.isSelected();
            this.disabled = data.isDisabled();
            this.checked = data.isChecked();
            this.data = (TreeNode)data.getData();
            if (data.getIcons() != null) {
                this.icon = Arrays.toString(data.getIcons());
            }
            this.isLeaf = data.isLeaf();
            if (data.hasChildren()) {
                ArrayList<JUITreeNode> list = new ArrayList<JUITreeNode>();
                for (ITree child : data.getChildren()) {
                    list.add(new JUITreeNode((ITree<TreeNode>)child));
                }
                this.children = list;
            }
        }
    }

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
        if (null == this.title) {
            this.title = null == this.getData() ? null : this.getData().getTitle();
        }
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isExpand() {
        return this.expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    @JsonIgnore
    public String getOrder() {
        return this.order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean isDisabled() {
        return this.disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }

    public boolean isChecked() {
        return this.checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public TreeNode getData() {
        return this.data;
    }

    public void setData(TreeNode data) {
        this.data = data;
    }

    @JsonIgnore
    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @JsonProperty(value="isLeaf")
    public boolean isLeaf() {
        return this.isLeaf;
    }

    public void setLeaf(boolean leaf) {
        this.isLeaf = leaf;
    }

    public List<JUITreeNode> getChildren() {
        return this.children;
    }

    public void setChildren(List<JUITreeNode> children) {
        this.children = children;
    }

    public void addChildren(JUITreeNode children) {
        if (this.children == null) {
            this.children = new ArrayList<JUITreeNode>(16);
        }
        this.children.add(children);
    }
}

