/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.dataset.storage;

import com.jiuqi.bi.dataset.storage.DataSetStorageException;
import com.jiuqi.bi.dataset.storage.IDataSetStorageProvider;

public abstract class AbstractUserDataSetStorageProviderFactory {
    public abstract IDataSetStorageProvider getDataSetStorageProvider(String var1) throws DataSetStorageException;
}

