/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 */
package com.jiuqi.nr.param.transfer.datascheme.spi;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;

public interface DataSchemeTransfer {
    default public String getId() {
        return this.getClass().getName();
    }

    public byte[] exportTaskData(IExportContext var1, String var2) throws TransferException;

    public void importTaskData(IImportContext var1, String var2, byte[] var3) throws TransferException;
}

