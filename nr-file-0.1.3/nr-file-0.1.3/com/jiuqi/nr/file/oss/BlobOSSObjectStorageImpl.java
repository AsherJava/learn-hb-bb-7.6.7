/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.oss.storage.AbstractObjectStorage
 *  com.jiuqi.bi.oss.storage.IObjectMetaAdapter
 *  com.jiuqi.bi.oss.storage.StorageConfig
 *  com.jiuqi.np.blob.BlobContainer
 *  com.jiuqi.np.blob.BlobContainerProvider
 */
package com.jiuqi.nr.file.oss;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.oss.storage.AbstractObjectStorage;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.np.blob.BlobContainer;
import com.jiuqi.np.blob.BlobContainerProvider;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BlobOSSObjectStorageImpl
extends AbstractObjectStorage {
    private static final Logger logger = LoggerFactory.getLogger(BlobOSSObjectStorageImpl.class);
    protected static final String NAME = "default";
    private BlobContainerProvider provider;

    public BlobOSSObjectStorageImpl(IObjectMetaAdapter metaAdapter, BlobContainerProvider provider) {
        super(metaAdapter);
        this.provider = provider;
    }

    private BlobContainer getContainer(String bucketName) {
        return this.provider.getContainer(bucketName);
    }

    public boolean createBucket(Bucket bucket) throws ObjectStorageException {
        return true;
    }

    public boolean deleteBucket(String bucket) throws ObjectStorageException {
        this.getContainer(bucket).deleteAllBlobs();
        return false;
    }

    public void close() throws ObjectStorageException {
        super.close();
    }

    public void initialize(StorageConfig context) throws ObjectStorageException {
        super.initialize(context);
    }

    public boolean deleteObject(String bucketName, String key) throws ObjectStorageException {
        BlobContainer container = this.getContainer(bucketName);
        boolean b = container.exists(key);
        if (b) {
            container.deleteIfExists(key);
            return true;
        }
        return true;
    }

    private ObjectStorageService objService(String bucketName) throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(bucketName);
        if (!exist) {
            Bucket bucket = new Bucket(bucketName);
            bucketService.createBucket(bucket);
            bucketService.close();
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(bucketName);
        return objService;
    }

    public InputStream download(String bucketName, String key) throws ObjectStorageException {
        BlobContainer container = this.getContainer(bucketName);
        boolean b = container.exists(key);
        if (b) {
            byte[] bytes = null;
            try {
                bytes = container.downloadBytes(key);
            }
            catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
            if (bytes != null) {
                return new ByteArrayInputStream(bytes);
            }
        }
        return null;
    }

    public void download(String bucketName, String key, OutputStream outputStream) throws ObjectStorageException {
        BlobContainer container = this.getContainer(bucketName);
        boolean b = container.exists(key);
        if (b) {
            try {
                container.downloadToStream(key, outputStream);
            }
            catch (IOException e) {
                logger.info(e.getMessage());
            }
        }
    }

    public boolean existObject(String bucketName, String key) throws ObjectStorageException {
        BlobContainer container = this.getContainer(bucketName);
        boolean b = container.exists(key);
        return b;
    }

    public String name() {
        return "\u62a5\u8868\u5b58\u50a8";
    }

    public void upload(String bucketName, String key, InputStream input, ObjectInfo info) throws ObjectStorageException {
        BlobContainer container = this.getContainer(bucketName);
        try {
            container.uploadFromStream(key, input);
        }
        catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}

