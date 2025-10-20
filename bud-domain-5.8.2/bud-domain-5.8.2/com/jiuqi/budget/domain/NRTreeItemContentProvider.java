/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.budget.domain;

public interface NRTreeItemContentProvider<T, E> {
    default public String[] getIcons(T data) {
        return null;
    }

    public E getData(T var1);

    default public boolean needSelect(T data) {
        return false;
    }

    default public boolean needCheck(T data) {
        return false;
    }
}

