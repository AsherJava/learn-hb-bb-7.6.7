/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.data.engine.gather;

import com.jiuqi.nr.data.engine.gather.ShowItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GatherEntityMap {
    private int maxLevel = 0;
    private List<String> gatherEntitys = new ArrayList<String>();
    private Map<String, ShowItem> unitCache;

    public int getMaxLevel() {
        return this.maxLevel;
    }

    public void setMaxLevel(int maxLevel) {
        this.maxLevel = maxLevel;
    }

    public List<String> getGatherEntitys() {
        return this.gatherEntitys;
    }

    public void setGatherEntitys(List<String> gatherEntitys) {
        this.gatherEntitys = gatherEntitys;
    }

    public Map<String, ShowItem> getUnitCache() {
        return this.unitCache;
    }

    public void setUnitCache(Map<String, ShowItem> unitCache) {
        this.unitCache = unitCache;
    }
}

