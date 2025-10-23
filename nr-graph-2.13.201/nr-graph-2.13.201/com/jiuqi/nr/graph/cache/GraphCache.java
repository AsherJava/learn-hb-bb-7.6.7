/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph.cache;

import com.jiuqi.nr.graph.GraphHelper;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCache;
import com.jiuqi.nr.graph.IGraphCacheObserver;
import com.jiuqi.nr.graph.cache.GlobalIndex;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.cache.GraphMap;
import com.jiuqi.nr.graph.cache.IGraphMap;
import com.jiuqi.nr.graph.exception.GraphException;
import com.jiuqi.nr.graph.label.IndexLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.util.StringUtils;

public class GraphCache
implements IGraphCache {
    private final GraphCacheDefine define;
    private volatile IGraphMap map;

    public GraphCache(GraphCacheDefine define) {
        this.define = define;
        this.map = new GraphMap(define);
        for (IGraphCacheObserver observer : this.define.getObservers()) {
            try {
                observer.onInit(this);
            }
            catch (Exception e) {
                GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
            }
        }
    }

    public GraphCache(GraphCacheDefine define, GraphCache from) {
        this.define = define;
        this.map = from.map.clone();
        for (IGraphCacheObserver observer : this.define.getObservers()) {
            try {
                observer.onInit(this);
            }
            catch (Exception e) {
                GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
            }
        }
    }

    @Override
    public String getName() {
        return this.define.getName();
    }

    @Override
    public int size() {
        return this.map.size();
    }

    @Override
    public boolean exists(String key) {
        return this.map.containsKey(key);
    }

    @Override
    public IGraph get(String key) {
        IGraph graph = this.map.get(key);
        for (IGraphCacheObserver observer : this.define.getObservers()) {
            try {
                observer.onGet(this, key, graph);
            }
            catch (Exception e) {
                GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
            }
        }
        return graph;
    }

    private void checkGraph(IGraph value) {
        if (!GraphUtils.emptyGraph().equals(value) && !this.define.getGraphDefine().getLabel().equals(value.getLabel())) {
            throw new GraphException("\u7f13\u5b58\u5b9a\u4e49\u56fe\u7c7b\u578b\u4e0e\u5b9e\u9645\u5b58\u50a8\u56fe\u5b9e\u4f8b\u7c7b\u578b\u4e0d\u4e00\u81f4");
        }
    }

    @Override
    public synchronized void put(String key, IGraph value) {
        this.checkGraph(value);
        IGraphMap clone = this.map.clone();
        IGraph oldValue = clone.put(key, value);
        this.map = clone;
        for (IGraphCacheObserver observer : this.define.getObservers()) {
            try {
                observer.onPut(this, key, value, oldValue);
            }
            catch (Exception e) {
                GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
            }
        }
    }

    @Override
    public synchronized void put(Map<String, IGraph> map) {
        for (Map.Entry<String, IGraph> entry : map.entrySet()) {
            this.checkGraph(entry.getValue());
        }
        HashMap<String, IGraph> oldMap = new HashMap<String, IGraph>();
        IGraphMap clone = this.map.clone();
        for (Map.Entry<String, IGraph> entry : map.entrySet()) {
            IGraph oldValue = clone.put(entry.getKey(), entry.getValue());
            oldMap.put(entry.getKey(), oldValue);
        }
        this.map = clone;
        for (Map.Entry<String, IGraph> entry : map.entrySet()) {
            for (IGraphCacheObserver observer : this.define.getObservers()) {
                try {
                    observer.onPut(this, entry.getKey(), entry.getValue(), (IGraph)oldMap.get(entry.getKey()));
                }
                catch (Exception e) {
                    GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
                }
            }
        }
    }

    @Override
    public synchronized IGraph remove(String key) {
        IGraphMap clone = this.map.clone();
        IGraph value = clone.remove(key);
        this.map = clone;
        for (IGraphCacheObserver observer : this.define.getObservers()) {
            try {
                observer.onRemove(this, key, value);
            }
            catch (Exception e) {
                GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
            }
        }
        return value;
    }

    @Override
    public synchronized Collection<IGraph> remove(Collection<String> keys) {
        ArrayList<IGraph> result = new ArrayList<IGraph>();
        IGraphMap clone = this.map.clone();
        for (String key : keys) {
            IGraph remove = clone.remove(key);
            result.add(remove);
        }
        this.map = clone;
        int index = 0;
        for (String key : keys) {
            for (IGraphCacheObserver observer : this.define.getObservers()) {
                try {
                    observer.onRemove(this, key, (IGraph)result.get(index++));
                }
                catch (Exception e) {
                    GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
                }
            }
        }
        return result;
    }

    @Override
    public synchronized void clear() {
        IGraphMap clone = this.map.clone();
        clone.clear();
        this.map = clone;
        for (IGraphCacheObserver observer : this.define.getObservers()) {
            try {
                observer.onClear(this);
            }
            catch (Exception e) {
                GraphHelper.CACHE_LOGGER.error("\u62a5\u8868\u56fe\u7f13\u5b58\u7684\u89c2\u5bdf\u8005\u5f02\u5e38", e);
            }
        }
    }

    @Override
    public IGraph getByIndex(NodeLabel label, String key, Function<String, IGraph> function) {
        GlobalIndex globalIndex = this.map.getGlobalIndex(label);
        String graphId = (String)globalIndex.get(key);
        return this.getGraph(graphId, function);
    }

    private IGraph getGraph(String graphId, Function<String, IGraph> function) {
        if (!StringUtils.hasLength(graphId)) {
            if (null != function) {
                return function.apply(null);
            }
            return GraphUtils.emptyGraph();
        }
        IGraph graph = this.get(graphId);
        if (null == graph && null != function) {
            graph = function.apply(graphId);
        }
        return graph;
    }

    @Override
    public IGraph getByIndex(IndexLabel label, String key, Function<String, IGraph> function) {
        GlobalIndex globalIndex = this.map.getGlobalIndex(label);
        String graphId = (String)globalIndex.get(key);
        return this.getGraph(graphId, function);
    }
}

