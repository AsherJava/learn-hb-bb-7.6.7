/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datascheme.api.core;

import com.jiuqi.nr.datascheme.api.core.INode;
import com.jiuqi.nr.datascheme.api.core.ITree;
import com.jiuqi.nr.datascheme.api.core.ITreeIterator;
import java.util.List;
import java.util.Stack;

public class DepthFirstIterator<E extends INode>
implements ITreeIterator<E> {
    private Stack<ITree<E>> stack = new Stack();

    public DepthFirstIterator(ITree<E> tree) {
        this.stack.push(tree);
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
            children.forEach(child -> this.stack.push((ITree<ITree>)child));
        }
        return node;
    }
}

