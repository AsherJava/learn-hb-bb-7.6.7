/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.transfer.engine.ex.TransferException
 *  com.jiuqi.bi.transfer.engine.intf.IExportContext
 *  com.jiuqi.bi.transfer.engine.intf.IImportContext
 *  org.jdom2.Element
 */
package com.jiuqi.nr.param.transfer.definition.spi;

import com.jiuqi.bi.transfer.engine.ex.TransferException;
import com.jiuqi.bi.transfer.engine.intf.IExportContext;
import com.jiuqi.bi.transfer.engine.intf.IImportContext;
import org.jdom2.Element;

public interface TaskTransfer {
    default public String getId() {
        return this.getClass().getName();
    }

    default public void toDocumentTaskExtra(String taskKey, Element element) {
    }

    default public Object loadBusinessTaskExtra(Element element) {
        return null;
    }

    public byte[] exportTaskData(IExportContext var1, String var2) throws TransferException;

    public void importTaskData(IImportContext var1, String var2, byte[] var3) throws TransferException;
}

