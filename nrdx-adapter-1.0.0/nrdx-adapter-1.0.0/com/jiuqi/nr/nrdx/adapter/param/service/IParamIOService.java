/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.np.asynctask.AsyncTaskMonitor
 */
package com.jiuqi.nr.nrdx.adapter.param.service;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.np.asynctask.AsyncTaskMonitor;
import com.jiuqi.nr.nrdx.adapter.dto.IParamVO;
import com.jiuqi.nr.nrdx.adapter.exception.NrdxTransferException;
import com.jiuqi.nr.nrdx.adapter.param.common.NrdxTransferContext;
import java.io.File;

public interface IParamIOService {
    public void importFile(IParamVO var1, AsyncTaskMonitor var2) throws TransferException;

    public void importTaskParam(NrdxTransferContext var1, AsyncTaskMonitor var2) throws NrdxTransferException;

    public File exportFile(NrdxTransferContext var1, AsyncTaskMonitor var2) throws NrdxTransferException;

    public void exportTaskParam(NrdxTransferContext var1, AsyncTaskMonitor var2) throws Exception;
}

