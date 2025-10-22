/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.finalaccountsaudit.multcheck.controller;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.finalaccountsaudit.multcheck.common.OneKeyCheckInfo;

public interface IBeforUploadMultCheckController {
    public boolean oneKeyCheck(OneKeyCheckInfo var1, AsyncTaskMonitor var2) throws Exception;
}

