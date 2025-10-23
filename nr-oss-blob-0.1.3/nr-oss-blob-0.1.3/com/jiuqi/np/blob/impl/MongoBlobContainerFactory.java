/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.MongoClient
 */
package com.jiuqi.np.blob.impl;

import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.impl.BlobContainerFactory;
import com.jiuqi.np.blob.impl.BlobContainerMongoImpl;
import com.mongodb.MongoClient;
import java.util.concurrent.ConcurrentHashMap;

public class MongoBlobContainerFactory
implements BlobContainerFactory {
    private final MongoClient mongoClient;
    private final String dataBase;
    private final ConcurrentHashMap<String, BlobContainerMongoImpl> containers = new ConcurrentHashMap();

    public MongoBlobContainerFactory(String dataBase, MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        this.dataBase = dataBase;
    }

    @Override
    public BlobContainer getBlobContainer(String name) {
        if (null == name) {
            throw new IllegalArgumentException("MongoBlobContainer name must not be null.");
        }
        BlobContainerMongoImpl container = this.containers.get(name);
        if (container != null) {
            return container;
        }
        BlobContainerMongoImpl newContainer = new BlobContainerMongoImpl(this.dataBase, name, this.mongoClient);
        container = this.containers.putIfAbsent(name, newContainer);
        if (container != null) {
            return container;
        }
        return newContainer;
    }
}

