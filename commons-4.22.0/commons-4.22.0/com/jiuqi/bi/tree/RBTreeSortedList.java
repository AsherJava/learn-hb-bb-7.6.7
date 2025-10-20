/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.tree;

import com.jiuqi.bi.tree.RBTreeNode;
import com.jiuqi.bi.tree.RedBlackTree;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class RBTreeSortedList<E>
extends AbstractSequentialList<E>
implements List<E>,
Queue<E>,
Cloneable,
Serializable {
    private static final long serialVersionUID = 8514438684241929978L;
    private transient RedBlackTree<E> tree;

    public RBTreeSortedList() {
        this.tree = new RedBlackTree(new DefaultComparator());
    }

    public RBTreeSortedList(Collection<? extends E> c) {
        this();
        this.addAll(c);
    }

    public RBTreeSortedList(Comparator<? super E> comparator) {
        this.tree = new RedBlackTree<E>(comparator);
    }

    public RBTreeSortedList(Collection<? extends E> c, Comparator<? super E> comparator) {
        this(comparator);
        this.addAll(c);
    }

    @Override
    public void add(int index, E element) {
        ++this.modCount;
        this.tree.add(element);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public int size() {
        return this.tree.size();
    }

    @Override
    public E element() {
        RBTreeNode<E> n = this.tree.getMinumNode();
        if (n == null) {
            throw new NoSuchElementException();
        }
        return n.getData();
    }

    @Override
    public boolean offer(E o) {
        return this.add(o);
    }

    @Override
    public E peek() {
        RBTreeNode<E> n = this.tree.getMinumNode();
        return n == null ? null : (E)n.getData();
    }

    @Override
    public E poll() {
        RBTreeNode<E> n = this.tree.getMinumNode();
        if (n == null) {
            return null;
        }
        E elem = n.getData();
        this.tree.delete(n);
        ++this.modCount;
        return elem;
    }

    @Override
    public E remove() {
        RBTreeNode<E> n = this.tree.getMinumNode();
        if (n == null) {
            throw new NoSuchElementException();
        }
        E elem = n.getData();
        this.tree.delete(n);
        ++this.modCount;
        return elem;
    }

    @Override
    public void clear() {
        this.tree = new RedBlackTree<E>(this.tree.getComparator());
        ++this.modCount;
    }

    @Override
    public int indexOf(Object o) {
        RBTreeNode<Object> node = this.tree.searchFirst(o);
        if (node == null || node == this.tree.getNil()) {
            return -1;
        }
        int p = -1;
        do {
            ++p;
        } while ((node = this.tree.getPre(node)) != null && node != this.tree.getNil());
        return p;
    }

    @Override
    public int lastIndexOf(Object o) {
        RBTreeNode<Object> node = this.tree.searchLast(o);
        if (node == null || node == this.tree.getNil()) {
            return -1;
        }
        int p = 0;
        do {
            ++p;
        } while ((node = this.tree.getNext(node)) != null && node != this.tree.getNil());
        return this.size() - p;
    }

    @Override
    public boolean contains(Object o) {
        return this.tree.contains(o);
    }

    public E search(E elem) {
        RBTreeNode<E> node = this.tree.search(elem);
        return node == null ? null : (E)node.getData();
    }

    public E searchFirst(E elem) {
        RBTreeNode<E> node = this.tree.searchFirst(elem);
        return node == null ? null : (E)node.getData();
    }

    public E searchLast(E elem) {
        RBTreeNode<E> node = this.tree.searchLast(elem);
        return node == null ? null : (E)node.getData();
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeInt(this.size());
        Iterator<E> itr = this.tree.iterator();
        while (itr.hasNext()) {
            s.writeObject(itr.next());
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int c = s.readInt();
        this.clear();
        for (int i = 0; i < c; ++i) {
            this.add(s.readObject());
        }
    }

    public Iterator<E> qSearch(E start, E end) {
        final Iterator<E> itr = this.tree.search(this.searchFirst(start), end);
        final int expectedModCount = this.modCount;
        return new Iterator<E>(){

            @Override
            public boolean hasNext() {
                return itr.hasNext();
            }

            @Override
            public E next() {
                this.checkForComodification();
                return itr.next();
            }

            @Override
            public void remove() {
                this.checkForComodification();
                itr.remove();
            }

            private void checkForComodification() {
                if (RBTreeSortedList.this.modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    private final class ListItr
    implements ListIterator<E> {
        private int nextIndex;
        private RBTreeNode<E> nextNode;
        private RBTreeNode<E> lastReturned;
        private int expectedModCount;

        public ListItr(int index) {
            this.expectedModCount = RBTreeSortedList.this.modCount;
            if (index < 0 || index > RBTreeSortedList.this.size()) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + RBTreeSortedList.this.size());
            }
            if (index < RBTreeSortedList.this.size() >> 1) {
                this.nextNode = RBTreeSortedList.this.tree.getMinumNode();
                this.nextIndex = 0;
                while (this.nextIndex < index) {
                    this.nextNode = RBTreeSortedList.this.tree.getNext(this.nextNode);
                    ++this.nextIndex;
                }
            } else {
                this.nextNode = RBTreeSortedList.this.tree.getMaxmumNode();
                this.nextIndex = RBTreeSortedList.this.size() - 1;
                while (this.nextIndex > index) {
                    this.nextNode = RBTreeSortedList.this.tree.getPre(this.nextNode);
                    --this.nextIndex;
                }
            }
        }

        @Override
        public void add(E o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasNext() {
            return this.nextIndex < RBTreeSortedList.this.size();
        }

        @Override
        public boolean hasPrevious() {
            return this.nextIndex > 0;
        }

        @Override
        public E next() {
            this.checkForComodification();
            if (this.nextIndex == RBTreeSortedList.this.size()) {
                throw new NoSuchElementException();
            }
            this.lastReturned = this.nextNode;
            this.nextNode = RBTreeSortedList.this.tree.getNext(this.nextNode);
            ++this.nextIndex;
            return this.lastReturned.getData();
        }

        @Override
        public int nextIndex() {
            return this.nextIndex;
        }

        @Override
        public E previous() {
            this.checkForComodification();
            if (this.nextIndex == 0) {
                throw new NoSuchElementException();
            }
            this.nextNode = RBTreeSortedList.this.tree.getPre(this.nextNode);
            this.lastReturned = this.nextNode;
            --this.nextIndex;
            return this.lastReturned.getData();
        }

        @Override
        public int previousIndex() {
            return this.nextIndex - 1;
        }

        @Override
        public void remove() {
            this.checkForComodification();
            RBTreeNode last = RBTreeSortedList.this.tree.getPre(this.lastReturned);
            RBTreeSortedList.this.tree.delete(this.lastReturned);
            --this.nextIndex;
            this.lastReturned = last;
            RBTreeSortedList.this.modCount++;
            ++this.expectedModCount;
        }

        @Override
        public void set(E o) {
            if (this.lastReturned == null) {
                throw new IllegalStateException();
            }
            this.checkForComodification();
            this.lastReturned.setData(o);
        }

        final void checkForComodification() {
            if (RBTreeSortedList.this.modCount != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private static final class DefaultComparator<T>
    implements Comparator<T> {
        private DefaultComparator() {
        }

        @Override
        public int compare(T o1, T o2) {
            if (o1 == o2) {
                return 0;
            }
            if (o1 == null) {
                return -1;
            }
            return ((Comparable)o1).compareTo(o2);
        }
    }
}

