/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage.mongo;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.ObjectStorageFactory;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageConnectException;
import com.jiuqi.bi.oss.storage.mongo.MongoStorage;
import com.jiuqi.bi.oss.storage.mongo.MongoStorageConfig;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MongoStorageFactory
extends ObjectStorageFactory {
    @Override
    public IObjectStorage createStorage(StorageConfig context, IObjectMetaAdapter metaAdapter) throws ObjectStorageException {
        if (!(context instanceof MongoStorageConfig)) {
            throw new ObjectStorageException("context\u4fe1\u606f\u4f20\u9012\u6709\u8bef");
        }
        MongoStorage storage = new MongoStorage(metaAdapter);
        storage.initialize(context);
        return storage;
    }

    @Override
    public String getType() {
        return "MongoDB";
    }

    @Override
    public String getTitle() {
        return "MongoDB";
    }

    @Override
    public StorageConfig newInstanceConfig() {
        return new MongoStorageConfig();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean testConnect(StorageConfig config) throws StorageConnectException {
        MongoStorageConfig cfg = (MongoStorageConfig)config;
        try (Socket socket = new Socket();){
            socket.connect(new InetSocketAddress(cfg.getHost(), cfg.getPort()));
            boolean bl = true;
            return bl;
        }
        catch (IOException e) {
            throw new StorageConnectException(e.getMessage());
        }
    }
}

