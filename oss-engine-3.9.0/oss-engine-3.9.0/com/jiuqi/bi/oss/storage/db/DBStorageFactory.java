/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage.db;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.ObjectStorageFactory;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConnectException;
import com.jiuqi.bi.oss.storage.db.DBStorage;
import com.jiuqi.bi.oss.storage.db.DBStorageConfig;

public class DBStorageFactory
extends ObjectStorageFactory {
    @Override
    public IObjectStorage createStorage(StorageConfig context, IObjectMetaAdapter metaAdapter) throws ObjectStorageException {
        if (!(context instanceof DBStorageConfig)) {
            throw new ObjectStorageException("context\u4fe1\u606f\u4f20\u9012\u6709\u8bef");
        }
        DBStorage storage = new DBStorage(metaAdapter);
        storage.initialize(context);
        return storage;
    }

    @Override
    public String getType() {
        return "DATABASE";
    }

    @Override
    public String getTitle() {
        return "\u6570\u636e\u5e93";
    }

    @Override
    public StorageConfig newInstanceConfig() {
        return new DBStorageConfig(ObjectStorageManager.getInstance().getConnProvider());
    }

    @Override
    public boolean testConnect(StorageConfig config) throws StorageConnectException {
        DBStorageConfig cfg = (DBStorageConfig)config;
        return cfg.getDataConnProvider() != null;
    }
}

