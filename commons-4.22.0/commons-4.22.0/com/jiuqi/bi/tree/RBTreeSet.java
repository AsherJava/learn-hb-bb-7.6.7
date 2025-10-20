/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.tree;

import com.jiuqi.bi.tree.RBTreeNode;
import com.jiuqi.bi.tree.RedBlackTree;
import java.util.AbstractSet;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;

public class RBTreeSet<E>
extends AbstractSet<E> {
    private RedBlackTree<E> tree;
    private Comparator<? super E> comparator;

    public RBTreeSet() {
        this.comparator = new InnerComparable<E>();
        this.tree = new RedBlackTree<E>(this.comparator);
    }

    public RBTreeSet(E[] datas) {
        this.comparator = new InnerComparable<E>();
        this.tree = new RedBlackTree<E>(this.comparator, datas);
    }

    public RBTreeSet(Collection<? extends E> dataList) {
        this.comparator = new InnerComparable<E>();
        this.tree = new RedBlackTree<E>(this.comparator, dataList);
    }

    public RBTreeSet(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.tree = new RedBlackTree<E>(comparator);
    }

    public RBTreeSet(Comparator<? super E> comparator, E[] datas) {
        this.comparator = comparator;
        this.tree = new RedBlackTree<E>(comparator, datas);
    }

    public RBTreeSet(Comparator<? super E> comparator, Collection<? extends E> dataList) {
        this.comparator = comparator;
        this.tree = new RedBlackTree<E>(comparator, dataList);
    }

    @Override
    public boolean add(E o) {
        return this.tree.add(o);
    }

    @Override
    public boolean add(E[] datas) {
        return this.tree.add(datas);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.tree.add(c);
    }

    public boolean addUnique(E o) {
        if (o == null) {
            throw new IllegalArgumentException("\u4f20\u5165\u7684\u5bf9\u8c61\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01");
        }
        return this.tree.addUnique(o);
    }

    public boolean addCoverageFirst(E o) {
        if (o == null) {
            throw new IllegalArgumentException("\u4f20\u5165\u7684\u5bf9\u8c61\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01");
        }
        return this.tree.addCoverageFirst(o);
    }

    public boolean addCoverageLast(E o) {
        if (o == null) {
            throw new IllegalArgumentException("\u4f20\u5165\u7684\u5bf9\u8c61\u4e0d\u5141\u8bb8\u4e3a\u7a7a\uff01");
        }
        return this.tree.addCoverageLast(o);
    }

    @Override
    public void clear() {
        this.tree = new RedBlackTree<E>(this.comparator);
    }

    @Override
    public boolean contains(Object o) {
        return this.tree.contains(o);
    }

    public boolean removeAll(E o) {
        return this.tree.delete(o);
    }

    @Override
    public boolean remove(Object o) {
        return this.tree.deleteFirst(o);
    }

    @Override
    public Iterator<E> iterator() {
        return this.tree.iterator();
    }

    public E search(E elem) {
        RBTreeNode<E> node = this.tree.search(elem);
        return node == null ? null : (E)node.getData();
    }

    public Iterator<E> search(E first, E last) {
        return this.tree.search(first, last);
    }

    public Iterator<E> qSearch(E first, E last) {
        return this.tree.qSearch(first, last);
    }

    public Iterator<E> search(Comparator<E> sComparator, E o) {
        return this.tree.search(sComparator, o);
    }

    public E searchFirst(E o) {
        return this.tree.searchFirst(o).getData();
    }

    public E searchLast(E o) {
        return this.tree.searchLast(o).getData();
    }

    public boolean remove(E[] datas) {
        return this.tree.delete(datas);
    }

    @Override
    public int size() {
        return this.tree.size();
    }

    public E last() {
        return this.tree.getMaxmumNode().getData();
    }

    public E first() {
        return this.tree.getMinumNode().getData();
    }

    private final class InnerComparable<T>
    implements Comparator<T> {
        private InnerComparable() {
        }

        @Override
        public int compare(T o1, T o2) {
            if (o1 == null) {
                return -1;
            }
            return ((Comparable)o1).compareTo(o2);
        }
    }
}

