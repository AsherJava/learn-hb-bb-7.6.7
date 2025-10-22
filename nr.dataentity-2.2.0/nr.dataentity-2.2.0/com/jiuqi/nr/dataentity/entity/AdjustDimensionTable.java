/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.AdjustDimensionRow;
import com.jiuqi.nr.dataentity.entity.IDimensionRow;
import com.jiuqi.nr.dataentity.entity.IDimensionTable;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AdjustDimensionTable
implements IDimensionTable {
    private List<AdjustPeriod> rows;

    public AdjustDimensionTable(List<AdjustPeriod> rows) {
        this.rows = rows;
    }

    @Override
    public String getDimensionName() {
        return "ADJUST";
    }

    @Override
    public String getDimensionEntityId() {
        return "ADJUST";
    }

    @Override
    public List<IDimensionRow> getDatas() {
        ArrayList<IDimensionRow> datas = new ArrayList<IDimensionRow>();
        Iterator<IDimensionRow> iterator = this.iterator();
        while (iterator.hasNext()) {
            datas.add(iterator.next());
        }
        return datas;
    }

    @Override
    public Iterator<IDimensionRow> iterator() {
        return new DataIterator(this.rows);
    }

    class DataIterator
    implements Iterator<IDimensionRow> {
        AdjustPeriod current = null;
        int nextIndex = 0;
        List<AdjustPeriod> rows = new ArrayList<AdjustPeriod>();

        protected DataIterator(List<AdjustPeriod> rows) {
            this.rows = rows;
            this.next();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public IDimensionRow next() {
            AdjustPeriod result = this.current;
            this.current = this.nextIndex == this.rows.size() ? null : this.rows.get(this.nextIndex++);
            return new AdjustDimensionRow(result);
        }
    }
}

