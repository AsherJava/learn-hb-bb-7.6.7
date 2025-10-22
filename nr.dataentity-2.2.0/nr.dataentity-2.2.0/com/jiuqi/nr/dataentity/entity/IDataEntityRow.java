/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.datascheme.api.AdjustPeriod
 *  com.jiuqi.nr.entity.engine.intf.IEntityRow
 *  com.jiuqi.nr.period.modal.IPeriodEntity
 */
package com.jiuqi.nr.dataentity.entity;

import com.jiuqi.nr.dataentity.entity.DataEntityType;
import com.jiuqi.nr.datascheme.api.AdjustPeriod;
import com.jiuqi.nr.entity.engine.intf.IEntityRow;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import java.util.List;

public interface IDataEntityRow {
    public DataEntityType getType();

    public List<IEntityRow> getRowList();

    public List<AdjustPeriod> getAdjustPeriod();

    public IPeriodEntity getPeriodEntity();
}

