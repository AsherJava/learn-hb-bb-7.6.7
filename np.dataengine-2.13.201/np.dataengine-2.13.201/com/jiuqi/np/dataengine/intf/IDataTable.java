/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataRow;
import com.jiuqi.np.dataengine.intf.IMonitor;
import com.jiuqi.np.dataengine.intf.IReadonlyTable;
import com.jiuqi.np.dataengine.node.IParsedExpression;
import java.util.List;

public interface IDataTable
extends IReadonlyTable {
    public IDataRow appendRow(DimensionValueSet var1) throws IncorrectQueryException;

    public IDataRow newEmptyRow(boolean var1);

    public int deleteRow(DimensionValueSet var1) throws IncorrectQueryException;

    public void deleteByIndex(int var1);

    public void deleteAll();

    public IDataRow revokeRow(DimensionValueSet var1);

    public boolean commitChanges(boolean var1) throws IncorrectQueryException, Exception;

    public boolean commitChanges(boolean var1, boolean var2) throws IncorrectQueryException, Exception;

    public boolean commitChanges(boolean var1, boolean var2, boolean var3) throws IncorrectQueryException, Exception;

    public void setDataChangeMonitor(IMonitor var1);

    public void needCheckDuplicateKeys(boolean var1);

    public void setIgnoreEvent(boolean var1);

    public void setOnlySaveData(boolean var1);

    public void setIgnoreResetCache(boolean var1);

    public void setValidExpression(List<IParsedExpression> var1);
}

