/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import java.util.AbstractList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class SubList<E>
extends AbstractList<E> {
    private List<E> l;
    private int offset;
    private int size;

    public SubList(List<E> list, int fromIndex, int toIndex) {
        if (fromIndex < 0) {
            throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
        }
        if (toIndex > list.size()) {
            throw new IndexOutOfBoundsException("toIndex = " + toIndex);
        }
        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("fromIndex(" + fromIndex + ") > toIndex(" + toIndex + ")");
        }
        this.l = list;
        this.offset = fromIndex;
        this.size = toIndex - fromIndex;
    }

    @Override
    public E set(int index, E element) {
        this.rangeCheck(index);
        return this.l.set(index + this.offset, element);
    }

    @Override
    public E get(int index) {
        this.rangeCheck(index);
        return this.l.get(index + this.offset);
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException();
        }
        this.l.add(index + this.offset, element);
        ++this.size;
        ++this.modCount;
    }

    @Override
    public E remove(int index) {
        this.rangeCheck(index);
        E result = this.l.remove(index + this.offset);
        --this.size;
        ++this.modCount;
        return result;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        this.size -= toIndex - fromIndex;
        ++this.modCount;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return this.addAll(this.size, c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        int cSize = c.size();
        if (cSize == 0) {
            return false;
        }
        this.l.addAll(this.offset + index, c);
        this.size += cSize;
        ++this.modCount;
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return this.listIterator();
    }

    @Override
    public ListIterator<E> listIterator(final int index) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
        }
        return new ListIterator<E>(){
            private ListIterator<E> i;
            {
                this.i = SubList.this.l.listIterator(index + SubList.this.offset);
            }

            @Override
            public boolean hasNext() {
                return this.nextIndex() < SubList.this.size;
            }

            @Override
            public E next() {
                if (this.hasNext()) {
                    return this.i.next();
                }
                throw new NoSuchElementException();
            }

            @Override
            public boolean hasPrevious() {
                return this.previousIndex() >= 0;
            }

            @Override
            public E previous() {
                if (this.hasPrevious()) {
                    return this.i.previous();
                }
                throw new NoSuchElementException();
            }

            @Override
            public int nextIndex() {
                return this.i.nextIndex() - SubList.this.offset;
            }

            @Override
            public int previousIndex() {
                return this.i.previousIndex() - SubList.this.offset;
            }

            @Override
            public void remove() {
                this.i.remove();
                SubList.this.size--;
                SubList.this.modCount++;
            }

            @Override
            public void set(E o) {
                this.i.set(o);
            }

            @Override
            public void add(E o) {
                this.i.add(o);
                SubList.this.size++;
                SubList.this.modCount++;
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new SubList<E>(this, fromIndex, toIndex);
    }

    private void rangeCheck(int index) {
        if (index < 0 || index >= this.size) {
            throw new IndexOutOfBoundsException("Index: " + index + ",Size: " + this.size);
        }
    }
}

