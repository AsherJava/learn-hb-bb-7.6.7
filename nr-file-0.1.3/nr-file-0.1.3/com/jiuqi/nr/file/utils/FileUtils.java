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
 *  com.jiuqi.bi.util.StringUtils
 */
package com.jiuqi.nr.file.utils;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    public static String GROUPKEY = "fileGroupKey";
    public static String SECRETLEVEL = "secretlevel";
    private static final int TEMP_BUCKET_EXPIRE_MILLS = 345600000;

    public static String generateFileKey(String extension) {
        String key = FileUtils.generateFileKey();
        return StringUtils.isEmpty((String)extension) ? key : String.format("%s%s", key, extension);
    }

    public static String generateFileKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static ObjectStorageService objService(String bucketName) throws ObjectStorageException {
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

    public static ObjectStorageService objServiceTemp(String bucketName) throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(bucketName);
        if (!exist) {
            Bucket bucket = new Bucket(bucketName);
            bucket.setExpireTime(345600000);
            bucketService.createBucket(bucket);
            bucketService.close();
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(bucketName);
        return objService;
    }

    public static String joinKey(String key, String directory) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        return directory == null || "".equals(directory) ? key : directory + "\\" + key;
    }

    public static void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    public static Date getCreateDate(ObjectInfo objectInfo) {
        if (objectInfo.getCreateTime() == null) {
            return null;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = df.parse(objectInfo.getCreateTime());
        }
        catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return date;
    }

    public static String getFormateDate(Date date) {
        if (date == null) {
            return null;
        }
        String fd = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        fd = df.format(date);
        return fd;
    }
}

