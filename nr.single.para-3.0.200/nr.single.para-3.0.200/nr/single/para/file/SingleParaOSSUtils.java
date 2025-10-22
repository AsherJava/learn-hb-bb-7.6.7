/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageManager
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.bi.util.StringUtils
 */
package nr.single.para.file;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.util.StringUtils;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SingleParaOSSUtils {
    private static Logger logger = LoggerFactory.getLogger(SingleParaOSSUtils.class);
    private static final String BUCKET_ID = "SINGLEPARA";

    public static void initBucket() {
        SingleParaOSSUtils.initBucket(BUCKET_ID, "\u7528\u4e8eJIO\u53c2\u6570\u4e0a\u4f20");
    }

    public static void initBucket(String bucketName, String bucketDesc) {
        try {
            ObjectStorageEngine bucketService = ObjectStorageEngine.newInstance();
            Bucket oldBucket = bucketService.getBucket(bucketName);
            if (oldBucket == null) {
                Bucket bucket = new Bucket(bucketName);
                bucket.setDesc(bucketDesc);
                bucketService.createBucket(bucket);
            } else if (StringUtils.isEmpty((String)oldBucket.getDesc())) {
                ObjectStorageManager mgr = ObjectStorageManager.getInstance();
                try {
                    mgr.setBucketDesc(bucketName, bucketDesc);
                }
                catch (NoSuchMethodError e) {
                    logger.error("\u7a0b\u5e8f\u7248\u672c\u8fc7\u4f4e\uff0c\u8bf7\u5347\u7ea7\u7a0b\u5e8f" + e.getMessage(), e);
                }
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
        SingleParaOSSUtils.initBucket();
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
        SingleParaOSSUtils.initBucket();
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
        SingleParaOSSUtils.initBucket();
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
        SingleParaOSSUtils.initBucket();
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
        SingleParaOSSUtils.initBucket();
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

