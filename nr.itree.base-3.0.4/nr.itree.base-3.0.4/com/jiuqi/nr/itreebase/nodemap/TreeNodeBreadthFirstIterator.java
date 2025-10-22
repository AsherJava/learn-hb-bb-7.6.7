/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodemap;

import com.jiuqi.nr.itreebase.nodemap.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class TreeNodeBreadthFirstIterator<E>
implements Iterator<TreeNode<E>> {
    private final Deque<TreeNode<E>> nodeQue = new ArrayDeque<TreeNode<E>>(0);

    public TreeNodeBreadthFirstIterator(TreeNode<E> node) {
        this.nodeQue.add(node);
    }

    public TreeNodeBreadthFirstIterator(List<TreeNode<E>> tree) {
        tree.forEach(this.nodeQue::addLast);
    }

    @Override
    public boolean hasNext() {
        return !this.nodeQue.isEmpty();
    }

    @Override
    public TreeNode<E> next() {
        TreeNode<E> node = this.nodeQue.pollFirst();
        assert (node != null);
        if (node.hasChildren()) {
            List<TreeNode<E>> children = node.getChildren();
            children.forEach(this.nodeQue::addLast);
        }
        return node;
    }
}

