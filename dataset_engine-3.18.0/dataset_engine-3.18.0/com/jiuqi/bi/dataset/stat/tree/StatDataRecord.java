/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.stat.tree;

import com.jiuqi.bi.dataset.stat.tree.StatTreeNode;
import com.jiuqi.bi.dataset.stat.tree.TreeNodeMsValue;
import java.util.ArrayList;
import java.util.List;

class StatDataRecord
implements Cloneable {
    private List<Object> dimColValues;
    private List<TreeNodeMsValue> msValues;

    public StatDataRecord(List<Object> dimColValues, List<TreeNodeMsValue> msValues) {
        this.dimColValues = dimColValues;
        this.msValues = msValues;
    }

    public List<Object> getDimColValues() {
        return this.dimColValues;
    }

    public int getDimColSize() {
        return this.dimColValues.size();
    }

    public int getMsColSize() {
        return this.msValues.size();
    }

    public TreeNodeMsValue getMsValue(int msPos) {
        return this.msValues.get(msPos);
    }

    public Object getDimValue(int dimPos) {
        return this.dimColValues.get(dimPos);
    }

    public Object[] toDatasetRow(StatTreeNode node, int refParentIndex, int unitCodeColumnIndex) {
        int i;
        int dimSize = this.dimColValues.size();
        int msSize = this.msValues.size();
        boolean hasParent = refParentIndex >= 0;
        Object[] row = new Object[dimSize + (hasParent ? 1 : 0) + msSize];
        for (i = 0; i < dimSize; ++i) {
            row[i] = this.dimColValues.get(i);
        }
        if (hasParent) {
            row[dimSize] = node.getDataRow().getString(refParentIndex);
        }
        for (i = 0; i < msSize; ++i) {
            row[i + dimSize + (hasParent ? 1 : 0)] = this.getMsValue(i).getValue(node, this, i, unitCodeColumnIndex);
        }
        return row;
    }

    public StatDataRecord clone() {
        ArrayList<Object> dv = new ArrayList<Object>(this.dimColValues);
        ArrayList<TreeNodeMsValue> mv = new ArrayList<TreeNodeMsValue>();
        for (TreeNodeMsValue v : this.msValues) {
            mv.add(v.clone());
        }
        return new StatDataRecord(dv, mv);
    }

    public String toString() {
        StringBuilder b = new StringBuilder();
        if (this.dimColValues != null) {
            b.append(this.dimColValues.toString());
        }
        if (this.msValues != null) {
            b.append(this.msValues.toString());
        }
        return b.toString();
    }
}

