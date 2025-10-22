/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.dataengine.common.DimensionValueSet
 */
package com.jiuqi.nr.data.estimation.service.dataio;

import com.jiuqi.np.dataengine.common.DimensionValueSet;
import com.jiuqi.nr.data.estimation.service.dataio.CheckPolicy;
import com.jiuqi.nr.data.estimation.service.dataio.ITableDataSet;

public interface IDataRegionTableData
extends ITableDataSet {
    public ITableDataSet getNewTableData();

    public ITableDataSet getUpdateTableData();

    public ITableDataSet getDeleteTableData();

    public boolean isEmpty(CheckPolicy var1);

    public DimensionValueSet getPubColumnValueSet();
}

