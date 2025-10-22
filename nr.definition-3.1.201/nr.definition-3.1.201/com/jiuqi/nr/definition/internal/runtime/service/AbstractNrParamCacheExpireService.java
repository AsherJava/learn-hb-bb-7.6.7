/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.core.context.ContextExtension
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.datascheme.common.Consts
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphCache
 *  com.jiuqi.nr.graph.IGraphCacheObserver
 *  com.jiuqi.nr.graph.INode
 *  com.jiuqi.nr.graph.util.GraphUtils
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.np.core.context.ContextExtension;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.datascheme.common.Consts;
import com.jiuqi.nr.definition.facade.FormulaSchemeDefine;
import com.jiuqi.nr.definition.internal.runtime.dto.GraphGroupDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.GraphInfoDTO;
import com.jiuqi.nr.definition.internal.runtime.service.NrFormulaGraphService;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCache;
import com.jiuqi.nr.graph.IGraphCacheObserver;
import com.jiuqi.nr.graph.INode;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.util.StringUtils;

public abstract class AbstractNrParamCacheExpireService
implements IGraphCacheObserver {
    protected final Map<String, IGraphCache> caches = new HashMap<String, IGraphCache>();
    protected final Map<String, GraphGroupDTO> graphGroups = new ConcurrentHashMap<String, GraphGroupDTO>();
    protected final Map<String, GraphInfoDTO> graphInfos = new ConcurrentHashMap<String, GraphInfoDTO>();
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss SSS");
    protected static final Logger LOGGER = Consts.NR_PARAM_GRAPH_LOGGER;
    private static final String NR_PARAM_CONTEXT_EXTENSION = "NR_PARAM_CONTEXT_EXTENSION";
    private static final String CACHE_COUNT_FLAG = "CACHE_COUNT_FLAG";

    abstract void init();

    public void onInit(IGraphCache cache) {
        this.caches.put(cache.getName(), cache);
    }

    public void onGet(IGraphCache cache, String key, IGraph graph) {
        GraphInfoDTO info = this.graphInfos.get(key);
        if (null == info) {
            return;
        }
        ContextExtension extension = NpContextHolder.getContext().getExtension(NR_PARAM_CONTEXT_EXTENSION);
        Object flag = extension.get(CACHE_COUNT_FLAG);
        if (null == flag) {
            info.getGroup().count();
            extension.put(CACHE_COUNT_FLAG, (Serializable)Integer.valueOf(1));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    protected GraphInfoDTO createGraphInfo(IGraphCache cache, String key, IGraph graph) {
        GraphInfoDTO info;
        String groupKey;
        Object formulaScheme;
        if ("NR_FORMULA_CACHE_NAME".equals(cache.getName())) {
            INode node = graph.getNode(NrFormulaGraphService.FORMULA_SCHEME, key);
            formulaScheme = (FormulaSchemeDefine)node.getData(FormulaSchemeDefine.class);
            groupKey = formulaScheme.getFormSchemeKey();
        } else {
            groupKey = key;
        }
        GraphGroupDTO group = this.graphGroups.get(groupKey);
        if (null == group) {
            formulaScheme = this.graphGroups;
            synchronized (formulaScheme) {
                group = this.graphGroups.get(groupKey);
                if (null == group) {
                    group = new GraphGroupDTO(groupKey);
                    this.graphGroups.put(groupKey, group);
                }
            }
        }
        if (null == (info = this.graphInfos.get(key))) {
            Map<String, GraphInfoDTO> map = this.graphInfos;
            synchronized (map) {
                info = this.graphInfos.get(key);
                if (null == info) {
                    info = new GraphInfoDTO(key, cache.getName());
                    group.add(info);
                    this.graphInfos.put(info.getKey(), info);
                }
            }
        }
        return info;
    }

    public void onPut(IGraphCache cache, String key, IGraph graph, IGraph oldGraph) {
        if (GraphUtils.emptyGraph().equals(graph)) {
            return;
        }
        GraphInfoDTO info = this.graphInfos.get(key);
        if (null == info) {
            this.createGraphInfo(cache, key, graph);
        }
    }

    private void remove(GraphInfoDTO info) {
        info.getGroup().remove(info);
        this.graphInfos.remove(info.getKey());
    }

    public void onRemove(IGraphCache cache, String key, IGraph oldGraph) {
        GraphInfoDTO info = this.graphInfos.get(key);
        if (null == info) {
            return;
        }
        this.remove(info);
    }

    public void onClear(IGraphCache cache) {
        for (GraphInfoDTO info : this.graphInfos.values()) {
            if (!cache.getName().equals(info.getName())) continue;
            this.remove(info);
        }
    }

    private static void logGraph(StringBuilder builder, List<GraphGroupDTO> items) {
        for (GraphGroupDTO item : items) {
            String formulas = item.getGraphInfos().stream().map(GraphInfoDTO::getKey).filter(k -> !k.equals(item.getKey())).collect(Collectors.joining(","));
            builder.append("\n").append(item.getKey()).append("\t").append(StringUtils.hasText(formulas) ? formulas : "\u65e0").append("\t").append(FORMATTER.format(Instant.ofEpochMilli(item.getUpdateTime()).atZone(ZoneId.systemDefault()))).append("\t").append(FORMATTER.format(Instant.ofEpochMilli(item.getLastTime()).atZone(ZoneId.systemDefault()))).append("\t").append(item.getCount());
        }
    }

    protected void logGraph() {
        if (!LOGGER.isDebugEnabled()) {
            return;
        }
        StringBuilder builder = new StringBuilder("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u5f53\u524d\u7f13\u5b58\u60c5\u51b5");
        List<GraphGroupDTO> groups = this.graphGroups.values().stream().filter(g -> !g.isInvalid()).collect(Collectors.toList());
        if (groups.isEmpty()) {
            builder.append("\n").append("\u65e0");
        } else {
            builder.append("\n\u62a5\u8868\u65b9\u6848\t\u516c\u5f0f\u65b9\u6848\t\u6dfb\u52a0\u65f6\u95f4\t\u6700\u540e\u8bbf\u95ee\u65f6\u95f4\t\u8bbf\u95ee\u6b21\u6570");
            AbstractNrParamCacheExpireService.logGraph(builder, groups);
        }
        LOGGER.debug(builder.toString());
    }

    protected void logGraphRemove(List<GraphGroupDTO> items) {
        if (!LOGGER.isDebugEnabled()) {
            return;
        }
        StringBuilder builder = new StringBuilder("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u79fb\u9664\u62a5\u8868\u65b9\u6848\n\u62a5\u8868\u65b9\u6848\t\u516c\u5f0f\u65b9\u6848\t\u6dfb\u52a0\u65f6\u95f4\t\u6700\u540e\u8bbf\u95ee\u65f6\u95f4\t\u8bbf\u95ee\u6b21\u6570");
        AbstractNrParamCacheExpireService.logGraph(builder, items);
        LOGGER.debug(builder.toString());
    }

    protected void logGraphRemove() {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u6ca1\u6709\u53ef\u4ee5\u79fb\u9664\u7684\u62a5\u8868\u65b9\u6848\u7f13\u5b58");
    }
}

