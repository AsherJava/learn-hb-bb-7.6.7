/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.nr.period.internal.adapter;

import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nr.period.internal.adapter.IPeriodProvider;
import com.jiuqi.nr.period.modal.IPeriodEntity;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.Date;
import java.util.List;

public interface IPeriodEntityAdapter {
    public boolean isPeriodEntity(String var1);

    public TableModelDefine getPeriodEntityTableModel(String var1);

    public IPeriodProvider getPeriodProvider(String var1);

    public List<IPeriodEntity> getPeriodEntity();

    public List<IPeriodEntity> getPeriodEntityByPeriodType(PeriodType var1);

    public IPeriodEntity getPeriodEntity(String var1);

    public TableModelDefine getPeriodViewByMasterKey(String var1);

    public IPeriodEntity getIPeriodByTableKey(String var1);

    public String getPeriodDimensionName(String var1);

    public String getPeriodDimensionName();

    public List<ColumnModelDefine> getPeriodEntityColumnModel(String var1);

    public List<ColumnModelDefine> getPeriodEntityShowColumnModel(String var1);

    public Date getStartDateByPeriodCode(String var1, String var2);

    public List<String> getPeriodCodeByDataRegion(String var1, String var2, String var3);
}

