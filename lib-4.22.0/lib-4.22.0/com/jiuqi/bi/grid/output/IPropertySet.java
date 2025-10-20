/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.grid.output;

import java.util.Iterator;

public interface IPropertySet {
    public void put(String var1, Object var2);

    public void remove(String var1);

    public void clear();

    public int size();

    public boolean isEmpty();

    public Iterator<IEntry> iterator();

    public static interface IEntry {
        public String getKey();

        public Object getValue();
    }
}

