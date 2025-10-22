/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.datacrud.api;

import java.util.List;

public interface IDwClearChangeDataService {
    public void doChangeData(String var1, String var2, String var3, String var4);

    public void doChangeData(String var1, String var2);

    public void doChangeData(String var1, List<String> var2);

    public void doChangeData(String var1, List<String> var2, String var3, String var4);
}

