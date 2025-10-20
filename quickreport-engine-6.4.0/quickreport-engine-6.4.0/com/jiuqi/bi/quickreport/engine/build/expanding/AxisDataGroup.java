/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.syntax.DataType
 */
package com.jiuqi.bi.quickreport.engine.build.expanding;

import com.jiuqi.bi.quickreport.engine.area.ExpandingRegion;
import com.jiuqi.bi.quickreport.engine.build.expanding.AxisDataNode;
import com.jiuqi.bi.syntax.DataType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

final class AxisDataGroup {
    public final ExpandingRegion region;
    public final List<AxisDataNode> datas;
    private int[] starts;

    public AxisDataGroup(ExpandingRegion region) {
        this.region = region;
        this.datas = new ArrayList<AxisDataNode>();
    }

    public boolean isEmpty() {
        return this.datas.isEmpty();
    }

    public int getMaxLevel() {
        int level = 0;
        for (AxisDataNode data : this.datas) {
            if (data.getLevel() <= level) continue;
            level = data.getLevel();
        }
        return level;
    }

    public int lastIndexOf(int level, int endIndex) {
        for (int i = endIndex - 1; i >= 0; --i) {
            if (this.datas.get(i).getLevel() != level) continue;
            return i;
        }
        return -1;
    }

    public void setStart(int index, int pos) {
        this.ensureStarts();
        this.starts[index] = pos;
    }

    public int getStart(int index) {
        return this.starts == null ? -1 : this.starts[index];
    }

    private void ensureStarts() {
        if (this.starts == null) {
            this.starts = new int[this.datas.size()];
            Arrays.fill(this.starts, -1);
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append(this.region.getMasterCell().getPosition()).append("=[");
        Iterator<AxisDataNode> i = this.datas.iterator();
        while (i.hasNext()) {
            AxisDataNode data = i.next();
            buffer.append(DataType.formatValue((int)0, (Object)data.getDisplayValue()));
            if (!i.hasNext()) continue;
            buffer.append(',');
        }
        buffer.append(']');
        return buffer.toString();
    }
}

