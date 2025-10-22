/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.basedata.GRBreathFirstIterator;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupRowFilter;
import java.util.List;

public class GRBreathExpandIterator
extends GRBreathFirstIterator {
    protected IGroupRowFilter filter;

    public GRBreathExpandIterator(IGroupDataRow subTree, IGroupRowFilter filter) {
        super(subTree);
        this.filter = filter;
    }

    public GRBreathExpandIterator(List<IGroupDataRow> tree, IGroupRowFilter filter) {
        super(tree);
        this.filter = filter;
    }

    @Override
    public IGroupDataRow next() {
        this.pollRow = (IGroupDataRow)this.breadthQue.pollFirst();
        if (this.pollRow != null && this.filter.isValidRow(this.pollRow)) {
            List<IGroupDataRow> children = this.pollRow.getChildren();
            children.forEach(child -> this.breadthQue.addLast(child));
        }
        return this.pollRow;
    }
}

