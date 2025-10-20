/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.util.type.GUID
 */
package com.jiuqi.bi.oss.storage;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectNotFoundException;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.MultipartObjectUploader;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.util.type.GUID;
import java.io.InputStream;
import java.util.List;

public abstract class AbstractObjectStorage
implements IObjectStorage {
    private IObjectMetaAdapter metaAdapter;

    public AbstractObjectStorage(IObjectMetaAdapter metaAdapter) {
        this.metaAdapter = metaAdapter;
    }

    @Override
    public void initialize(StorageConfig context) throws ObjectStorageException {
    }

    public final Bucket getBucket(String bucketName) throws ObjectStorageException {
        return this.metaAdapter.getBucket(bucketName);
    }

    public final boolean existBucket(String bucketName) throws ObjectStorageException {
        return this.metaAdapter.existBucket(bucketName);
    }

    public final List<Bucket> listBucket() throws ObjectStorageException {
        return this.metaAdapter.listBucket();
    }

    public final ObjectInfo getObjectInfo(String bucketName, String key) throws ObjectStorageException {
        return this.metaAdapter.getObjectInfo(bucketName, key);
    }

    @Override
    public MultipartObjectUploader createMultipartObjectUploader(Bucket bucket) throws ObjectStorageException {
        return null;
    }

    @Override
    public String copy(String bucketName, String key) throws ObjectStorageException {
        ObjectInfo info = this.getObjectInfo(bucketName, key);
        if (info == null) {
            throw new ObjectNotFoundException(key);
        }
        String newKey = GUID.newGUID();
        info.setKey(newKey);
        if (info.getName() != null) {
            info.setName(info.getName() + "(1)");
        }
        InputStream input = this.download(bucketName, key);
        this.upload(bucketName, info.getKey(), input, info);
        return newKey;
    }

    @Override
    public void move(String key, String srcBucket, String destBucket) throws ObjectStorageException {
        throw new UnsupportedOperationException("\u5f53\u524d\u4ecb\u8d28\u4e0d\u652f\u6301\u8de8bucket\u79fb\u52a8\u6570\u636e");
    }

    @Override
    public void close() throws ObjectStorageException {
    }
}

