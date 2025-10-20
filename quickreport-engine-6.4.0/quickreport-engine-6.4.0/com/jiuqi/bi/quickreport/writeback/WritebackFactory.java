/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.quickreport.writeback;

import com.jiuqi.bi.quickreport.writeback.IWritebackExecutor;
import com.jiuqi.bi.quickreport.writeback.IWritebackFieldProvider;
import com.jiuqi.bi.quickreport.writeback.IWritebackMetaDataProvider;
import com.jiuqi.bi.quickreport.writeback.WritebackException;

public abstract class WritebackFactory {
    public abstract String getType();

    public abstract String getTitle();

    public abstract boolean hasFieldProvider();

    public abstract IWritebackExecutor createExecutor(String var1) throws WritebackException;

    public abstract IWritebackMetaDataProvider crateMetaDataProvider() throws WritebackException;

    public abstract IWritebackFieldProvider createFieldProvider(String var1) throws WritebackException;
}

