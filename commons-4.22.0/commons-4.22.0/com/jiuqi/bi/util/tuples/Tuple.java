/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.util.tuples;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Tuple
implements Serializable,
Cloneable,
Iterable<Object>,
Comparable<Tuple> {
    private static final long serialVersionUID = 1L;
    private List<Object> elements;

    public Tuple(Object ... elements) {
        this.elements = Arrays.asList(elements);
    }

    public Tuple(Collection<?> elements) {
        this.elements = new ArrayList(elements);
    }

    public int length() {
        return this.elements.size();
    }

    public Object get(int index) {
        return this.elements.get(index);
    }

    public int indexOf(Object elem) {
        return this.elements.indexOf(elem);
    }

    public int lastIndexOf(Object elem) {
        return this.elements.lastIndexOf(elem);
    }

    public boolean contains(Object elem) {
        return this.elements.contains(elem);
    }

    public boolean containsAll(Collection<?> c) {
        return this.elements.containsAll(c);
    }

    public boolean containsAll(Tuple t) {
        return this.elements.containsAll(t.elements);
    }

    public boolean isEmpty() {
        for (Object elem : this.elements) {
            if (elem == null) continue;
            return false;
        }
        return true;
    }

    public int hashCode() {
        return this.elements.hashCode();
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Tuple)) {
            return false;
        }
        Tuple other = (Tuple)obj;
        return this.elements.equals(other.elements);
    }

    public String toString() {
        return this.elements.toString();
    }

    public String toString(String delimeter) {
        StringBuilder buffer = new StringBuilder();
        Iterator<Object> i = this.elements.iterator();
        while (i.hasNext()) {
            buffer.append(i.next());
            if (!i.hasNext()) continue;
            buffer.append(delimeter);
        }
        return buffer.toString();
    }

    public Object clone() {
        Tuple tuple;
        try {
            tuple = (Tuple)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        tuple.elements = new ArrayList<Object>(this.elements);
        return tuple;
    }

    @Override
    public Iterator<Object> iterator() {
        return this.elements.iterator();
    }

    @Override
    public int compareTo(Tuple tuple) {
        Iterator<Object> itr1 = this.iterator();
        Iterator<Object> itr2 = tuple.iterator();
        while (itr1.hasNext() && itr2.hasNext()) {
            Object v2;
            Object v1 = itr1.next();
            int c = v1 == (v2 = itr2.next()) ? 0 : (v1 == null ? -1 : (v2 == null ? 1 : ((Comparable)v1).compareTo(v2)));
            if (c == 0) continue;
            return c;
        }
        int len1 = itr1.hasNext() ? 1 : 0;
        int len2 = itr2.hasNext() ? 1 : 0;
        return len1 - len2;
    }
}

