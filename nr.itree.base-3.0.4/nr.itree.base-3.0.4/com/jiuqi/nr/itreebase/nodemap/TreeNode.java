/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodemap;

import com.jiuqi.nr.itreebase.nodemap.TreeNodeBreadthFirstIterator;
import com.jiuqi.nr.itreebase.nodemap.TreeNodeDepthFirstIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TreeNode<E> {
    private E data;
    private int level = 1;
    private String key;
    private TreeNode<E> parent;
    private List<TreeNode<E>> children = new ArrayList<TreeNode<E>>(0);

    public TreeNode(String key, E data) {
        this.key = key;
        this.data = data;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public E getData() {
        return this.data;
    }

    public void setData(E data) {
        this.data = data;
    }

    public TreeNode<E> getParent() {
        return this.parent;
    }

    public void setParent(TreeNode<E> parent) {
        this.parent = parent;
    }

    public List<TreeNode<E>> getChildren() {
        return this.children;
    }

    public List<TreeNode<E>> getAllChildren() {
        ArrayList<TreeNode<TreeNode<E>>> allChildren = new ArrayList<TreeNode<TreeNode<E>>>();
        Iterator<TreeNode<E>> iterator = this.iterator(traverPloy.BREADTH_FIRST);
        while (iterator.hasNext()) {
            allChildren.add(iterator.next());
        }
        return allChildren;
    }

    public void setChildren(List<TreeNode<E>> children) {
        this.children = children;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public int getLevel() {
        return this.level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String[] getPath() {
        ArrayList<String> path = new ArrayList<String>();
        for (TreeNode<E> target = this; target != null; target = target.getParent()) {
            path.add(0, target.getKey());
        }
        return path.toArray(new String[0]);
    }

    public int getMaxDepth() {
        int maxLevel = this.getLevel();
        Iterator<TreeNode<E>> iterator = this.iterator(traverPloy.BREADTH_FIRST);
        while (iterator.hasNext()) {
            TreeNode<E> next = iterator.next();
            if (next.getLevel() <= maxLevel) continue;
            maxLevel = next.getLevel();
        }
        return maxLevel;
    }

    public void appendChild(String key, E data) {
        this.children.add(this.getNode(data));
    }

    public void appendChild(String key, int index, E data) {
        this.children.add(index, this.getNode(data));
    }

    public void appendChild(TreeNode<E> child) {
        if (child != null) {
            child.setParent(this);
            this.children.add(child);
        }
    }

    public Iterator<TreeNode<E>> iterator(traverPloy ploy) {
        if (Objects.requireNonNull(ploy) == traverPloy.DEPTH_FIRST) {
            return new TreeNodeDepthFirstIterator(this);
        }
        return new TreeNodeBreadthFirstIterator(this);
    }

    private TreeNode<E> getNode(E data) {
        TreeNode<E> node = new TreeNode<E>(this.key, data);
        node.setParent(this);
        node.setLevel(this.level + 1);
        return node;
    }

    public static enum traverPloy {
        DEPTH_FIRST,
        BREADTH_FIRST;

    }
}

