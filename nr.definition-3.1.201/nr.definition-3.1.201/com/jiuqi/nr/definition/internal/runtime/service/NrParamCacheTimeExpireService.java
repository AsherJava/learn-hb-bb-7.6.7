/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.concurrent.Daemon
 *  com.jiuqi.nr.graph.IGraphCache
 */
package com.jiuqi.nr.definition.internal.runtime.service;

import com.jiuqi.bi.concurrent.Daemon;
import com.jiuqi.nr.definition.internal.runtime.dto.GraphGroupDTO;
import com.jiuqi.nr.definition.internal.runtime.dto.GraphInfoDTO;
import com.jiuqi.nr.definition.internal.runtime.service.AbstractNrParamCacheExpireService;
import com.jiuqi.nr.definition.internal.runtime.service.NrParamCacheManagerService;
import com.jiuqi.nr.graph.IGraphCache;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class NrParamCacheTimeExpireService
extends AbstractNrParamCacheExpireService {
    private ScheduledFuture<?> timeExpireScheduled;
    private ScheduledFuture<?> memCheckScheduled;

    @Override
    void init() {
        if (NrParamCacheManagerService.NrParamCacheSettings.enableObsolete) {
            if (null == this.timeExpireScheduled) {
                long expirationTime = NrParamCacheManagerService.NrParamCacheConfig.expirationScheduled.toMillis();
                this.timeExpireScheduled = Daemon.getGlobal().atFixedRate(this::obsolete, expirationTime, expirationTime, TimeUnit.MILLISECONDS);
            }
            if (null == this.memCheckScheduled && NrParamCacheManagerService.NrParamCacheSettings.memThreshold > 0L) {
                long memCheckTime = NrParamCacheManagerService.NrParamCacheConfig.memCheckScheduled.toMillis();
                this.memCheckScheduled = Daemon.getGlobal().atFixedRate(this::memCheck, memCheckTime, memCheckTime, TimeUnit.MILLISECONDS);
            } else if (null != this.memCheckScheduled && NrParamCacheManagerService.NrParamCacheSettings.memThreshold <= 0L) {
                this.memCheckScheduled.cancel(true);
                this.memCheckScheduled = null;
            }
        } else {
            if (null != this.timeExpireScheduled) {
                this.timeExpireScheduled.cancel(true);
                this.timeExpireScheduled = null;
            }
            if (null != this.memCheckScheduled) {
                this.memCheckScheduled.cancel(true);
                this.memCheckScheduled = null;
            }
        }
    }

    private synchronized void memCheck() {
        if (!NrParamCacheManagerService.NrParamCacheSettings.enableObsolete && NrParamCacheManagerService.NrParamCacheSettings.memThreshold <= 0L) {
            return;
        }
        Runtime runtime = Runtime.getRuntime();
        long used = runtime.totalMemory() - runtime.freeMemory();
        long max = runtime.maxMemory();
        this.logJvmMem(max, used);
        if (used < NrParamCacheManagerService.NrParamCacheSettings.memThreshold) {
            return;
        }
        Collection values = this.graphGroups.values();
        HashSet<String> filter = new HashSet<String>(NrParamCacheManagerService.NrParamCacheSettings.permanent);
        List sortedByTime = values.stream().filter(v -> !v.isInvalid()).filter(v -> !filter.contains(v.getKey())).sorted(Comparator.comparingLong(GraphGroupDTO::getLastTime)).collect(Collectors.toList());
        if (sortedByTime.isEmpty()) {
            this.logGraphRemove();
        } else {
            int subIndex = sortedByTime.size() / 2 + sortedByTime.size() % 2;
            List<GraphGroupDTO> sortedByCount = sortedByTime.subList(0, subIndex);
            this.obsolete(sortedByCount);
        }
        this.logGraph();
    }

    private synchronized void obsolete() {
        if (!NrParamCacheManagerService.NrParamCacheSettings.enableObsolete && NrParamCacheManagerService.NrParamCacheSettings.expirationTime > 0L) {
            return;
        }
        Collection values = this.graphGroups.values();
        HashSet<String> filter = new HashSet<String>(NrParamCacheManagerService.NrParamCacheSettings.permanent);
        long now = System.currentTimeMillis();
        List<GraphGroupDTO> sortedByTime = values.stream().filter(v -> !v.isInvalid()).filter(v -> !filter.contains(v.getKey())).filter(v -> now - v.getLastTime() > NrParamCacheManagerService.NrParamCacheSettings.expirationTime).sorted(Comparator.comparingLong(GraphGroupDTO::getLastTime)).collect(Collectors.toList());
        if (sortedByTime.isEmpty()) {
            this.logGraphRemove();
        } else {
            this.obsolete(sortedByTime);
        }
        this.logGraph();
        this.logJvmMem(Runtime.getRuntime().maxMemory(), Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory());
    }

    private void obsolete(List<GraphGroupDTO> sortedByTime) {
        this.logGraphRemove(sortedByTime);
        for (GraphGroupDTO item : sortedByTime) {
            Set<GraphInfoDTO> infos = item.getGraphInfos();
            for (GraphInfoDTO info : infos) {
                IGraphCache cache = (IGraphCache)this.caches.get(info.getName());
                cache.remove(info.getKey());
            }
        }
    }

    private void logJvmMem(long max, long used) {
        if (!LOGGER.isDebugEnabled()) {
            return;
        }
        LOGGER.debug("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1aJVM \u6700\u5927\u5185\u5b58{}MB, \u5df2\u7ecf\u4f7f\u7528\u4e86{}MB", (Object)(max / 1024L / 1024L), (Object)(used / 1024L / 1024L));
    }

    private void logCacheWarn() {
        LOGGER.warn("\u62a5\u8868\u53c2\u6570\u7f13\u5b58\u7ba1\u7406\uff1aJVM \u5806\u5185\u5b58\u4f7f\u7528\u5df2\u8fbe\u4e0a\u9650\uff0c\u4f46\u662f\u6ca1\u6709\u53ef\u4ee5\u79fb\u9664\u7684\u62a5\u8868\u65b9\u6848\u7f13\u5b58\uff1b\u8bf7\u68c0\u67e5 JVM \u5185\u5b58\u914d\u7f6e\u6216\u7cfb\u7edf\u914d\u7f6e-\u62a5\u8868-\u62a5\u8868\u7f13\u5b58-\u4e2d\u7684\u5e38\u9a7b\u7f13\u5b58\u89c4\u5219");
    }
}

