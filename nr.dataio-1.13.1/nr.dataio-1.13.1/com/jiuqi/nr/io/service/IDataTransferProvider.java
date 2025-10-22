/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.nr.io.service;

import com.jiuqi.nr.io.datacheck.param.TransferParam;
import com.jiuqi.nr.io.service.IDataTransfer;

public interface IDataTransferProvider {
    public IDataTransfer getDataTransfer(TransferParam var1);
}

