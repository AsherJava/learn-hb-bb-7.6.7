/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.service.common.TreeNodeBreadthFirstIterator;
import com.jiuqi.nr.workflow2.service.common.TreeNodeDepthFirstIterator;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class TreeNode<K, E> {
    private final K key;
    private final E data;
    private TreeNode<K, E> parent;
    private List<TreeNode<K, E>> children = new ArrayList<TreeNode<K, E>>(0);

    public TreeNode(K key, E data) {
        this.key = key;
        this.data = data;
    }

    public K getKey() {
        return this.key;
    }

    public E getData() {
        return this.data;
    }

    public TreeNode<K, E> getParent() {
        return this.parent;
    }

    public void setParent(TreeNode<K, E> parent) {
        this.parent = parent;
    }

    public List<TreeNode<K, E>> getChildren() {
        return this.children;
    }

    public void setChildren(List<TreeNode<K, E>> children) {
        this.children = children;
    }

    public List<TreeNode<K, E>> getAllChildren() {
        ArrayList<TreeNode<K, TreeNode<K, E>>> allChildren = new ArrayList<TreeNode<K, TreeNode<K, E>>>();
        Iterator<TreeNode<K, E>> iterator = this.iterator(traverPloy.BREADTH_FIRST);
        while (iterator.hasNext()) {
            allChildren.add(iterator.next());
        }
        return allChildren;
    }

    public boolean hasChildren() {
        return !this.children.isEmpty();
    }

    public void appendChild(K key, E data) {
        this.children.add(new TreeNode<K, E>(key, data));
    }

    public void appendChild(TreeNode<K, E> child) {
        if (child != null) {
            this.children.add(child);
        }
    }

    public TreeNode<K, E> findNode(K key) {
        Iterator<TreeNode<K, E>> iterator = this.iterator(traverPloy.DEPTH_FIRST);
        while (iterator.hasNext()) {
            TreeNode<K, E> node = iterator.next();
            if (!node.getKey().equals(key)) continue;
            return node;
        }
        return null;
    }

    public Iterator<TreeNode<K, E>> iterator(traverPloy ploy) {
        if (Objects.requireNonNull(ploy) == traverPloy.DEPTH_FIRST) {
            return new TreeNodeDepthFirstIterator(this);
        }
        return new TreeNodeBreadthFirstIterator(this);
    }

    public static enum traverPloy {
        DEPTH_FIRST,
        BREADTH_FIRST;

    }
}

