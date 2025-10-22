/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.finalaccountsaudit.integritycheck.controller;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityCheckInfo;
import com.jiuqi.nr.finalaccountsaudit.integritycheck.common.IntegrityDataInfo;

public interface IIntegrityCheckController {
    public IntegrityDataInfo integrityCheck(IntegrityCheckInfo var1, AsyncTaskMonitor var2) throws Exception;
}

