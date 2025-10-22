/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.jtable.params.base.JtableContext
 */
package com.jiuqi.nr.dataentry.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.dataentry.bean.FormLockParam;
import com.jiuqi.nr.dataentry.bean.ResultObject;
import com.jiuqi.nr.dataentry.readwrite.bean.FormLockBatchReadWriteResult;
import com.jiuqi.nr.jtable.params.base.JtableContext;
import java.util.List;
import java.util.Map;

public interface IFormLockService {
    public boolean isEnableFormLock(String var1);

    public boolean isFormLocked(FormLockParam var1);

    public Map<String, String> getLockedFormKeysMap(FormLockParam var1, boolean var2);

    public Map<String, String> getLockedFormKeysMapByUser(FormLockParam var1, boolean var2);

    public ResultObject lockForm(FormLockParam var1) throws Exception;

    public Boolean queryForceUnLock(FormLockParam var1);

    public void batchLockForm(FormLockParam var1, AsyncTaskMonitor var2);

    public List<FormLockBatchReadWriteResult> batchDimension(JtableContext var1);
}

