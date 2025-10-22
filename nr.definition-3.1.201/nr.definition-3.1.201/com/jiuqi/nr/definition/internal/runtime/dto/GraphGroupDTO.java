/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.definition.internal.runtime.dto;

import com.jiuqi.nr.definition.internal.runtime.dto.GraphInfoDTO;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicLong;

public class GraphGroupDTO {
    private final String key;
    private final Set<GraphInfoDTO> graphInfos;
    private final AtomicLong count;
    private final AtomicLong lastTime;
    private final AtomicLong updateTime;

    public GraphGroupDTO(String key) {
        this.key = key;
        this.graphInfos = new CopyOnWriteArraySet<GraphInfoDTO>();
        this.count = new AtomicLong(0L);
        this.lastTime = new AtomicLong(0L);
        this.updateTime = new AtomicLong(0L);
    }

    public void count() {
        this.count.incrementAndGet();
        this.lastTime.set(System.currentTimeMillis());
    }

    public void add(GraphInfoDTO graphInfo) {
        if (this.isInvalid()) {
            this.count.set(1L);
            this.lastTime.set(System.currentTimeMillis());
            this.updateTime.set(System.currentTimeMillis());
        }
        graphInfo.setGroup(this);
        this.graphInfos.add(graphInfo);
    }

    public void remove(GraphInfoDTO graphInfo) {
        graphInfo.setGroup(null);
        this.graphInfos.remove(graphInfo);
        if (this.isInvalid()) {
            this.count.set(0L);
            this.lastTime.set(0L);
            this.updateTime.set(0L);
        }
    }

    public boolean isInvalid() {
        return this.graphInfos.isEmpty();
    }

    public long getSize() {
        long totalSize = 0L;
        for (GraphInfoDTO graphInfo : this.graphInfos) {
            totalSize += graphInfo.getSize();
        }
        return totalSize;
    }

    public String getKey() {
        return this.key;
    }

    public long getCount() {
        return this.count.get();
    }

    public long getLastTime() {
        return this.lastTime.get();
    }

    public long getUpdateTime() {
        return this.updateTime.get();
    }

    public Set<GraphInfoDTO> getGraphInfos() {
        return this.graphInfos;
    }
}

