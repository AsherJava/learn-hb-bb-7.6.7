/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.ITreeIterator;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class BreadthFirstIterator<E extends INode>
implements ITreeIterator<E> {
    private Deque<ITree<E>> nodeQue = new ArrayDeque<ITree<E>>(0);

    public BreadthFirstIterator(ITree<E> tree) {
        this.nodeQue.add(tree);
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
}

