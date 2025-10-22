/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.concurrent.Daemon
 *  com.jiuqi.np.core.application.NpApplication
 *  com.jiuqi.nr.graph.IGraph
 *  com.jiuqi.nr.graph.IGraphCache
 *  com.jiuqi.nr.graph.util.GraphUtils
 *  jdk.nashorn.internal.ir.debug.ObjectSizeCalculator
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.concurrent.Daemon;
import com.jiuqi.np.core.application.NpApplication;
import com.jiuqi.nr.definition.internal.runtime.dto.GraphGroupDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.GraphInfoDTO;
import com.jiuqi.nr.definition.internal.runtime.service.AbstractNrParamCacheExpireService;
import com.jiuqi.nr.definition.internal.runtime.service.NrParamCacheManagerService;
import com.jiuqi.nr.graph.IGraph;
import com.jiuqi.nr.graph.IGraphCache;
import com.jiuqi.nr.graph.util.GraphUtils;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;
import org.springframework.beans.factory.annotation.Autowired;

public class NrParamCacheExpireService
extends AbstractNrParamCacheExpireService {
    @Autowired
    private NpApplication npApplication;
    private ScheduledFuture<?> schedule;
    private final Queue<GraphInfoDTO> newGraphInfos = new LinkedBlockingQueue<GraphInfoDTO>();

    @Override
    protected void init() {
        if (null == this.schedule) {
            long millis = NrParamCacheManagerService.NrParamCacheConfig.safeTime.toMillis();
            LOGGER.info("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u53c2\u6570\u7f13\u5b58\u5b89\u5168\u65f6\u95f4\u662f{}ms", (Object)millis);
            if (millis > 0L) {
                long delay = millis / 2L;
                delay = delay > 60000L ? delay : 60000L;
                this.schedule = Daemon.getGlobal().atFixedRate(this::obsolete, delay, delay, TimeUnit.MILLISECONDS);
            }
        }
        this.obsolete();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onPut(IGraphCache cache, String key, IGraph graph, IGraph oldGraph) {
        if (GraphUtils.emptyGraph().equals(graph)) {
            return;
        }
        GraphInfoDTO info = (GraphInfoDTO)this.graphInfos.get(key);
        if (null == info) {
            info = this.createGraphInfo(cache, key, graph);
        } else {
            info.setSize(0L);
        }
        if (this.newGraphInfos.isEmpty()) {
            Queue<GraphInfoDTO> queue = this.newGraphInfos;
            synchronized (queue) {
                if (!this.newGraphInfos.isEmpty()) {
                    this.newGraphInfos.add(info);
                    return;
                }
                this.newGraphInfos.add(info);
                this.startCalSize();
            }
        } else {
            this.newGraphInfos.add(info);
        }
    }

    private void startCalSize() {
        block2: {
            try {
                this.npApplication.asyncRun(this::calculateSize);
            }
            catch (Exception e) {
                LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u5f02\u6b65\u8ba1\u7b97\u5f02\u5e38", e);
                if (this.newGraphInfos.isEmpty()) break block2;
                this.startCalSize();
            }
        }
    }

    private void calculateSize() {
        while (!this.newGraphInfos.isEmpty()) {
            GraphInfoDTO info = this.newGraphInfos.peek();
            IGraphCache cache = (IGraphCache)this.caches.get(info.getName());
            IGraph graph = cache.get(info.getKey());
            long size = NrParamCacheExpireService.calculate(graph);
            info.setSize(size);
            this.logGraphCalSize(info);
            this.obsolete();
            this.newGraphInfos.poll();
        }
    }

    private static long calculate(IGraph graph) {
        long size;
        try {
            size = ObjectSizeCalculator.getObjectSize((Object)graph);
        }
        catch (Exception e) {
            try {
                size = ObjectSizeCalculator.getObjectSize((Object)graph);
            }
            catch (Exception error) {
                size = 0x40000000L;
                LOGGER.error("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u8ba1\u7b97\u5f02\u5e38", error);
            }
        }
        return size;
    }

    private synchronized void obsolete() {
        if (!NrParamCacheManagerService.NrParamCacheSettings.enableObsolete) {
            return;
        }
        Collection<GraphGroupDTO> values = this.graphGroups.values();
        long totalSize = this.getTotalSize(values);
        if (totalSize <= NrParamCacheManagerService.NrParamCacheSettings.maxMemSize) {
            return;
        }
        HashSet<String> filter = new HashSet<String>(NrParamCacheManagerService.NrParamCacheSettings.permanent);
        filter.addAll(this.newGraphInfos.stream().map(g -> g.getGroup().getKey()).collect(Collectors.toSet()));
        long now = System.currentTimeMillis();
        List<GraphGroupDTO> sortedByTime = values.stream().filter(v -> !v.isInvalid()).filter(v -> !filter.contains(v.getKey())).filter(v -> NrParamCacheManagerService.NrParamCacheConfig.safeTime.compareTo(Duration.ofMillis(now - v.getUpdateTime())) < 0).sorted(Comparator.comparingLong(GraphGroupDTO::getLastTime)).collect(Collectors.toList());
        if (sortedByTime.isEmpty()) {
            this.logGraphRemove();
            NrParamCacheExpireService.logCacheWarn(totalSize);
            return;
        }
        this.obsolete(totalSize, sortedByTime);
    }

    private void obsolete(long totalSize, List<GraphGroupDTO> sortedByTime) {
        ArrayList<GraphGroupDTO> removed = new ArrayList<GraphGroupDTO>();
        while (!sortedByTime.isEmpty()) {
            int subIndex = sortedByTime.size() / 2 + sortedByTime.size() % 2;
            List<GraphGroupDTO> sortedByCount = sortedByTime.subList(0, subIndex);
            sortedByCount.sort(Comparator.comparingLong(GraphGroupDTO::getCount));
            while (!sortedByCount.isEmpty()) {
                subIndex = sortedByTime.size() / 2 + sortedByTime.size() % 2;
                List<GraphGroupDTO> sortedBySize = sortedByCount.subList(0, subIndex);
                sortedBySize.sort(Comparator.comparingLong(GraphGroupDTO::getSize));
                for (GraphGroupDTO item : sortedBySize) {
                    removed.add(item);
                    long size = item.getSize();
                    Set<GraphInfoDTO> infos = item.getGraphInfos();
                    for (GraphInfoDTO info : infos) {
                        IGraphCache cache = (IGraphCache)this.caches.get(info.getName());
                        cache.remove(info.getKey());
                    }
                    this.logCacheSize(totalSize -= size);
                    if (totalSize > NrParamCacheManagerService.NrParamCacheSettings.maxMemSize) continue;
                    return;
                }
                sortedByCount = sortedByCount.subList(subIndex, sortedByCount.size());
            }
            sortedByTime = sortedByTime.subList(subIndex, sortedByTime.size());
        }
        this.logGraphRemove(removed);
        NrParamCacheExpireService.logCacheWarn(totalSize);
    }

    private long getTotalSize(Collection<GraphGroupDTO> values) {
        long totalSize = 0L;
        for (GraphGroupDTO value : values) {
            totalSize += value.getSize();
        }
        return totalSize;
    }

    private void logGraphCalSize(GraphInfoDTO info) {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u8ba1\u7b97{}[{}]\u7f13\u5b58\u5927\u5c0f\u4e3a{}MB", "NR_FORMULA_CACHE_NAME".equals(info.getName()) ? "\u516c\u5f0f\u65b9\u6848" : "\u62a5\u8868\u65b9\u6848", info.getKey(), info.getSize() / 1024L / 1024L);
        if (NrParamCacheManagerService.NrParamCacheSettings.enableObsolete) {
            this.logCacheSize();
        }
    }

    private void logCacheSize() {
        this.logCacheSize(this.getTotalSize(this.graphGroups.values()));
    }

    private void logCacheSize(long totalSize) {
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u5f53\u524d\u7f13\u5b58\u5360\u7528\u5927\u5c0f\u4e3a{}MB\uff0c\u57df\u503c\u4e3a{}MB", (Object)(totalSize / 1024L / 1024L), (Object)(NrParamCacheManagerService.NrParamCacheSettings.maxMemSize / 1024L / 1024L));
    }

    private static void logCacheWarn(long totalSize) {
        LOGGER.warn("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1a\u7f13\u5b58\u53ef\u7528\u5185\u5b58\u592a\u5c0f\uff0c\u5f53\u524d\u7f13\u5b58\u5360\u7528\u5927\u5c0f\u4e3a{}MB\uff0c\u57df\u503c\u4e3a{}MB\uff0c\u8bf7\u68c0\u67e5 JVM \u5185\u5b58\u914d\u7f6e\u6216\u7cfb\u7edf\u914d\u7f6e-\u62a5\u8868-\u62a5\u8868\u7f13\u5b58-\u4e2d\u7684\u5e38\u9a7b\u7f13\u5b58\u89c4\u5219", (Object)(totalSize / 1024L / 1024L), (Object)(NrParamCacheManagerService.NrParamCacheSettings.maxMemSize / 1024L / 1024L));
    }
}

