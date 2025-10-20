/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.dataset.MemoryDataSet
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.dataset.MemoryDataSet;
import com.jiuqi.bi.quickreport.writeback.IWritebackContext;
import com.jiuqi.bi.quickreport.writeback.WritebackException;

public interface IWritebackExecutor {
    public void write(IWritebackContext var1, MemoryDataSet<?> var2) throws WritebackException;
}

