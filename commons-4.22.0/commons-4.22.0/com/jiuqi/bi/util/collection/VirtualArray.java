/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import com.jiuqi.bi.util.collection.RandomAccessSubList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

public class VirtualArray<E>
implements List<E>,
Collection<E>,
RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = -2149336107553291314L;
    private int size;
    private int pageSize;
    private transient Object[] pages;

    public VirtualArray(int size) {
        this(size, 1024);
    }

    public VirtualArray(int size, int pageSize) {
        this.size = size;
        this.pageSize = pageSize;
        this.pages = new Object[(size + pageSize - 1) / pageSize];
    }

    public VirtualArray(Collection<? extends E> c) {
        this(c, c instanceof VirtualArray ? ((VirtualArray)c).pageSize : 1024);
    }

    public VirtualArray(Collection<? extends E> c, int pageSize) {
        this.size = c.size();
        this.pageSize = pageSize;
        this.pages = new Object[(this.size + pageSize - 1) / pageSize];
        if (c instanceof VirtualArray) {
            VirtualArray other = (VirtualArray)c;
            if (other.pageSize == this.pageSize) {
                for (int i = 0; i < this.pages.length; ++i) {
                    if (other.pages[i] == null) continue;
                    this.pages[i] = new Object[pageSize];
                    System.arraycopy(other.pages[i], 0, this.pages[i], 0, pageSize);
                }
                return;
            }
        }
        int i = 0;
        for (E elem : c) {
            this.set(i++, elem);
        }
    }

    @Override
    public boolean add(E o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        Arrays.fill(this.pages, null);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            for (Object data : this.pages) {
                if (data == null) {
                    return true;
                }
                for (Object item : (Object[])data) {
                    if (item != null) continue;
                    return true;
                }
            }
        } else {
            for (Object data : this.pages) {
                if (data == null) continue;
                for (Object item : (Object[])data) {
                    if (!o.equals(item)) continue;
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object item : c) {
            if (this.contains(item)) continue;
            return false;
        }
        return true;
    }

    @Override
    public E get(int index) {
        Object[] data = (Object[])this.pages[index / this.pageSize];
        return (E)(data == null ? null : data[index % this.pageSize]);
    }

    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < this.pages.length; ++i) {
                Object[] data = (Object[])this.pages[i];
                if (data == null) {
                    return i * this.pageSize;
                }
                for (int j = 0; j < data.length; ++j) {
                    if (data[j] != null) continue;
                    return i * this.pageSize + j;
                }
            }
        } else {
            for (int i = 0; i < this.pages.length; ++i) {
                Object[] data = (Object[])this.pages[i];
                if (data == null) continue;
                for (int j = 0; j < data.length; ++j) {
                    if (!o.equals(data[j])) continue;
                    return i * this.pageSize + j;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        for (Object data : this.pages) {
            if (data == null) continue;
            for (Object elem : (Object[])data) {
                if (elem == null) continue;
                return false;
            }
        }
        return true;
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    public Iterator<E> elemIterator() {
        return new ElemItr();
    }

    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = this.pages.length - 1; i >= 0; --i) {
                Object[] data = (Object[])this.pages[i];
                if (data == null) {
                    return Math.min((i + 1) * this.pageSize - 1, this.size - 1);
                }
                for (int j = data.length - 1; j >= 0; --j) {
                    if (data[j] != null) continue;
                    return i * this.pageSize + j;
                }
            }
        } else {
            for (int i = this.pages.length - 1; i >= 0; --i) {
                Object[] data = (Object[])this.pages[i];
                if (data == null) continue;
                for (int j = data.length - 1; j >= 0; --j) {
                    if (!o.equals(data[j])) continue;
                    return i * this.pageSize + j;
                }
            }
        }
        return -1;
    }

    @Override
    public ListIterator<E> listIterator() {
        return this.listIterator(0);
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return new ListItr(index);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E set(int index, E element) {
        int pageIndex = index / this.pageSize;
        Object[] data = (Object[])this.pages[pageIndex];
        if (data == null) {
            if (element == null) {
                return null;
            }
            this.pages[pageIndex] = data = new Object[this.getPageDataLen(pageIndex)];
        }
        int pos = index % this.pageSize;
        Object oldValue = data[pos];
        data[pos] = element;
        return (E)oldValue;
    }

    private int getPageDataLen(int pageIndex) {
        return pageIndex == this.pages.length - 1 ? this.size - (this.pages.length - 1) * this.pageSize : this.pageSize;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return new RandomAccessSubList(this, fromIndex, toIndex);
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size];
        for (int i = 0; i < this.pages.length; ++i) {
            Object[] data = (Object[])this.pages[i];
            if (data == null) continue;
            System.arraycopy(data, 0, result, i * this.pageSize, data.length);
        }
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        boolean newInstance = false;
        if (a.length < this.size) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), this.size);
            newInstance = true;
        }
        for (int i = 0; i < this.pages.length; ++i) {
            Object[] data = (Object[])this.pages[i];
            if (data == null) {
                if (newInstance) continue;
                Arrays.fill(a, i * this.pageSize, i * this.pageSize + this.getPageDataLen(i), null);
                continue;
            }
            System.arraycopy(data, 0, a, i * this.pageSize, data.length);
        }
        if (a.length > this.size) {
            a[this.size] = null;
        }
        return a;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("[");
        Iterator<E> i = this.iterator();
        boolean hasNext = i.hasNext();
        while (hasNext) {
            E o = i.next();
            buf.append(o == this ? "(this Collection)" : String.valueOf(o));
            hasNext = i.hasNext();
            if (!hasNext) continue;
            buf.append(", ");
        }
        buf.append("]");
        return buf.toString();
    }

    public Object clone() {
        try {
            VirtualArray v = (VirtualArray)super.clone();
            v.pages = new Object[this.pages.length];
            for (int i = 0; i < this.pages.length - 1; ++i) {
                Object[] data = (Object[])this.pages[i];
                if (data == null) continue;
                v.pages[i] = new Object[this.pageSize];
                System.arraycopy(data, 0, v.pages[i], 0, this.pageSize);
            }
            return v;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        for (int i = 0; i < this.pages.length; ++i) {
            if (this.pages[i] == null) continue;
            s.writeInt(i);
            s.writeObject(this.pages[i]);
        }
        s.writeInt(-1);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        this.pages = new Object[(this.size + this.pageSize - 1) / this.pageSize];
        int index = s.readInt();
        while (index != -1) {
            this.pages[index] = s.readObject();
            index = s.readInt();
        }
    }

    public void pack() {
        for (int i = 0; i < this.pages.length; ++i) {
            Object[] data = (Object[])this.pages[i];
            if (data == null) continue;
            boolean empty = true;
            for (Object elem : data) {
                if (elem == null) continue;
                empty = false;
                break;
            }
            if (!empty) continue;
            this.pages[i] = null;
        }
    }

    private final class ElemItr
    implements Iterator<E> {
        private int curPageNum = -1;
        private Object[] curPage = null;
        private int curPos = 0;

        public ElemItr() {
            if (this.nextPage()) {
                this.nextElem();
            }
        }

        private boolean nextPage() {
            while (this.curPageNum < VirtualArray.this.pages.length) {
                ++this.curPageNum;
                Object[] objectArray = this.curPage = this.curPageNum < VirtualArray.this.pages.length ? (Object[])VirtualArray.this.pages[this.curPageNum] : null;
                if (this.curPage == null) continue;
                return true;
            }
            return false;
        }

        private void nextElem() {
            while (this.curPage[this.curPos] == null) {
                ++this.curPos;
                if (this.curPos < VirtualArray.this.pageSize) continue;
                if (!this.nextPage()) break;
                this.curPos = 0;
            }
        }

        @Override
        public boolean hasNext() {
            return this.curPage != null && this.curPos < VirtualArray.this.pageSize;
        }

        @Override
        public E next() {
            Object elem = this.curPage[VirtualArray.this.pageSize];
            this.nextElem();
            return elem;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class ListItr
    extends Itr
    implements ListIterator<E> {
        private int lastPage;
        private int lastPos;

        public ListItr(int index) {
            this.lastPage = -1;
            this.lastPos = -1;
            this.curPage = index / VirtualArray.this.pageSize;
            this.curPos = index % VirtualArray.this.pageSize;
        }

        @Override
        public E next() {
            this.lastPage = this.curPage;
            this.lastPos = this.curPos;
            return super.next();
        }

        @Override
        public void add(E o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean hasPrevious() {
            return this.curPage > 0 || this.curPos > 0;
        }

        @Override
        public int nextIndex() {
            return this.curPage * VirtualArray.this.pageSize + this.curPos;
        }

        @Override
        public E previous() {
            try {
                Object[] data;
                --this.curPos;
                if (this.curPos < 0) {
                    --this.curPage;
                    this.curPos = VirtualArray.this.pageSize - 1;
                }
                Object elem = (data = (Object[])VirtualArray.this.pages[this.curPage]) == null ? null : data[this.curPos];
                this.lastPage = this.curPage;
                this.lastPos = this.curPos;
                return elem;
            }
            catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public int previousIndex() {
            return this.nextIndex() - 1;
        }

        @Override
        public void set(E o) {
            try {
                Object[] data = (Object[])VirtualArray.this.pages[this.lastPage];
                if (data == null) {
                    if (o == null) {
                        return;
                    }
                    ((VirtualArray)VirtualArray.this).pages[this.lastPage] = data = new Object[VirtualArray.this.pageSize];
                }
                data[this.lastPos] = o;
            }
            catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }
    }

    private class Itr
    implements Iterator<E> {
        protected int curPage = 0;
        protected int curPos = 0;

        private Itr() {
        }

        @Override
        public boolean hasNext() {
            return this.curPage * VirtualArray.this.pageSize + this.curPos != VirtualArray.this.size;
        }

        @Override
        public E next() {
            try {
                Object[] data = (Object[])VirtualArray.this.pages[this.curPage];
                Object elem = data == null ? null : data[this.curPos];
                ++this.curPos;
                if (this.curPage == VirtualArray.this.pageSize) {
                    ++this.curPage;
                    this.curPos = 0;
                }
                return elem;
            }
            catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

