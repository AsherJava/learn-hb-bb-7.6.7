/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.workflow2.service.common;

import com.jiuqi.nr.workflow2.service.common.TreeNode;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TreeNodeDepthFirstIterator<K, E>
implements Iterator<TreeNode<K, E>> {
    private final Stack<TreeNode<K, E>> stack = new Stack();

    public TreeNodeDepthFirstIterator(TreeNode<K, E> node) {
        this.stack.push(node);
    }

    public TreeNodeDepthFirstIterator(List<TreeNode<K, E>> tree) {
        this.reverseAdd(tree);
    }

    @Override
    public boolean hasNext() {
        return !this.stack.isEmpty();
    }

    @Override
    public TreeNode<K, E> next() {
        TreeNode<K, E> node = this.stack.pop();
        if (node.hasChildren()) {
            List<TreeNode<K, E>> children = node.getChildren();
            this.reverseAdd(children);
        }
        return node;
    }

    private void reverseAdd(List<TreeNode<K, E>> nodes) {
        for (int i = nodes.size() - 1; i >= 0; --i) {
            this.stack.push(nodes.get(i));
        }
    }
}

