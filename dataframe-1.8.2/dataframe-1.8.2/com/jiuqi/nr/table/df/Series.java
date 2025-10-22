/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Series<E>
implements Iterable<E>,
Comparator<E> {
    private String name;
    private String source;
    private List<E> data;
    public static final String DEF_NAME = "__def__";

    public Series() {
        this(new ArrayList());
    }

    public Series(String name) {
        this(new ArrayList(), name);
    }

    public Series(List<E> values) {
        this(values, DEF_NAME);
    }

    public Series(List<E> values, String name) {
        this(values, name, null);
    }

    public Series(List<E> values, String name, String source) {
        this.name = name;
        this.source = source;
        this.data = values;
    }

    public String getName() {
        return this.name;
    }

    public void rename(String name) {
        this.setName(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<E> getValues() {
        return this.data;
    }

    public int size() {
        return this.data.size();
    }

    public void add(int index, E e) {
        this.data.add(index, e);
    }

    public void add(E e) {
        this.add(this.data.size(), e);
    }

    public E get(int index) {
        return this.getValue(index);
    }

    public E getValue(int index) {
        return this.data.get(index);
    }

    public void setValue(int index, E e) {
        this.add(index, e);
    }

    public int index(E e) {
        return this.data.indexOf(e);
    }

    public Series<E> unique() {
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        return this.data.iterator();
    }

    public String toString() {
        return super.toString();
    }

    @Override
    public int compare(E o1, E o2) {
        return 0;
    }
}

