/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCache;

public interface IGraphCacheObserver {
    public void onInit(IGraphCache var1);

    public void onGet(IGraphCache var1, String var2, IGraph var3);

    public void onPut(IGraphCache var1, String var2, IGraph var3, IGraph var4);

    public void onRemove(IGraphCache var1, String var2, IGraph var3);

    public void onClear(IGraphCache var1);
}

