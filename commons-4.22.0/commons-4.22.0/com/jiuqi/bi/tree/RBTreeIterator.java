/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.tree;

import com.jiuqi.bi.tree.RBTreeNode;
import com.jiuqi.bi.tree.RedBlackTree;
import java.util.Iterator;

public class RBTreeIterator<E>
implements Iterator<E> {
    private RedBlackTree<E> tree;
    private RBTreeNode<E> currNode;
    private RBTreeNode<E> firstNode;
    private E lastObj;
    private RBTreeNode<E> nextNode;
    private boolean isBeginning;

    public RBTreeIterator(RedBlackTree<E> tree) {
        this.tree = tree;
        this.isBeginning = true;
    }

    public RBTreeIterator(RedBlackTree<E> tree, RBTreeNode<E> minNode, E maxObj) {
        this.tree = tree;
        this.isBeginning = true;
        this.firstNode = minNode;
        this.lastObj = maxObj;
    }

    public void init(RBTreeNode<E> first, E lastObj) {
        this.firstNode = first;
        this.lastObj = lastObj;
        this.isBeginning = true;
    }

    @Override
    public boolean hasNext() {
        if (this.isBeginning) {
            return this.firstNode != this.tree.getNil() && this.firstNode != null;
        }
        if (this.currNode == this.tree.getNil() || this.currNode == null) {
            return false;
        }
        this.nextNode = this.currNode.getNext();
        if (this.nextNode == this.tree.getNil() || this.nextNode == null) {
            return false;
        }
        return this.tree.getComparator().compare(this.nextNode.getData(), this.lastObj) <= 0;
    }

    @Override
    public E next() {
        if (this.isBeginning) {
            this.currNode = this.firstNode;
            this.isBeginning = false;
        } else {
            this.currNode = this.currNode.getNext();
        }
        E ret = this.currNode.getData();
        return ret;
    }

    @Override
    public void remove() {
        if (this.currNode == this.firstNode) {
            this.currNode = null;
            this.isBeginning = false;
        } else {
            this.currNode = this.currNode.getPre();
            this.tree.delete(this.currNode.getNext());
        }
    }
}

