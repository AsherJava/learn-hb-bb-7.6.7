/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.itree;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class ITreeDepthFirstIterator<E extends INode>
implements Iterator<ITree<E>> {
    private Stack<ITree<E>> stack = new Stack();

    public ITreeDepthFirstIterator(ITree<E> node) {
        this.stack.push(node);
    }

    public ITreeDepthFirstIterator(List<ITree<E>> tree) {
        this.reverseAdd(tree);
    }

    @Override
    public boolean hasNext() {
        return !this.stack.isEmpty();
    }

    @Override
    public ITree<E> next() {
        ITree<E> node = this.stack.pop();
        if (node.hasChildren()) {
            List<ITree<E>> children = node.getChildren();
            this.reverseAdd(children);
        }
        return node;
    }

    private void reverseAdd(List<ITree<E>> nodes) {
        for (int i = nodes.size() - 1; i >= 0; --i) {
            this.stack.push(nodes.get(i));
        }
    }
}

