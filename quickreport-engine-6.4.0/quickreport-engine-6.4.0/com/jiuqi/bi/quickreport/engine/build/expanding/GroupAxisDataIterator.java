/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataGroup;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import java.util.Iterator;
import java.util.List;

final class GroupAxisDataIterator
implements Iterator<AxisDataGroup> {
    private List<AxisDataNode> datas;
    private List<ExpandingRegion> regions;
    private int curRegionIdx;
    private int curDataIdx;

    public GroupAxisDataIterator(List<AxisDataNode> datas, List<ExpandingRegion> regions) {
        this.datas = datas;
        this.regions = regions;
        this.curDataIdx = 0;
        this.curRegionIdx = 0;
    }

    @Override
    public boolean hasNext() {
        return this.curRegionIdx < this.regions.size();
    }

    @Override
    public AxisDataGroup next() {
        ExpandingRegion curRegion = this.regions.get(this.curRegionIdx);
        AxisDataGroup group = new AxisDataGroup(curRegion);
        ++this.curRegionIdx;
        this.fetchDatas(group);
        return group;
    }

    private void fetchDatas(AxisDataGroup group) {
        AxisDataNode curData;
        while (this.curDataIdx < this.datas.size() && (curData = this.datas.get(this.curDataIdx)).getRegion() == group.region) {
            group.datas.add(curData);
            ++this.curDataIdx;
        }
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}

