/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dafafill.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dafafill.model.DFDAddRowConfirmResult;
import com.jiuqi.nr.dafafill.model.DFDAddRowQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataQueryInfo;
import com.jiuqi.nr.dafafill.model.DataFillDataResult;
import com.jiuqi.nr.dafafill.model.DataFillDataSaveInfo;
import com.jiuqi.nr.dafafill.model.DataFillResult;

public interface IDataFillDataEnvService {
    default public DataFillDataResult query(DataFillDataQueryInfo queryInfo) {
        return this.query(queryInfo, null);
    }

    public DataFillDataResult query(DataFillDataQueryInfo var1, AsyncTaskMonitor var2);

    public DataFillResult save(DataFillDataSaveInfo var1);

    public DFDAddRowConfirmResult floatAddRowConfirm(DFDAddRowQueryInfo var1);
}

