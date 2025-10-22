/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.EntityDimensionRow;
import com.jiuqi.nr.dataentity.entity.IDimensionRow;
import com.jiuqi.nr.dataentity.entity.IDimensionTable;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class EntityDimensionTable
implements IDimensionTable {
    private String dimeneionName;
    private String entityID;
    private List<IEntityRow> rows;

    public EntityDimensionTable(String entityID, String dimensionName, List<IEntityRow> rows) {
        this.entityID = entityID;
        this.dimeneionName = dimensionName;
        this.rows = rows;
    }

    @Override
    public String getDimensionName() {
        return this.dimeneionName;
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
        IEntityRow current = null;
        int nextIndex = 0;
        List<IEntityRow> rows = new ArrayList<IEntityRow>();

        protected DataIterator(List<IEntityRow> rows) {
            this.rows = rows;
            this.next();
        }

        @Override
        public boolean hasNext() {
            return this.current != null;
        }

        @Override
        public IDimensionRow next() {
            IEntityRow result = this.current;
            this.current = this.nextIndex == this.rows.size() ? null : this.rows.get(this.nextIndex++);
            return new EntityDimensionRow(result);
        }
    }
}

