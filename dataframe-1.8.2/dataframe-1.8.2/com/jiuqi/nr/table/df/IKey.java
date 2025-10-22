/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.table.df;

import java.util.Arrays;

public class IKey {
    public static final String SPLIT_SYMBOL = ".";
    private Object[] elements;

    public IKey(int length) {
        this.elements = new Object[length];
    }

    public IKey(Object[] elements) {
        this.elements = elements;
    }

    public IKey(Object key) {
        this.elements = new Object[]{key};
    }

    public static IKey newIKey(Object ... o) {
        return new IKey(o);
    }

    public int length() {
        return this.elements.length;
    }

    public Object getElement(int level) {
        return this.elements[level];
    }

    public Object[] getElements() {
        return this.elements;
    }

    public int hashCode() {
        return Arrays.hashCode(this.elements);
    }

    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String toString() {
        return Arrays.toString(this.elements);
    }
}

