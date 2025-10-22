/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.definition.facade.FieldDefine
 *  com.jiuqi.np.period.PeriodType
 *  com.jiuqi.nvwa.definition.facade.ColumnModelDefine
 *  com.jiuqi.nvwa.definition.facade.TableModelDefine
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.executors.ExecutorContext;
import com.jiuqi.np.definition.facade.FieldDefine;
import com.jiuqi.np.period.PeriodType;
import com.jiuqi.nvwa.definition.facade.ColumnModelDefine;
import com.jiuqi.nvwa.definition.facade.TableModelDefine;
import java.util.List;

public interface IColumnModelFinder {
    public ColumnModelDefine findColumnModelDefine(ExecutorContext var1, String var2) throws Exception;

    public ColumnModelDefine findColumnModelDefine(ExecutorContext var1, String var2, int var3) throws Exception;

    public ColumnModelDefine findColumnModelDefine(ExecutorContext var1, String var2, String var3) throws Exception;

    public ColumnModelDefine findColumnModelDefine(FieldDefine var1) throws Exception;

    public FieldDefine findFieldDefine(ColumnModelDefine var1) throws Exception;

    default public FieldDefine findFieldDefineByColumnId(String columnId) {
        return null;
    }

    public List<ColumnModelDefine> getAllColumnModelsByTableKey(ExecutorContext var1, String var2) throws Exception;

    public TableModelDefine getTableModelByTableKey(ExecutorContext var1, String var2) throws Exception;

    default public PeriodType getPeriodType(TableModelDefine tableModelDefine) {
        return null;
    }
}

