/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.datawarning.service;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.datawarning.dao.IWarningRow;
import java.util.List;

public interface IDataWarningTable {
    public List<IWarningRow> getAllRows();

    public IWarningRow getRow(int var1);

    public int getRowCount();

    public List<IWarningRow> getRowsByFieldCode(String var1, DimensionValueSet var2);

    public List<IWarningRow> getRows(String var1);
}

