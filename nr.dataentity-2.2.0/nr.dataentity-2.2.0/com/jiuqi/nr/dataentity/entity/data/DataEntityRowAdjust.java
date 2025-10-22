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

public class DataEntityRowAdjust
implements IDataEntityRow {
    private List<AdjustPeriod> adjustPeriod;

    public DataEntityRowAdjust(List<AdjustPeriod> adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    @Override
    public List<AdjustPeriod> getAdjustPeriod() {
        return this.adjustPeriod;
    }

    public void setAdjustPeriod(List<AdjustPeriod> adjustPeriod) {
        this.adjustPeriod = adjustPeriod;
    }

    @Override
    public DataEntityType getType() {
        return DataEntityType.DataEntityAdjust;
    }

    @Override
    @Deprecated
    public List<IEntityRow> getRowList() {
        return null;
    }

    @Override
    @Deprecated
    public IPeriodEntity getPeriodEntity() {
        return null;
    }
}

