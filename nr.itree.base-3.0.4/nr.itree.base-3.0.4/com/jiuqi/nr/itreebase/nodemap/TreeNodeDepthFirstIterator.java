/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.itreebase.nodemap;

import com.jiuqi.nr.itreebase.nodemap.TreeNode;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class TreeNodeDepthFirstIterator<E>
implements Iterator<TreeNode<E>> {
    private Stack<TreeNode<E>> stack = new Stack();

    public TreeNodeDepthFirstIterator(TreeNode<E> node) {
        this.stack.push(node);
    }

    public TreeNodeDepthFirstIterator(List<TreeNode<E>> tree) {
        this.reverseAdd(tree);
    }

    @Override
    public boolean hasNext() {
        return !this.stack.isEmpty();
    }

    @Override
    public TreeNode<E> next() {
        TreeNode<E> node = this.stack.pop();
        if (node.hasChildren()) {
            List<TreeNode<E>> children = node.getChildren();
            this.reverseAdd(children);
        }
        return node;
    }

    private void reverseAdd(List<TreeNode<E>> nodes) {
        for (int i = nodes.size() - 1; i >= 0; --i) {
            this.stack.push(nodes.get(i));
        }
    }
}

