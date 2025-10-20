/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.common.itree;

import com.jiuqi.nr.common.itree.INode;
import com.jiuqi.nr.common.itree.ITree;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

public class ITreeBreadthFirstIterator<E extends INode>
implements Iterator<ITree<E>> {
    private Deque<ITree<E>> nodeQue = new ArrayDeque<ITree<E>>(0);

    public ITreeBreadthFirstIterator(ITree<E> node) {
        this.nodeQue.add(node);
    }

    public ITreeBreadthFirstIterator(List<ITree<E>> tree) {
        tree.forEach(child -> this.nodeQue.addLast((ITree<ITree>)child));
    }

    @Override
    public boolean hasNext() {
        return !this.nodeQue.isEmpty();
    }

    @Override
    public ITree<E> next() {
        ITree<E> node = this.nodeQue.pollFirst();
        if (node.hasChildren()) {
            List<ITree<E>> children = node.getChildren();
            children.forEach(child -> this.nodeQue.addLast((ITree<ITree>)child));
        }
        return node;
    }

    public ITree<E> peekFirst() {
        return this.nodeQue.peekFirst();
    }
}

