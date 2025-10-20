/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage.disk;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.ObjectStorageFactory;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConnectException;
import com.jiuqi.bi.oss.storage.disk.DiskStorage;
import com.jiuqi.bi.oss.storage.disk.DiskStorageConfig;
import java.io.File;

public class DiskStorageFactory
extends ObjectStorageFactory {
    @Override
    public IObjectStorage createStorage(StorageConfig context, IObjectMetaAdapter metaAdapter) throws ObjectStorageException {
        if (!(context instanceof DiskStorageConfig)) {
            throw new ObjectStorageException("context\u4fe1\u606f\u4f20\u9012\u6709\u8bef");
        }
        DiskStorage disk = new DiskStorage(metaAdapter);
        disk.initialize(context);
        return disk;
    }

    @Override
    public String getType() {
        return "DISK";
    }

    @Override
    public String getTitle() {
        return "\u6587\u4ef6\u7cfb\u7edf";
    }

    @Override
    public StorageConfig newInstanceConfig() {
        return new DiskStorageConfig();
    }

    @Override
    public boolean testConnect(StorageConfig config) throws StorageConnectException {
        DiskStorageConfig cfg = (DiskStorageConfig)config;
        String basePath = cfg.getBasePath();
        if (basePath == null || basePath.trim().isEmpty()) {
            throw new StorageConnectException("\u672a\u914d\u7f6e\u78c1\u76d8\u8def\u5f84");
        }
        File f = new File(basePath);
        return f.exists() || f.mkdirs();
    }
}

