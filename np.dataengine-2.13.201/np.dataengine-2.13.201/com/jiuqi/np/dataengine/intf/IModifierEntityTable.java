/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.np.dataengine.intf;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.np.dataengine.exception.IncorrectQueryException;
import com.jiuqi.np.dataengine.intf.IDataTable;
import com.jiuqi.np.dataengine.intf.IEntityRow;
import com.jiuqi.np.dataengine.intf.IEntityTable;

@Deprecated
public interface IModifierEntityTable
extends IDataTable,
IEntityTable {
    public void setOnlyUpdate(boolean var1);

    public IEntityRow appendEntity(String var1) throws IncorrectQueryException;

    public void deleteByEntityKey(String var1) throws IncorrectQueryException;

    @Override
    public IEntityRow revokeRow(DimensionValueSet var1);

    public IEntityRow revokeRow(String var1);

    public void deleteByEntityKey(String var1, boolean var2) throws IncorrectQueryException;
}

