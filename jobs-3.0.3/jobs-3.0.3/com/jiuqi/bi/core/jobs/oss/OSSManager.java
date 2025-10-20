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
 */
package com.jiuqi.bi.core.jobs.oss;

import com.jiuqi.bi.core.jobs.JobLifeCycle;
import com.jiuqi.bi.core.jobs.JobsException;
import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSSManager {
    private static Logger logger = LoggerFactory.getLogger(OSSManager.class);
    private static OSSManager instance;
    private String bucketId;

    public static synchronized OSSManager getInstance() {
        if (instance == null) {
            instance = new OSSManager(JobLifeCycle.getBucket());
        }
        return instance;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public OSSManager(String bucketId) {
        this.bucketId = bucketId;
        try (BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();){
            if (!bucketService.existBucket(bucketId)) {
                Bucket bucket = new Bucket(bucketId);
                bucket.setDesc("\u5373\u65f6\u4efb\u52a1\u4e2d\u7684\u6587\u4ef6\u5305\u62ec\uff1a\u5206\u6790\u8868\u3001\u5206\u6790\u62a5\u544a\u3001\u5373\u5e2d\u67e5\u8be2\u7b49\u6a21\u5757\u4e2d\u5bfc\u51fa\u7684\u6587\u4ef6");
                bucketService.createBucket(bucket);
            }
        }
        catch (ObjectStorageException e) {
            logger.error("\u521b\u5efabucket\u5931\u8d25", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void upload(String key, InputStream inputStream, long size) throws JobsException {
        try (ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(this.bucketId);){
            ObjectInfo info = new ObjectInfo(key);
            info.setSize(size);
            objService.upload(key, inputStream, info);
        }
        catch (ObjectStorageException e) {
            throw new JobsException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public InputStream download(String key) throws JobsException {
        InputStream inputStream;
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(this.bucketId);
        try {
            inputStream = objService.download(key);
        }
        catch (Throwable throwable) {
            try {
                objService.close();
                throw throwable;
            }
            catch (ObjectStorageException e) {
                throw new JobsException(e);
            }
        }
        objService.close();
        return inputStream;
    }

    public void delete(String key) throws JobsException {
        try (ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(this.bucketId);){
            objService.deleteObject(key);
        }
        catch (ObjectStorageException e) {
            throw new JobsException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public long getSize(String key) throws JobsException {
        try (ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(this.bucketId);){
            ObjectInfo objInfo = objService.getObjectInfo(key);
            if (objInfo == null) {
                long l = 0L;
                return l;
            }
            long l = objInfo.getSize();
            return l;
        }
        catch (ObjectStorageException e) {
            throw new JobsException(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean exist(String key) throws JobsException {
        boolean bl;
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(this.bucketId);
        try {
            bl = objService.existObject(key);
        }
        catch (Throwable throwable) {
            try {
                objService.close();
                throw throwable;
            }
            catch (ObjectStorageException e) {
                throw new JobsException(e);
            }
        }
        objService.close();
        return bl;
    }

    public String getBucketId() {
        return this.bucketId;
    }
}

