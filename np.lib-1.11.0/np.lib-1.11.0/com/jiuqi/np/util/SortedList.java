/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public final class SortedList
implements List {
    private List items;
    private Comparator comparator;
    private int[] findStub = new int[1];

    public SortedList() {
        this.items = new ArrayList();
    }

    public SortedList(int initialCapacity) {
        this.items = new ArrayList(initialCapacity);
    }

    public SortedList(Comparator comparator) {
        this.items = new ArrayList();
        this.comparator = comparator;
    }

    public SortedList(int initalCapacity, Comparator comparator) {
        this.items = new ArrayList(initalCapacity);
        this.comparator = comparator;
    }

    public int compareObjects(Object o1, Object o2) {
        if (this.comparator != null) {
            return this.comparator.compare(o1, o2);
        }
        if (o1 instanceof Comparable) {
            return ((Comparable)o1).compareTo(o2);
        }
        if (o2 instanceof Comparable) {
            return -((Comparable)o2).compareTo(o1);
        }
        throw new UnsupportedOperationException("\u5bf9\u8c61\u6bd4\u8f83\u65f6\u65e0\u6cd5\u8f6c\u5316\u4e3aComparable\u63a5\u53e3\uff01");
    }

    @Override
    public boolean add(Object o) {
        int[] index = new int[1];
        this.find(o, index);
        this.items.add(index[0], o);
        return true;
    }

    public void add(int index, Object element) {
        this.add(element);
    }

    public void _add(int index, Object element) {
        this.items.add(index, element);
    }

    @Override
    public boolean addAll(Collection c) {
        ArrayList newItems = new ArrayList(c);
        this.reset(newItems);
        return true;
    }

    public boolean addAll(int index, Collection c) {
        return this.addAll(c);
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public boolean contains(Object o) {
        return this.find(o, new int[1]);
    }

    @Override
    public boolean containsAll(Collection c) {
        Iterator itr = c.iterator();
        while (itr.hasNext()) {
            if (this.contains(itr.next())) continue;
            return false;
        }
        return true;
    }

    public Object get(int index) {
        return this.items.get(index);
    }

    @Override
    public int indexOf(Object o) {
        int low = 0;
        int high = this.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int flag = this.compareObjects(o, this.get(mid));
            if (flag == 0) {
                if (mid == low) {
                    return mid;
                }
                high = mid;
                continue;
            }
            if (flag > 0) {
                low = mid + 1;
                continue;
            }
            high = mid - 1;
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return this.items.isEmpty();
    }

    @Override
    public Iterator iterator() {
        return this.items.iterator();
    }

    @Override
    public int lastIndexOf(Object o) {
        int low = 0;
        int high = this.size() - 1;
        while (low <= high) {
            int mid = (low + high + 1) / 2;
            int flag = this.compareObjects(o, this.get(mid));
            if (flag == 0) {
                if (mid == high) {
                    return mid;
                }
                low = mid;
                continue;
            }
            if (flag > 0) {
                low = mid + 1;
                continue;
            }
            high = mid - 1;
        }
        return -1;
    }

    public ListIterator listIterator() {
        return this.items.listIterator();
    }

    public ListIterator listIterator(int index) {
        return this.items.listIterator(index);
    }

    public Object remove(int index) {
        return this.items.remove(index);
    }

    @Override
    public boolean remove(Object o) {
        return this.items.remove(o);
    }

    @Override
    public boolean removeAll(Collection c) {
        boolean ret = false;
        Iterator itr = c.iterator();
        while (itr.hasNext()) {
            if (!this.remove(itr.next())) continue;
            ret = true;
        }
        return ret;
    }

    @Override
    public boolean retainAll(Collection c) {
        SortedList list = new SortedList(c.size(), this.comparator);
        list.addAll(c);
        boolean ret = false;
        for (int i = this.size() - 1; i >= 0; --i) {
            if (list.contains(this.get(i))) continue;
            this.remove(i);
            ret = true;
        }
        return ret;
    }

    public Object set(int index, Object element) {
        if (this.compareObjects(this.get(index), element) == 0) {
            return this.items.set(index, element);
        }
        Object ret = this.items.get(index);
        this.items.remove(index);
        this.add(element);
        return ret;
    }

    @Override
    public int size() {
        return this.items.size();
    }

    public List subList(int fromIndex, int toIndex) {
        SortedList result = new SortedList(this.comparator);
        result.items = this.items.subList(fromIndex, toIndex);
        return result;
    }

    @Override
    public Object[] toArray() {
        return this.items.toArray();
    }

    @Override
    public Object[] toArray(Object[] a) {
        return this.items.toArray(a);
    }

    public Comparator getComparator() {
        return this.comparator;
    }

    public void setComparator(Comparator comparator) {
        this.comparator = comparator;
    }

    public boolean find(Object element, int[] index) {
        int low = 0;
        int high = this.size() - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            int flag = this.compareObjects(element, this.get(mid));
            if (flag == 0) {
                index[0] = mid;
                return true;
            }
            if (flag > 0) {
                low = mid + 1;
                continue;
            }
            high = mid - 1;
        }
        index[0] = low;
        return false;
    }

    public int find(Object element) {
        if (this.find(element, this.findStub)) {
            return this.findStub[0];
        }
        return -1;
    }

    public void reset(List items) {
        if (items == null) {
            throw new NullPointerException("items");
        }
        Collections.sort(items, new Comparator(){

            public int compare(Object o1, Object o2) {
                return SortedList.this.compareObjects(o1, o2);
            }
        });
        this.items = items;
    }

    public static void listMove(List list, int index1, int index2) {
        if (index1 > index2) {
            Object t = list.get(index1);
            for (int i = index1; i > index2; --i) {
                list.set(i, list.get(i - 1));
            }
            list.set(index2, t);
        } else if (index1 < index2) {
            Object t = list.get(index1);
            for (int i = index1; i < index2; ++i) {
                list.set(i, list.get(i + 1));
            }
            list.set(index2, t);
        }
    }

    public static List listIntersectWith(List list1, List list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }
        if (list1.size() > list2.size()) {
            List t = list1;
            list1 = list2;
            list2 = t;
        }
        HashSet st = new HashSet();
        int c = list1.size();
        for (int i = 0; i < c; ++i) {
            st.add(list1.get(i));
        }
        ArrayList result = new ArrayList(list1.size());
        int c2 = list2.size();
        for (int i = 0; i < c2; ++i) {
            Object item = list2.get(i);
            if (!st.contains(item)) continue;
            result.add(item);
        }
        return result;
    }
}

