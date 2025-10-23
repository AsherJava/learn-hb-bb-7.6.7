/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.zb.scheme.web.vo.ZbCheckParam;

public interface IFormSchemeZbCheckService {
    public String checkZb(ZbCheckParam var1) throws Exception;

    public void checkZb(ZbCheckParam var1, AsyncTaskMonitor var2) throws Exception;
}

