/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.cache;

import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.cache.GlobalIndex;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;

public interface IGraphMap {
    public int size();

    public boolean containsKey(String var1);

    public IGraph get(String var1);

    public IGraphMap clone();

    public IGraph put(String var1, IGraph var2);

    public IGraph remove(String var1);

    public void clear();

    public GlobalIndex getGlobalIndex(NodeLabel var1);

    public GlobalIndex getGlobalIndex(IndexLabel var1);
}

