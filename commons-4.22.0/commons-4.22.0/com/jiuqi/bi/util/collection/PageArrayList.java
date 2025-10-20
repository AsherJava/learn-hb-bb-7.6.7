/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.collection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class PageArrayList<E>
extends AbstractList<E>
implements List<E>,
RandomAccess,
Cloneable,
Serializable {
    private static final long serialVersionUID = 2002298891939778218L;
    private static final int DEFAULT_PAGESIZE = 4096;
    private transient List<E[]> pages;
    private int size;
    private final int pageCapacity;

    public PageArrayList() {
        this(4096);
    }

    public PageArrayList(int pageCapacity) {
        this(pageCapacity, pageCapacity * 4);
    }

    public PageArrayList(int pageSize, int initCapacity) {
        if (pageSize < 0) {
            throw new IllegalArgumentException("\u975e\u6cd5\u7684\u6570\u636e\u9875\u5927\u5c0f\uff1a" + pageSize);
        }
        if (initCapacity < 0) {
            throw new IllegalArgumentException("\u975e\u6cd5\u7684\u521d\u59cb\u5316\u5bb9\u91cf\uff1a" + initCapacity);
        }
        this.pageCapacity = pageSize;
        this.size = 0;
        int pageCount = (initCapacity + pageSize - 1) / pageSize;
        this.pages = new ArrayList<E[]>(pageCount);
        for (int i = 0; i < pageCount; ++i) {
            this.pages.add(this.allocPage());
        }
    }

    public PageArrayList(Collection<? extends E> c) {
        this(4096, c.size());
        this.addAll(c);
    }

    public void trimToSize() {
        if (this.size == 0) {
            this.pages.clear();
        } else {
            int lastPage = (this.size - 1) / this.pageCapacity;
            for (int i = this.pages.size() - 1; i > lastPage; --i) {
                this.pages.remove(i);
            }
        }
    }

    public void ensureCapacity(int miniCapacity) {
        int pageCount = (miniCapacity + this.pageCapacity - 1) / this.pageCapacity;
        while (this.pages.size() < pageCount) {
            this.pages.add(this.allocPage());
        }
    }

    private E[] allocPage() {
        return new Object[this.pageCapacity];
    }

    @Override
    public int size() {
        return this.size;
    }

    public void setSize(int size) {
        if (this.size == size) {
            return;
        }
        if (this.size < size) {
            this.ensureCapacity(size);
            this.size = size;
            return;
        }
        int lastPage = (size - 1) / this.pageCapacity;
        int lastPos = (size - 1) % this.pageCapacity;
        this.pages.subList(lastPage + 1, this.pages.size()).clear();
        Object[] data = this.pages.get(lastPage);
        Arrays.fill(data, lastPos + 1, data.length, null);
        this.size = size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public boolean contains(Object elem) {
        return this.indexOf(elem) >= 0;
    }

    @Override
    public int indexOf(Object elem) {
        if (this.size == 0) {
            return -1;
        }
        int lastPage = (this.size - 1) / this.pageCapacity;
        if (elem == null) {
            int i;
            E[] data;
            for (int p = 0; p < lastPage; ++p) {
                data = this.pages.get(p);
                for (i = 0; i < this.pageCapacity; ++i) {
                    if (data[i] != null) continue;
                    return p * this.pageCapacity + i;
                }
            }
            int lastPos = (this.size - 1) % this.pageCapacity;
            data = this.pages.get(lastPage);
            for (i = 0; i <= lastPos; ++i) {
                if (data[i] != null) continue;
                return lastPage * this.pageCapacity + i;
            }
        } else {
            int i;
            E[] data;
            for (int p = 0; p < lastPage; ++p) {
                data = this.pages.get(p);
                for (i = 0; i < this.pageCapacity; ++i) {
                    if (!elem.equals(data[i])) continue;
                    return p * this.pageCapacity + i;
                }
            }
            int lastPos = (this.size - 1) % this.pageCapacity;
            data = this.pages.get(lastPage);
            for (i = 0; i <= lastPos; ++i) {
                if (!elem.equals(data[i])) continue;
                return lastPage * this.pageCapacity + i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object elem) {
        if (this.size == 0) {
            return -1;
        }
        int lastPage = (this.size - 1) / this.pageCapacity;
        int lastPos = (this.size - 1) % this.pageCapacity;
        if (elem == null) {
            E[] data = this.pages.get(lastPage);
            for (int i = lastPos; i >= 0; --i) {
                if (data[i] != null) continue;
                return lastPage * this.pageCapacity + i;
            }
            for (int p = lastPage - 1; p >= 0; --p) {
                data = this.pages.get(p);
                for (int i = this.pageCapacity - 1; i >= 0; --i) {
                    if (data[i] != null) continue;
                    return p * this.pageCapacity + i;
                }
            }
        } else {
            E[] data = this.pages.get(lastPage);
            for (int i = lastPos; i >= 0; --i) {
                if (!elem.equals(data[i])) continue;
                return lastPage * this.pageCapacity + i;
            }
            for (int p = lastPage - 1; p >= 0; --p) {
                data = this.pages.get(p);
                for (int i = this.pageCapacity - 1; i >= 0; --i) {
                    if (!elem.equals(data[i])) continue;
                    return p * this.pageCapacity + i;
                }
            }
        }
        return -1;
    }

    public Object clone() {
        try {
            PageArrayList v = (PageArrayList)super.clone();
            v.pages = new ArrayList<E[]>(this.pages.size());
            for (E[] data : this.pages) {
                E[] newData = this.allocPage();
                System.arraycopy(data, 0, newData, 0, this.pageCapacity);
                v.pages.add(newData);
            }
            return v;
        }
        catch (CloneNotSupportedException e) {
            throw new InternalError(e.toString());
        }
    }

    @Override
    public Object[] toArray() {
        Object[] result = new Object[this.size];
        int lastPage = (this.size - 1) / this.pageCapacity;
        int lastPos = (this.size - 1) % this.pageCapacity;
        int cur = 0;
        for (int i = 0; i < lastPage; ++i) {
            E[] data = this.pages.get(i);
            System.arraycopy(data, 0, result, cur, this.pageCapacity);
            cur += this.pageCapacity;
        }
        E[] data = this.pages.get(lastPage);
        System.arraycopy(data, 0, result, cur, lastPos + 1);
        return result;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < this.size) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), this.size);
        }
        int lastPage = (this.size - 1) / this.pageCapacity;
        int lastPos = (this.size - 1) % this.pageCapacity;
        int cur = 0;
        for (int i = 0; i < lastPage; ++i) {
            E[] data = this.pages.get(i);
            System.arraycopy(data, 0, a, cur, this.pageCapacity);
            cur += this.pageCapacity;
        }
        E[] data = this.pages.get(lastPage);
        System.arraycopy(data, 0, a, cur, lastPos + 1);
        if (a.length > this.size) {
            a[this.size] = null;
        }
        return a;
    }

    @Override
    public E get(int index) {
        return this.pages.get(index / this.pageCapacity)[index % this.pageCapacity];
    }

    @Override
    public E set(int index, E element) {
        E[] data = this.pages.get(index / this.pageCapacity);
        int p = index % this.pageCapacity;
        E oldValue = data[p];
        data[p] = element;
        return oldValue;
    }

    @Override
    public boolean add(E element) {
        this.ensureCapacity(this.size + 1);
        E[] data = this.pages.get(this.size / this.pageCapacity);
        data[this.size % this.pageCapacity] = element;
        ++this.size;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > this.size) {
            throw new IndexOutOfBoundsException("\u7d22\u5f15\uff1a" + index + "\uff1b\u8303\u56f4\uff1a" + this.size);
        }
        if (index == this.size) {
            this.add(element);
            return;
        }
        this.ensureCapacity(this.size + 1);
        this.moveAfter(index, 1);
        this.pages.get((int)(index / this.pageCapacity))[index % this.pageCapacity] = element;
        ++this.size;
    }

    private void moveAfter(int start, int offset) {
        int startPage = start / this.pageCapacity;
        int startPos = start % this.pageCapacity;
        int lastPage = (this.size - 1) / this.pageCapacity;
        if (startPos + offset <= this.pageCapacity) {
            E[] curData = this.pages.get(startPage);
            this.backwardPage(startPage + 1, lastPage, offset);
            if (startPage < this.pages.size() - 1) {
                E[] nextData = this.pages.get(startPage + 1);
                System.arraycopy(curData, this.pageCapacity - offset, nextData, 0, offset);
            }
            System.arraycopy(curData, startPos, curData, startPos + offset, this.pageCapacity - startPos - offset);
        } else {
            int delta1 = this.pageCapacity - startPos;
            int delta2 = (offset - delta1) % this.pageCapacity;
            this.backwardPage(startPage + 1, lastPage, delta1);
            E[] curData = this.pages.get(startPage);
            E[] nextData = this.pages.get(startPage + 1);
            System.arraycopy(curData, startPos, nextData, 0, delta1);
            this.backwardPage(startPage + 1, Math.min(lastPage + 1, this.pages.size() - 1), delta2);
            for (int deltaPage = (offset - delta1) / this.pageCapacity; deltaPage > 0; --deltaPage) {
                this.pages.add(startPage + 1, this.allocPage());
            }
        }
    }

    @Override
    public E remove(int index) {
        int pageIndex = index / this.pageCapacity;
        int pos = index % this.pageCapacity;
        E[] data = this.pages.get(pageIndex);
        E oldValue = data[pos];
        System.arraycopy(data, pos + 1, data, pos, this.pageCapacity - pos - 1);
        E[] preData = data;
        int lastPage = (this.size - 1) / this.pageCapacity;
        for (int i = pageIndex + 1; i <= lastPage; ++i) {
            data = this.pages.get(i);
            preData[this.pageCapacity - 1] = data[0];
            preData = data;
            System.arraycopy(data, 1, data, 0, this.pageCapacity - 1);
        }
        data[this.pageCapacity - 1] = null;
        --this.size;
        return oldValue;
    }

    @Override
    public boolean remove(Object element) {
        int index = this.indexOf(element);
        if (index == -1) {
            return false;
        }
        this.remove(index);
        return true;
    }

    @Override
    public void clear() {
        if (this.size > 0) {
            int lastPage = (this.size - 1) / this.pageCapacity;
            for (int i = 0; i <= lastPage; ++i) {
                Object[] data = this.pages.get(i);
                Arrays.fill(data, null);
            }
        }
        this.size = 0;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        this.ensureCapacity(this.size + c.size());
        this.fill(this.size, c);
        this.size += c.size();
        return true;
    }

    public void fill(int start, Collection<? extends E> c) {
        int curPage = start / this.pageCapacity;
        int curPos = start % this.pageCapacity;
        for (E elem : c) {
            this.pages.get((int)curPage)[curPos] = elem;
            if (++curPos != this.pageCapacity) continue;
            curPos = 0;
            ++curPage;
        }
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        this.ensureCapacity(this.size + c.size());
        this.moveAfter(index, c.size());
        this.fill(index, c);
        this.size += c.size();
        return true;
    }

    @Override
    protected void removeRange(int fromIndex, int toIndex) {
        int delta = toIndex - fromIndex;
        if (delta <= 0) {
            return;
        }
        int fromPage = fromIndex / this.pageCapacity;
        int fromPos = fromIndex % this.pageCapacity;
        int toPage = toIndex / this.pageCapacity;
        int toPos = toIndex % this.pageCapacity;
        int lastPage = (this.size - 1) / this.pageCapacity;
        if (toIndex == this.size) {
            if (fromPage + 1 < toPage) {
                this.pages.subList(fromPage + 1, toPage).clear();
            }
            Object[] data = this.pages.get(fromPage);
            Arrays.fill(data, fromPos, this.pageCapacity, null);
        } else if (fromPage == toPage) {
            E[] data = this.pages.get(fromPage);
            System.arraycopy(data, toPos, data, fromPos, this.pageCapacity - toPos);
            this.forwardPage(fromPage, lastPage, delta);
        } else {
            if (fromPage + 1 < toPage) {
                this.pages.subList(fromPage + 1, toPage).clear();
                delta -= (toPage - fromPage - 1) * this.pageCapacity;
                lastPage -= toPage - fromPage - 1;
                toPage = fromPage + 1;
            }
            int extraSize = this.pageCapacity - toPos;
            int needSize = this.pageCapacity - fromPos;
            E[] extraData = this.pages.remove(toPage);
            --lastPage;
            E[] data = this.pages.get(fromPage);
            System.arraycopy(extraData, toPos, data, fromPos, Math.min(needSize, extraSize));
            if (needSize > extraSize) {
                delta = needSize - extraSize;
                this.forwardPage(fromPage, lastPage, delta);
            } else if (needSize < extraSize) {
                this.ensureCapacity(this.size - (toIndex - fromIndex));
                delta = extraSize - needSize;
                this.backwardPage(toPage, lastPage, delta);
                data = this.pages.get(toPage);
                System.arraycopy(extraData, this.pageCapacity - delta, data, 0, delta);
            }
        }
        this.size -= toIndex - fromIndex;
    }

    private void forwardPage(int startPage, int lastPage, int elemNum) {
        Object[] data = this.pages.get(startPage);
        for (int i = startPage + 1; i <= lastPage; ++i) {
            E[] curData = this.pages.get(i);
            System.arraycopy(curData, 0, data, this.pageCapacity - elemNum, elemNum);
            System.arraycopy(curData, elemNum, curData, 0, this.pageCapacity - elemNum);
            data = curData;
        }
        Arrays.fill(data, this.pageCapacity - elemNum, this.pageCapacity, null);
    }

    private void backwardPage(int startPage, int lastPage, int elemNum) {
        E[] nextData = lastPage < this.pages.size() - 1 ? this.pages.get(lastPage + 1) : null;
        for (int i = lastPage; i >= startPage; --i) {
            E[] curData = this.pages.get(i);
            if (nextData != null) {
                System.arraycopy(curData, this.pageCapacity - elemNum, nextData, 0, elemNum);
            }
            System.arraycopy(curData, 0, curData, elemNum, this.pageCapacity - elemNum);
            nextData = curData;
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        int pageCount = (this.size + this.pageCapacity - 1) / this.pageCapacity;
        s.writeInt(pageCount);
        for (int i = 0; i < pageCount; ++i) {
            s.writeObject(this.pages.get(i));
        }
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        int pageCount = s.readInt();
        this.pages = new ArrayList<E[]>(pageCount);
        for (int i = 0; i < pageCount; ++i) {
            Object[] data = (Object[])s.readObject();
            this.pages.add(data);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Itr();
    }

    public int pageSize() {
        return (this.size + this.pageCapacity - 1) / this.pageCapacity;
    }

    public List<E> getPage(int pageIndex) {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        int pageSize = this.pageSize();
        E[] page = this.pages.get(pageIndex);
        int len = pageIndex >= pageSize - 1 ? this.size - this.pageCapacity * (pageSize - 1) : this.pageCapacity;
        return new PageList<E>(page, len);
    }

    public Iterator<List<E>> pageIterator() {
        return new PageItr();
    }

    private static final class PageList<E>
    extends AbstractList<E>
    implements RandomAccess {
        private final E[] elements;
        private final int size;

        public PageList(E[] elements, int size) {
            this.elements = Objects.requireNonNull(elements);
            this.size = size;
        }

        @Override
        public int size() {
            return this.size;
        }

        @Override
        public E get(int index) {
            return this.elements[index];
        }

        @Override
        public E set(int index, E element) {
            E oldValue = this.elements[index];
            this.elements[index] = element;
            return oldValue;
        }

        @Override
        public int indexOf(Object o) {
            if (o == null) {
                for (int i = 0; i < this.size; ++i) {
                    if (this.elements[i] != null) continue;
                    return i;
                }
            } else {
                for (int i = 0; i < this.size; ++i) {
                    if (!o.equals(this.elements[i])) continue;
                    return i;
                }
            }
            return -1;
        }

        @Override
        public boolean contains(Object o) {
            return this.indexOf(o) >= 0;
        }

        @Override
        public Object[] toArray() {
            return Arrays.copyOf(this.elements, this.size, this.elements.getClass());
        }

        @Override
        public <T> T[] toArray(T[] a) {
            if (a.length < this.size) {
                return Arrays.copyOf(this.elements, this.size, this.elements.getClass());
            }
            System.arraycopy(this.elements, 0, a, 0, this.size);
            if (a.length > this.size) {
                this.elements[this.size] = null;
            }
            return a;
        }

        @Override
        public Spliterator<E> spliterator() {
            return Spliterators.spliterator(this.elements, 0, this.size, 16);
        }

        @Override
        public void forEach(Consumer<? super E> action) {
            Objects.requireNonNull(action);
            for (int i = 0; i < this.size; ++i) {
                action.accept(this.elements[i]);
            }
        }

        @Override
        public void replaceAll(UnaryOperator<E> operator) {
            Objects.requireNonNull(operator);
            for (int i = 0; i < this.size; ++i) {
                this.elements[i] = operator.apply(this.elements[i]);
            }
        }

        @Override
        public void sort(Comparator<? super E> c) {
            Arrays.sort(this.elements, 0, this.size, c);
        }
    }

    private final class PageItr
    implements Iterator<List<E>> {
        private final int pageSize;
        private int pageIndex;

        private PageItr() {
            this.pageSize = PageArrayList.this.pageSize();
            this.pageIndex = 0;
        }

        @Override
        public boolean hasNext() {
            return this.pageIndex < this.pageSize;
        }

        @Override
        public List<E> next() {
            Object[] page = (Object[])PageArrayList.this.pages.get(this.pageIndex);
            int len = this.pageIndex >= this.pageSize - 1 ? PageArrayList.this.size - PageArrayList.this.pageCapacity * (this.pageSize - 1) : PageArrayList.this.pageCapacity;
            ++this.pageIndex;
            return new PageList<Object>(page, len);
        }
    }

    private final class Itr
    implements Iterator<E> {
        private int curPage = 0;
        private int curPos = 0;
        private int lastRet = -1;

        private Itr() {
        }

        @Override
        public boolean hasNext() {
            return this.curPage * PageArrayList.this.pageCapacity + this.curPos != PageArrayList.this.size;
        }

        @Override
        public E next() {
            try {
                Object next = ((Object[])PageArrayList.this.pages.get(this.curPage))[this.curPos];
                this.lastRet = this.curPage * PageArrayList.this.pageCapacity + this.curPos;
                ++this.curPos;
                if (this.curPos == PageArrayList.this.pageCapacity) {
                    this.curPos = 0;
                    ++this.curPage;
                }
                return next;
            }
            catch (IndexOutOfBoundsException e) {
                throw new NoSuchElementException();
            }
        }

        @Override
        public void remove() {
            if (this.lastRet == -1) {
                throw new IllegalStateException();
            }
            try {
                PageArrayList.this.remove(this.lastRet);
                this.curPage = this.lastRet / PageArrayList.this.pageCapacity;
                this.curPos = this.lastRet % PageArrayList.this.pageCapacity;
                this.lastRet = -1;
            }
            catch (IndexOutOfBoundsException e) {
                throw new ConcurrentModificationException();
            }
        }
    }
}

