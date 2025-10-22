/*
 * Decompiled with CFR 0.152.
 */
package com.jiuiqi.nr.unit.treebase.source.basedata;

import com.jiuiqi.nr.unit.treebase.source.basedata.IGRIterator;
import com.jiuiqi.nr.unit.treebase.source.basedata.IGroupDataRow;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class GRBreathFirstIterator
implements IGRIterator {
    protected IGroupDataRow pollRow;
    protected Deque<IGroupDataRow> breadthQue = new ArrayDeque<IGroupDataRow>(0);

    public GRBreathFirstIterator(IGroupDataRow subTree) {
        this.breadthQue.addLast(subTree);
    }

    public GRBreathFirstIterator(List<IGroupDataRow> tree) {
        tree.forEach(child -> this.breadthQue.addLast((IGroupDataRow)child));
    }

    @Override
    public boolean hasNext() {
        return !this.breadthQue.isEmpty();
    }

    @Override
    public IGroupDataRow next() {
        this.pollRow = this.breadthQue.pollFirst();
        if (this.pollRow != null) {
            List<IGroupDataRow> children = this.pollRow.getChildren();
            children.forEach(child -> this.breadthQue.addLast((IGroupDataRow)child));
        }
        return this.pollRow;
    }

    @Override
    public IGroupDataRow peekFirst() {
        return this.breadthQue.peekFirst();
    }
}

