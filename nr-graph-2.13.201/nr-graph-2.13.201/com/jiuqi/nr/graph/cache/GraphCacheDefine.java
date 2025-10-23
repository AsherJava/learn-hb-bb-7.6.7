/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.cache;

import com.jiuqi.nr.graph.IGraphCacheObserver;
import com.jiuqi.nr.graph.define.GraphDefine;
import com.jiuqi.nr.graph.exception.GraphDefineException;
import com.jiuqi.nr.graph.label.ILabel;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public class GraphCacheDefine {
    private final String name;
    private final GraphDefine graphDefine;
    private final List<IGraphCacheObserver> observers;
    private Supplier<Map<ILabel, Map<String, String>>> indexSupplier;
    private Function<String, Map<ILabel, Map<String, String>>> indexRefresher;

    public GraphCacheDefine(GraphDefine graphDefine) {
        this(graphDefine.getLabel().getName(), graphDefine);
    }

    public GraphCacheDefine(String name, GraphDefine graphDefine) {
        this.name = name;
        this.graphDefine = graphDefine;
        this.observers = new ArrayList<IGraphCacheObserver>();
    }

    public void enableGlobalIndex(Supplier<Map<ILabel, Map<String, String>>> indexSupplier) {
        this.enableGlobalIndex(indexSupplier, null);
    }

    public void enableGlobalIndex(Supplier<Map<ILabel, Map<String, String>>> indexSupplier, Function<String, Map<ILabel, Map<String, String>>> indexRefresher) {
        if (null == indexSupplier) {
            throw new GraphDefineException("\u542f\u7528\u5168\u5c40\u7d22\u5f15\u65f6\uff0c\u7d22\u5f15\u521d\u59cb\u5316\u6267\u884c\u5668\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        this.indexSupplier = indexSupplier;
        this.indexRefresher = indexRefresher;
    }

    public boolean isEnableGlobalIndex() {
        return null != this.indexSupplier;
    }

    public String getName() {
        return this.name;
    }

    public GraphDefine getGraphDefine() {
        return this.graphDefine;
    }

    public Supplier<Map<ILabel, Map<String, String>>> getIndexSupplier() {
        return this.indexSupplier;
    }

    public Function<String, Map<ILabel, Map<String, String>>> getIndexRefresher() {
        return this.indexRefresher;
    }

    public void addObserver(IGraphCacheObserver observer) {
        this.observers.add(observer);
    }

    public void removeObserver(IGraphCacheObserver observer) {
        this.observers.remove(observer);
    }

    protected List<IGraphCacheObserver> getObservers() {
        return this.observers;
    }
}

