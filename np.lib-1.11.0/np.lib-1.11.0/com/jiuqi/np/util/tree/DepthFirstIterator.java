/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util.tree;

import com.jiuqi.np.util.tree.TraversalIterator;
import com.jiuqi.np.util.tree.Tree;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class DepthFirstIterator<T>
implements TraversalIterator<T> {
    private final LinkedList<Tree<T>> queue = new LinkedList();
    private Tree<T> lastNode;

    public DepthFirstIterator(Tree<T> node) {
        this.queue.add(node);
    }

    @Override
    public boolean hasNext() {
        return this.queue.size() > 0;
    }

    @Override
    public Tree<T> next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("no more nodes!");
        }
        Tree<T> result = this.queue.removeFirst();
        List<Tree<T>> children = result.getChildren();
        if (children != null) {
            this.queue.addAll(0, children);
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
        List<Tree<T>> children;
        if (this.lastNode != null && (children = this.lastNode.getChildren()) != null) {
            for (Tree<T> child : children) {
                this.queue.removeFirst();
            }
        }
    }
}

