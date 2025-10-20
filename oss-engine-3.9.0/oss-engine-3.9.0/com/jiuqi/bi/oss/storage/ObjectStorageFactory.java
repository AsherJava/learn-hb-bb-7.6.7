/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConnectException;

public abstract class ObjectStorageFactory {
    public abstract String getType();

    public abstract String getTitle();

    public abstract IObjectStorage createStorage(StorageConfig var1, IObjectMetaAdapter var2) throws ObjectStorageException;

    public abstract StorageConfig newInstanceConfig();

    public boolean testConnect(StorageConfig config) throws StorageConnectException {
        throw new StorageConnectException("\u5f53\u524d\u5b58\u50a8\u4ecb\u8d28\u672a\u5b9e\u73b0\u8be5\u529f\u80fd");
    }

    public boolean isHidden() {
        return false;
    }
}

