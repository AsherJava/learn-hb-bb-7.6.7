/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.va.bill.inc.intf;

import java.util.Map;

public interface BillDataCacheProvider {
    public int getCacheType();

    public Object get(String var1);

    public void put(String var1, Map<String, Object> var2);

    public void clear(String var1);

    public void refresh(String var1);
}

