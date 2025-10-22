/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.lwtree.query;

public interface IEntityRowCounter {
    public int getCount();

    public int getChildCount(String var1);

    public int getAllChildCount(String var1);

    public boolean hasChildren(String var1);
}

