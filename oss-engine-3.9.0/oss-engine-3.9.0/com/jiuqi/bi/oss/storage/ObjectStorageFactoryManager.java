/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.ObjectStorageFactory;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.db.DBStorageFactory;
import com.jiuqi.bi.oss.storage.disk.DiskStorageFactory;
import com.jiuqi.bi.oss.storage.mongo.MongoStorageFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ObjectStorageFactoryManager {
    private static ObjectStorageFactoryManager instance = new ObjectStorageFactoryManager();
    private Map<String, ObjectStorageFactory> map = new ConcurrentHashMap<String, ObjectStorageFactory>();
    private IObjectMetaAdapter metaAdapter;

    public static final ObjectStorageFactoryManager getInstance() {
        return instance;
    }

    private ObjectStorageFactoryManager() {
        this.registerObjectStorageFactory("DATABASE", new DBStorageFactory());
        this.registerObjectStorageFactory("DISK", new DiskStorageFactory());
        this.registerObjectStorageFactory("MongoDB", new MongoStorageFactory());
    }

    public void registerObjectStorageFactory(String type, ObjectStorageFactory factory) {
        this.map.put(type, factory);
    }

    public void unregisterObjectStorageFactory(String type) {
        this.map.remove(type);
    }

    public List<String> types() {
        return new ArrayList<String>(this.map.keySet());
    }

    public ObjectStorageFactory getFactory(String type) {
        return this.map.get(type);
    }

    public void setMetaAdapter(IObjectMetaAdapter metaAdapter) {
        this.metaAdapter = metaAdapter;
    }

    public IObjectStorage createStorage(String type, StorageConfig context) throws ObjectStorageException {
        if (this.metaAdapter == null) {
            throw new ObjectStorageException("\u672a\u6307\u5b9a\u5143\u6570\u636e\u7684\u5b58\u50a8\u63d0\u4f9b\u5668");
        }
        ObjectStorageFactory factory = this.map.get(type);
        if (factory == null) {
            throw new ObjectStorageException("\u672a\u627e\u5230\u5bf9\u8c61\u5b58\u50a8\u5668\uff1a" + type);
        }
        return factory.createStorage(context, this.metaAdapter);
    }
}

