/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tree;

import com.jiuqi.bi.util.tree.TreeIterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TreeNode {
    private Object item;
    private TreeNode parent;
    private List children = new ArrayList();
    private Object tag;

    public TreeNode() {
    }

    public TreeNode(Object item) {
        this();
        this.item = item;
    }

    public Object getItem() {
        return this.item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public TreeNode getParent() {
        return this.parent;
    }

    public void setParent(TreeNode parent) {
        if (this.parent != null) {
            this.parent.children.remove(this);
        }
        this.parent = parent;
        if (parent != null) {
            parent.children.add(this);
        }
    }

    public int size() {
        return this.children.size();
    }

    public TreeNode get(int index) {
        return (TreeNode)this.children.get(index);
    }

    public void add(int index, TreeNode node) {
        node.setParent(null);
        this.children.add(index, node);
        node.parent = this;
    }

    public void remove(int index) {
        this.remove(this.get(index));
    }

    public void remove(TreeNode node) {
        node.setParent(null);
    }

    public TreeNode[] getChildren() {
        return this.children.toArray(new TreeNode[0]);
    }

    public int level() {
        int level = 0;
        for (TreeNode p = this.getParent(); p != null; p = p.getParent()) {
            ++level;
        }
        return level;
    }

    public Object getTag() {
        return this.tag;
    }

    public void setTag(Object tag) {
        this.tag = tag;
    }

    public Iterator iterator() {
        return new TreeIterator(this);
    }

    public String toString() {
        StringBuffer buffer = new StringBuffer("[Level: ");
        buffer.append(this.level());
        buffer.append("; Node: ");
        buffer.append(this.item == null ? "<null>" : this.item.toString());
        buffer.append(", Children: ");
        buffer.append(this.size());
        buffer.append(']');
        return buffer.toString();
    }

    public void sort(Comparator c) {
        Stack<TreeNode> nodes = new Stack<TreeNode>();
        nodes.push(this);
        while (!nodes.isEmpty()) {
            TreeNode node = (TreeNode)nodes.pop();
            Collections.sort(node.children, c);
            Iterator i = node.children.iterator();
            while (i.hasNext()) {
                nodes.push((TreeNode)i.next());
            }
        }
    }
}

