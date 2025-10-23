/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.zb.scheme.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.zb.scheme.web.vo.GenerateZbParam;
import com.jiuqi.nr.zb.scheme.web.vo.GenerateZbResult;

public interface IFormSchemeZbGenerateService {
    public GenerateZbResult generateZbInfo(GenerateZbParam var1);

    public void generateZbInfo(GenerateZbParam var1, AsyncTaskMonitor var2) throws Exception;

    public String getResult(String var1) throws Exception;
}

