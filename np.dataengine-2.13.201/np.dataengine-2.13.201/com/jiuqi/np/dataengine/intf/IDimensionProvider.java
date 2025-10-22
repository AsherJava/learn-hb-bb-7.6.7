/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.definition.provider.DimensionTable
 *  com.jiuqi.np.period.PeriodWrapper
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.dataengine.update.UpdateDataTable;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.definition.provider.DimensionTable;
import com.jiuqi.np.period.PeriodWrapper;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;

public interface IDimensionProvider {
    public String getFieldDimensionName(ExecutorContext var1, FieldDefine var2);

    public String getFieldDimensionName(ExecutorContext var1, ColumnModelDefine var2);

    public String getDimensionNameByEntityId(String var1);

    public String getDimensionNameByEntityTableCode(ExecutorContext var1, String var2);

    public String getEntityIdByEntityTableCode(ExecutorContext var1, String var2);

    public String getDimensionTableName(ExecutorContext var1, String var2);

    public DimensionTable getDimensionTableByEntityId(ExecutorContext var1, String var2, PeriodWrapper var3, Object var4);

    default public DimensionTable getDimensionTableByEntityId(ExecutorContext executorContext, String entityId, PeriodWrapper period, Object dimValue, String linkAlias) {
        return this.getDimensionTableByEntityId(executorContext, entityId, period, dimValue);
    }

    public void writeDimensionTable(ExecutorContext var1, UpdateDataTable var2, PeriodWrapper var3);

    default public String getCaliberDimensionName(ExecutorContext executorContext) {
        return null;
    }
}

