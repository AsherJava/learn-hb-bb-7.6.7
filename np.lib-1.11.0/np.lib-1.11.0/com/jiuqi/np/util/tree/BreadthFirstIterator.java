/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util.tree;

import com.jiuqi.np.util.tree.TraversalIterator;
import com.jiuqi.np.util.tree.Tree;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class BreadthFirstIterator<E>
implements TraversalIterator<E> {
    private final LinkedList<Tree<E>> queue = new LinkedList();
    private Tree<E> lastNode;

    public BreadthFirstIterator(Tree<E> node) {
        this.queue.add(node);
        this.lastNode = null;
    }

    @Override
    public boolean hasNext() {
        return this.queue.size() > 0;
    }

    @Override
    public Tree<E> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("no more nodes!");
        }
        Tree<E> result = this.queue.removeFirst();
        List<Tree<E>> children = result.getChildren();
        if (children != null) {
            this.queue.addAll(children);
        }
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
        List<Tree<E>> children;
        if (this.lastNode != null && (children = this.lastNode.getChildren()) != null) {
            for (Tree<E> child : children) {
                this.queue.removeLast();
            }
        }
    }
}

