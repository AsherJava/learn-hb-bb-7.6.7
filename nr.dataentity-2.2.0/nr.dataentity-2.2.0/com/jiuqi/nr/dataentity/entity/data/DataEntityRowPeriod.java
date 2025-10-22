/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dataentity.entity.data;

import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.dataentity.entity.IDataEntityRow;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.List;

public class DataEntityRowPeriod
implements IDataEntityRow {
    private IPeriodEntity periodEntity;

    @Override
    public IPeriodEntity getPeriodEntity() {
        return this.periodEntity;
    }

    public void setPeriodEntity(IPeriodEntity periodEntity) {
        this.periodEntity = periodEntity;
    }

    public DataEntityRowPeriod(IPeriodEntity periodEntity) {
        this.periodEntity = periodEntity;
    }

    @Override
    public DataEntityType getType() {
        return DataEntityType.DataEntityPeriod;
    }

    @Override
    @Deprecated
    public List<IEntityRow> getRowList() {
        return null;
    }

    @Override
    @Deprecated
    public List<AdjustPeriod> getAdjustPeriod() {
        return null;
    }
}

