/*
 * Decompiled with CFR 0.152.
 */
package com.jiuqi.bi.oss;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.event.BucketEventEngine;
import com.jiuqi.bi.oss.event.BucketEventType;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.StorageUtils;
import java.util.List;

public class BucketService
implements AutoCloseable {
    private IObjectMetaAdapter meta;
    private IObjectStorage storage;

    BucketService(IObjectMetaAdapter metaAdapter, IObjectStorage storage) {
        this.meta = metaAdapter;
        this.storage = storage;
    }

    public void createBucket(Bucket bucket) throws ObjectStorageException {
        StorageUtils.checkBucketName(bucket.getName());
        this.storage.createBucket(bucket);
        this.meta.createBucket(bucket);
        BucketEventEngine.getInstance().publish(bucket, BucketEventType.ADD);
    }

    public void deleteBucket(String bucketName) throws ObjectStorageException {
        this.checkMigrate(bucketName);
        this.storage.deleteBucket(bucketName);
        this.meta.deleteBucket(bucketName);
        Bucket bucket = new Bucket();
        bucket.setName(bucketName);
        BucketEventEngine.getInstance().publish(bucket, BucketEventType.DELETE);
    }

    public Bucket getBucket(String bucketName) throws ObjectStorageException {
        return this.meta.getBucket(bucketName);
    }

    public boolean existBucket(String bucketName) throws ObjectStorageException {
        return this.meta.existBucket(bucketName);
    }

    @Override
    public void close() throws ObjectStorageException {
        if (this.storage != null) {
            this.storage.close();
        }
    }

    public List<Bucket> listBucket() throws ObjectStorageException {
        return this.meta.listBucket();
    }

    private void checkMigrate(String bucketName) throws ObjectStorageException {
        if (ObjectStorageManager.getInstance().isMigrating(bucketName)) {
            throw new ObjectStorageException("\u5bf9\u8c61\u5b58\u50a8\u6570\u636e\u6b63\u5728\u8fc1\u79fb\uff0c\u65e0\u6cd5\u6267\u884c\u8be5\u64cd\u4f5c\u3002\u5982\u5728\u5373\u65f6\u4efb\u52a1\u76d1\u63a7\u4e2d\u786e\u8ba4\u8fc1\u79fb\u4efb\u52a1\u5df2\u7ed3\u675f\uff0c\u5c1d\u8bd5\u8fdb\u884c\u91cd\u7f6e\u8fc1\u79fb\u72b6\u6001\u3002");
        }
    }
}

