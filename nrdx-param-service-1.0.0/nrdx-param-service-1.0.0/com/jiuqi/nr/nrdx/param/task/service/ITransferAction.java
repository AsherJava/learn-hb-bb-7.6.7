/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 *  com.jiuqi.nr.nrdx.adapter.param.common.DepResource
 *  com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext
 */
package com.jiuqi.nr.nrdx.param.task.service;

import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.nrdx.adapter.param.common.DepResource;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import com.jiuqi.nr.nrdx.param.dto.ParamDTO;

public interface ITransferAction<T extends ParamDTO> {
    public void exportModel(T var1, NrdxTransferContext var2, AsyncTaskMonitor var3);

    public void importModel(T var1, NrdxTransferContext var2, AsyncTaskMonitor var3);

    public void preAnalysis(T var1, NrdxTransferContext var2, AsyncTaskMonitor var3);

    public DepResource depResource(T var1, NrdxTransferContext var2, AsyncTaskMonitor var3);
}

