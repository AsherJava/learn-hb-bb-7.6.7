/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.period.modal.IPeriodRow
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.IDimensionRow;
import com.jiuqi.nr.dataentity.entity.IDimensionTable;
import com.jiuqi.nr.dataentity.entity.PeriodDimensionRow;
import com.jiuqi.nr.period.modal.IPeriodRow;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PeriodDimensionTable
implements IDimensionTable {
    private String entityID;
    private List<IPeriodRow> rows;

    public PeriodDimensionTable(String entityID, List<IPeriodRow> rows) {
        this.entityID = entityID;
        this.rows = rows;
    }

    @Override
    public String getDimensionName() {
        return "DATATIME";
    }

    @Override
    public String getDimensionEntityId() {
        return this.entityID;
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
        IPeriodRow current = null;
        int nextIndex = 0;
        List<IPeriodRow> rows = new ArrayList<IPeriodRow>();

        protected DataIterator(List<IPeriodRow> rows) {
            this.rows = rows;
            this.next();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public IDimensionRow next() {
            IPeriodRow result = this.current;
            this.current = this.nextIndex == this.rows.size() ? null : this.rows.get(this.nextIndex++);
            return new PeriodDimensionRow(result);
        }
    }
}

