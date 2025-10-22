/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.spi;

import java.util.List;

public interface IDwClearChangeDataListener {
    public String name();

    public void dataClear(String var1, String var2, String var3, String var4);

    public void dataClear(String var1, List<String> var2, String var3, String var4);
}

