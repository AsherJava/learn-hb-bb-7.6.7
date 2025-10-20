/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.area;

import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;

class ExpandingRegionIterator
implements Iterator<ExpandingRegion> {
    private Deque<ExpandingRegion> regions = new ArrayDeque<ExpandingRegion>();

    public ExpandingRegionIterator(List<ExpandingRegion> rootRegions) {
        this.pushRegions(rootRegions);
    }

    public ExpandingRegionIterator(ExpandingRegion rootRegion) {
        this.regions.push(rootRegion);
    }

    private void pushRegions(List<ExpandingRegion> subRegions) {
        for (int i = subRegions.size() - 1; i >= 0; --i) {
            ExpandingRegion region = subRegions.get(i);
            this.regions.push(region);
        }
    }

    @Override
    public boolean hasNext() {
        return !this.regions.isEmpty();
    }

    @Override
    public ExpandingRegion next() {
        ExpandingRegion region = this.regions.pop();
        this.pushRegions(region.getSubRegions());
        return region;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

