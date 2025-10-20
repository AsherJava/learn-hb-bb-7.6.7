/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.page;

import com.jiuqi.budget.page.PageInfo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

public class PageList<T> {
    private PageInfo pageInfo;
    private List<T> rows;

    public PageList(int initialCapacity) {
        this.rows = new ArrayList<T>(initialCapacity);
        this.pageInfo = new PageInfo();
    }

    public PageList() {
        this.pageInfo = new PageInfo();
    }

    public PageList(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public PageList(Collection<? extends T> c) {
        this.rows = new ArrayList<T>(c);
        this.pageInfo = new PageInfo();
    }

    public List<T> getRows() {
        return this.rows;
    }

    public PageInfo getPageInfo() {
        return this.pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public String toString() {
        return "PageList{pageInfo=" + this.pageInfo + ", rows=" + this.rows + '}';
    }

    public int size() {
        if (this.rows == null) {
            return 0;
        }
        return this.rows.size();
    }

    public T get(int i) {
        if (this.rows == null) {
            return null;
        }
        return this.rows.get(i);
    }

    public boolean add(T data) {
        return this.rows.add(data);
    }

    public void replaceAll(UnaryOperator<T> operator) {
        this.rows.replaceAll(operator);
    }

    public void sort(Comparator<? super T> c) {
        this.rows.sort(c);
    }

    public Spliterator<T> spliterator() {
        return this.rows.spliterator();
    }

    public boolean removeIf(Predicate<? super T> filter) {
        return this.rows.removeIf(filter);
    }

    public Stream<T> stream() {
        return this.rows.stream();
    }

    public Stream<T> parallelStream() {
        return this.rows.parallelStream();
    }

    public void forEach(Consumer<? super T> action) {
        this.rows.forEach(action);
    }

    protected void finalize() throws Throwable {
        try {
            if (this.rows != null) {
                this.rows = null;
            }
        }
        finally {
            super.finalize();
        }
    }

    public boolean isEmpty() {
        return this.rows.isEmpty();
    }

    public boolean contains(Object o) {
        return this.rows.contains(o);
    }

    public Iterator<T> iterator() {
        return this.rows.iterator();
    }

    public Object[] toArray() {
        return this.rows.toArray();
    }

    public <T1> T1[] toArray(T1[] a) {
        return this.rows.toArray(a);
    }

    public boolean remove(Object o) {
        return this.rows.remove(o);
    }

    public boolean containsAll(Collection<?> c) {
        return this.rows.containsAll(c);
    }

    public boolean addAll(Collection<? extends T> c) {
        return this.rows.addAll(c);
    }

    public boolean addAll(int index, Collection<? extends T> c) {
        return this.rows.addAll(index, c);
    }

    public boolean removeAll(Collection<?> c) {
        return this.rows.removeAll(c);
    }

    public boolean retainAll(Collection<?> c) {
        return this.rows.retainAll(c);
    }

    public void clear() {
        this.rows.clear();
    }

    public T set(int index, T element) {
        return this.rows.set(index, element);
    }

    public void add(int index, T element) {
        this.rows.add(index, element);
    }

    public T remove(int index) {
        return this.rows.remove(index);
    }

    public int indexOf(Object o) {
        return this.rows.indexOf(o);
    }

    public int lastIndexOf(Object o) {
        return this.rows.lastIndexOf(o);
    }

    public ListIterator<T> listIterator() {
        return this.rows.listIterator();
    }

    public ListIterator<T> listIterator(int index) {
        return this.rows.listIterator(index);
    }

    public List<T> subList(int fromIndex, int toIndex) {
        return this.rows.subList(fromIndex, toIndex);
    }
}

