/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.paramInfo.AccountRollBackParam;
import com.jiuqi.nr.dataentry.paramInfo.RollbackAccountDataResult;

public interface IBatchRollbackAccountDataService {
    public void rollbackAccountData(AccountRollBackParam var1) throws Exception;

    public void asyncRollbackAccountData(AccountRollBackParam var1, AsyncTaskMonitor var2) throws Exception;

    public RollbackAccountDataResult batchRollbackAccountData(AccountRollBackParam var1, AsyncTaskMonitor var2);
}

