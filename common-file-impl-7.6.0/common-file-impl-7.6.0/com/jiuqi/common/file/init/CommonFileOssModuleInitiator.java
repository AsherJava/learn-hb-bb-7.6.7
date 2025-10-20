/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.BucketService
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.nvwa.sf.models.ModuleInitiator
 *  javax.servlet.ServletContext
 */
package com.jiuqi.common.file.init;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.nvwa.sf.models.ModuleInitiator;
import javax.servlet.ServletContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class CommonFileOssModuleInitiator
implements ModuleInitiator {
    public static final String COMMON_FILE_OSS_BUCKET = "COMMON_FILE_OSS";
    public static final Logger LOGGER = LoggerFactory.getLogger(CommonFileOssModuleInitiator.class);

    public void init(ServletContext context) {
    }

    public void initWhenStarted(ServletContext context) throws Exception {
        LOGGER.info("CommonFileOssModuleInitiator \u521b\u5efabucket!");
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        if (!bucketService.existBucket(COMMON_FILE_OSS_BUCKET)) {
            this.createBucket(bucketService, COMMON_FILE_OSS_BUCKET);
        }
        bucketService.close();
    }

    private void createBucket(BucketService bucketService, String bucketName) throws ObjectStorageException {
        Bucket bucket = new Bucket();
        bucket.setOpen(true);
        bucket.setName(bucketName);
        bucket.setOwner(COMMON_FILE_OSS_BUCKET);
        bucket.setDesc("\u5408\u5e76\u9644\u4ef6\u5206\u7247\u7ba1\u7406\u6a21\u5757");
        bucketService.createBucket(bucket);
    }
}

