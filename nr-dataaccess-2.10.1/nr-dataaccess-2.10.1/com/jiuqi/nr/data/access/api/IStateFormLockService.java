/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.dataservice.core.dimension.DimensionCombination
 */
package com.jiuqi.nr.data.access.api;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.data.access.api.param.FormLockHistory;
import com.jiuqi.nr.data.access.api.param.LockParam;
import com.jiuqi.nr.data.access.param.FormLockBatchReadWriteResult;
import com.jiuqi.nr.dataservice.core.dimension.DimensionCombination;
import java.util.List;
import java.util.Map;

public interface IStateFormLockService {
    public boolean isEnableFormLock(String var1);

    public boolean isFormLocked(LockParam var1);

    public boolean isFormLockedByUser(LockParam var1);

    public Map<String, String> getLockedFormKeysMap(LockParam var1, boolean var2);

    public String lockForm(LockParam var1);

    public void batchLockForm(LockParam var1, AsyncTaskMonitor var2);

    public List<FormLockBatchReadWriteResult> batchDimension(LockParam var1);

    public List<FormLockHistory> getLockHistory(DimensionCombination var1, String var2, String var3);
}

