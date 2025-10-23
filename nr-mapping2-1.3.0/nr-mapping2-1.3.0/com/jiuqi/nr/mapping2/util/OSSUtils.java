/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 */
package com.jiuqi.nr.mapping2.util;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OSSUtils {
    private static Logger logger = LoggerFactory.getLogger(OSSUtils.class);
    private static final String BUCKET_ID = "NR_MAPPING_JIO";

    private static void initBucket() {
        try {
            ObjectStorageEngine bucketService = ObjectStorageEngine.newInstance();
            if (bucketService.getBucket(BUCKET_ID) == null) {
                Bucket bucket = new Bucket(BUCKET_ID);
                bucket.setDesc("\u7528\u4e8e\u751f\u6210\u6620\u5c04\u65b9\u6848\u7684JIO\u6587\u4ef6");
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
    public static void upload(String key, InputStream inputStream, long size) throws Exception {
        OSSUtils.initBucket();
        try (ObjectStorageService objService = ObjectStorageEngine.newInstance().createObjectService(BUCKET_ID);){
            ObjectInfo info = new ObjectInfo(key);
            info.setSize(size);
            objService.upload(key, inputStream, info);
        }
        catch (ObjectStorageException e) {
            throw new Exception(e);
        }
    }

    public static InputStream download(String key) throws Exception {
        OSSUtils.initBucket();
        try {
            InputStream stream;
            try (ObjectStorageService objService = ObjectStorageEngine.newInstance().createObjectService(BUCKET_ID);){
                stream = objService.download(key);
            }
            return stream;
        }
        catch (ObjectStorageException e) {
            throw new Exception(e);
        }
    }

    public static void delete(String key) throws Exception {
        OSSUtils.initBucket();
        try (ObjectStorageService objService = ObjectStorageEngine.newInstance().createObjectService(BUCKET_ID);){
            objService.deleteObject(key);
        }
        catch (ObjectStorageException e) {
            throw new Exception(e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static long getSize(String key) throws Exception {
        OSSUtils.initBucket();
        try (ObjectStorageService objService = ObjectStorageEngine.newInstance().createObjectService(BUCKET_ID);){
            ObjectInfo objInfo = objService.getObjectInfo(key);
            if (objInfo == null) {
                long size;
                long l = size = 0L;
                return l;
            }
            long size = objInfo.getSize();
            return size;
        }
        catch (ObjectStorageException e) {
            throw new Exception(e);
        }
    }

    public static boolean exist(String key) throws Exception {
        OSSUtils.initBucket();
        try {
            boolean isExist;
            try (ObjectStorageService objService = ObjectStorageEngine.newInstance().createObjectService(BUCKET_ID);){
                isExist = objService.existObject(key);
            }
            return isExist;
        }
        catch (ObjectStorageException e) {
            throw new Exception(e);
        }
    }
}

