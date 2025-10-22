/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.query.service;

public interface IQueryCacheManager {
    public void setItem(String var1, String var2, String var3, Object var4);

    public void setItem(String var1, String var2, Object var3);

    public Object getCache(String var1, String var2, String var3);

    public Object getCache(String var1, String var2);

    public boolean reSetCache(String var1, String var2, String var3);

    public boolean reSetCache(String var1, String var2);

    public void clearCache();
}

