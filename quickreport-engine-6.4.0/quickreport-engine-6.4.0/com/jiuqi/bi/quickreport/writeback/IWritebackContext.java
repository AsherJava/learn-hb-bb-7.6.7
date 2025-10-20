/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.model.WritebackModel;
import com.jiuqi.bi.quickreport.writeback.WritebackException;

public interface IWritebackContext {
    public String getUserId();

    public void raiseError(MemoryDataSet<?> var1, int var2, String var3, String var4, Throwable var5) throws WritebackException;

    public WritebackModel getModel();
}

