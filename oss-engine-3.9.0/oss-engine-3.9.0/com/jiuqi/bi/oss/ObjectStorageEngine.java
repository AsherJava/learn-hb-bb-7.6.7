/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.oss.event.BucketEventEngine;
import com.jiuqi.bi.oss.event.BucketEventType;
import java.util.List;

public class ObjectStorageEngine {
    private ObjectStorageManager storageManager = ObjectStorageManager.getInstance();

    public static ObjectStorageEngine newInstance() {
        return new ObjectStorageEngine();
    }

    public void createBucket(Bucket bucket) throws ObjectStorageException {
        this.storageManager.createBucket(bucket);
        BucketEventEngine.getInstance().publish(bucket, BucketEventType.ADD);
    }

    public void deleteBucket(String bucketName) throws ObjectStorageException {
        this.storageManager.deleteBucket(bucketName);
        Bucket bucket = new Bucket();
        bucket.setName(bucketName);
        BucketEventEngine.getInstance().publish(bucket, BucketEventType.DELETE);
    }

    public Bucket getBucket(String bucketName) throws ObjectStorageException {
        return this.storageManager.getBucket(bucketName);
    }

    public List<Bucket> listBucket() throws ObjectStorageException {
        return this.storageManager.listBucket();
    }

    public ObjectStorageService createObjectService(String bucketName) throws ObjectStorageException {
        return this.storageManager.createObjectService(bucketName);
    }

    public ObjectStorageService createTemporaryObjectService() throws ObjectStorageException {
        return this.storageManager.createTemporaryObjectService();
    }
}

