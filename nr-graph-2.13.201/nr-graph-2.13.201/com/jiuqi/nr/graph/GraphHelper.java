/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.graph;

import com.jiuqi.nr.graph.IDataRelation;
import com.jiuqi.nr.graph.IDataWrapper;
import com.jiuqi.nr.graph.IGraphCache;
import com.jiuqi.nr.graph.cache.GraphCache;
import com.jiuqi.nr.graph.cache.GraphCacheDefine;
import com.jiuqi.nr.graph.internal.DataRelation;
import com.jiuqi.nr.graph.internal.DataWrapper;
import com.jiuqi.nr.graph.internal.GraphBuilder;
import com.jiuqi.nr.graph.label.EdgeLabel;
import com.jiuqi.nr.graph.label.NodeLabel;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface GraphHelper {
    public static final String CACHE_LOGGER_NAME = "com.jiuqi.nr.graph.cache";
    public static final Logger CACHE_LOGGER = LoggerFactory.getLogger("com.jiuqi.nr.graph.cache");

    public static GraphBuilder createGraphBuilder(String graphName) {
        return new GraphBuilder(graphName);
    }

    public static IGraphCache createGraphCache(GraphCacheDefine define) {
        return new GraphCache(define);
    }

    public static IGraphCache createGraphCache(GraphCacheDefine define, IGraphCache from) {
        if (from instanceof GraphCache) {
            return new GraphCache(define, (GraphCache)from);
        }
        throw new IllegalArgumentException("\u6682\u65e0\u652f\u6301");
    }

    public static IDataWrapper createDataWrapper(NodeLabel label, String key, Object data) {
        return new DataWrapper(label, key, data);
    }

    public static Collection<IDataWrapper> createDataWrapper(NodeLabel label, Map<String, Object> datas) {
        if (null == datas) {
            return Collections.emptyList();
        }
        return datas.entrySet().stream().map(e -> GraphHelper.createDataWrapper(label, (String)e.getKey(), e.getValue())).collect(Collectors.toList());
    }

    public static IDataRelation createDataRelation(EdgeLabel label, String outKey, String inKey) {
        return new DataRelation(label, outKey, inKey);
    }

    public static Collection<IDataRelation> createDataRelation(EdgeLabel label, Map<String, String> rels) {
        if (null == rels) {
            return Collections.emptyList();
        }
        return rels.entrySet().stream().map(e -> GraphHelper.createDataRelation(label, (String)e.getKey(), (String)e.getValue())).collect(Collectors.toList());
    }
}

