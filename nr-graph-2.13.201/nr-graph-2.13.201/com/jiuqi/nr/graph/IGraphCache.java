/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public interface IGraphCache {
    public String getName();

    public boolean exists(String var1);

    public IGraph get(String var1);

    public int size();

    public void put(String var1, IGraph var2);

    public void put(Map<String, IGraph> var1);

    public IGraph remove(String var1);

    public Collection<IGraph> remove(Collection<String> var1);

    public void clear();

    public IGraph getByIndex(NodeLabel var1, String var2, Function<String, IGraph> var3);

    public IGraph getByIndex(IndexLabel var1, String var2, Function<String, IGraph> var3);
}

