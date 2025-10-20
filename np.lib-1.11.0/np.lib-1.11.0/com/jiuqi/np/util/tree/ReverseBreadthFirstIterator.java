/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util.tree;

import com.jiuqi.np.util.tree.BreadthFirstIterator;
import com.jiuqi.np.util.tree.TraversalIterator;
import com.jiuqi.np.util.tree.Tree;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class ReverseBreadthFirstIterator<T>
implements TraversalIterator<T> {
    private final LinkedList<Tree<T>> stack = new LinkedList();
    private Tree<T> lastNode;

    public ReverseBreadthFirstIterator(Tree<T> node) {
        BreadthFirstIterator<T> i = new BreadthFirstIterator<T>(node);
        while (i.hasNext()) {
            Object tree = i.next();
            this.stack.addFirst((Tree<T>)tree);
        }
        this.lastNode = null;
    }

    @Override
    public boolean hasNext() {
        return this.stack.size() > 0;
    }

    @Override
    public Tree<T> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("no more nodes!");
        }
        Tree<T> result = this.stack.removeFirst();
        this.lastNode = result;
        return result;
    }

    @Override
    public void remove() {
        if (this.lastNode != null) {
            this.lastNode.prune(true, true);
            this.skip();
        }
    }

    @Override
    public void skip() {
        this.stack.removeFirst();
    }
}

