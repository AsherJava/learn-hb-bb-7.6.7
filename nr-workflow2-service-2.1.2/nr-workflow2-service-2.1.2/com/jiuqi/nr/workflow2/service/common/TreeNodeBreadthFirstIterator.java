/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.service.common.TreeNode;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class TreeNodeBreadthFirstIterator<K, E>
implements Iterator<TreeNode<K, E>> {
    private final Deque<TreeNode<K, E>> nodeQue = new ArrayDeque<TreeNode<K, E>>(0);

    public TreeNodeBreadthFirstIterator(TreeNode<K, E> node) {
        this.nodeQue.add(node);
    }

    public TreeNodeBreadthFirstIterator(List<TreeNode<K, E>> tree) {
        tree.forEach(this.nodeQue::addLast);
    }

    @Override
    public boolean hasNext() {
        return !this.nodeQue.isEmpty();
    }

    @Override
    public TreeNode<K, E> next() {
        TreeNode<K, E> node = this.nodeQue.pollFirst();
        assert (node != null);
        if (node.hasChildren()) {
            List<TreeNode<K, E>> children = node.getChildren();
            children.forEach(this.nodeQue::addLast);
        }
        return node;
    }
}

