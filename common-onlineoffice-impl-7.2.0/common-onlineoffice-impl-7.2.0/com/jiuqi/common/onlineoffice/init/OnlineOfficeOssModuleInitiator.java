/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 */
package com.jiuqi.common.onlineoffice.init;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class OnlineOfficeOssModuleInitiator {
    public static final String ONLINE_OFFICE = "ONLINE_OFFICE";
    public static final Logger LOGGER = LoggerFactory.getLogger(OnlineOfficeOssModuleInitiator.class);

    public void init() {
    }

    public void initWhenStarted() throws Exception {
        LOGGER.info("OnlineOfficeOssModuleInitiator \u521b\u5efabucket!");
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        if (!bucketService.existBucket(ONLINE_OFFICE)) {
            this.createBucket(bucketService, ONLINE_OFFICE);
        }
    }

    private void createBucket(BucketService bucketService, String bucketName) throws ObjectStorageException {
        Bucket bucket = new Bucket();
        bucket.setOpen(true);
        bucket.setName(bucketName);
        bucket.setOwner(ONLINE_OFFICE);
        bucket.setDesc("\u5408\u5e76\u62a5\u8868\u5206\u6790\u62a5\u544a\u6587\u6863\u7ba1\u7406\u6a21\u5757");
        bucketService.createBucket(bucket);
    }
}

