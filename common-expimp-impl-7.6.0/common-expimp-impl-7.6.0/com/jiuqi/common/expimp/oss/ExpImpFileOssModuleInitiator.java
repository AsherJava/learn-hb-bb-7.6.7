/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 */
package com.jiuqi.common.expimp.oss;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ExpImpFileOssModuleInitiator {
    public static final String EXPIMP_FILE_OSS_BUCKET = "EXPIMP_FILE_OSS";
    public static final Logger LOGGER = LoggerFactory.getLogger(ExpImpFileOssModuleInitiator.class);

    public void init() throws Exception {
    }

    public void initWhenStarted() throws Exception {
        LOGGER.info("ExpImpFileOssModuleInitiator \u521b\u5efabucket!");
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        if (!bucketService.existBucket(EXPIMP_FILE_OSS_BUCKET)) {
            this.createBucket(bucketService, EXPIMP_FILE_OSS_BUCKET);
        }
        bucketService.close();
    }

    private void createBucket(BucketService bucketService, String bucketName) throws ObjectStorageException {
        Bucket bucket = new Bucket();
        bucket.setOpen(true);
        bucket.setName(bucketName);
        bucket.setOwner(EXPIMP_FILE_OSS_BUCKET);
        bucket.setDesc("\u5408\u5e76\u62a5\u8868\u5bfc\u5165\u5bfc\u51fa\u4e34\u65f6\u5927\u6587\u4ef6\u5b58\u50a8\u7ba1\u7406\u6a21\u5757");
        bucketService.createBucket(bucket);
    }
}

