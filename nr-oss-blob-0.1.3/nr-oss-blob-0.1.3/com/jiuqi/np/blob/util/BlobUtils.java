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
 *  com.jiuqi.bi.oss.storage.DBMetaAdapter
 *  com.jiuqi.bi.oss.storage.IConnectionProvider
 *  com.jiuqi.bi.oss.storage.IObjectMetaAdapter
 *  com.jiuqi.bi.oss.storage.StorageConfig
 *  com.jiuqi.bi.oss.storage.db.DBStorageConfig
 *  com.jiuqi.bi.oss.storage.disk.DiskStorageConfig
 *  com.jiuqi.bi.oss.storage.mongo.MongoStorageConfig
 *  com.jiuqi.nvwa.oss.config.OSSConnectionProvider
 *  org.json.JSONObject
 */
package com.jiuqi.np.blob.util;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.oss.storage.DBMetaAdapter;
import com.jiuqi.bi.oss.storage.IConnectionProvider;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.db.DBStorageConfig;
import com.jiuqi.bi.oss.storage.disk.DiskStorageConfig;
import com.jiuqi.bi.oss.storage.mongo.MongoStorageConfig;
import com.jiuqi.np.blob.conf.NpBlobProperties;
import com.jiuqi.np.blob.util.BeanUtil;
import com.jiuqi.nvwa.oss.config.OSSConnectionProvider;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.util.StringUtils;

public class BlobUtils {
    private static final Logger LOG = LoggerFactory.getLogger(BlobUtils.class);
    private static final String LOAD_STORAGE = "loadStorage";
    private static Map<String, ObjectStorageService> objectStorageService = new HashMap<String, ObjectStorageService>();

    public static String getTimeSql(String dbName) {
        if (BlobUtils.isDatabase(dbName, "MSSQL", "SYBASE")) {
            return " GETDATE() ";
        }
        if (BlobUtils.isDatabase(dbName, "ORACLE", "DM", "KINGBASE")) {
            return " SYSDATE ";
        }
        if (BlobUtils.isDatabase(dbName, "MYSQL", "HANA", "GBASE", "IMPALA")) {
            return " NOW() ";
        }
        if (BlobUtils.isDatabase(dbName, "DB2")) {
            return " CURRENT TIME ";
        }
        return null;
    }

    private static boolean isDatabase(String dbName, String ... args) {
        for (String item : args) {
            if (!item.equalsIgnoreCase(dbName)) continue;
            return true;
        }
        return false;
    }

    public static String generateFileKey(String extension) {
        String key = BlobUtils.generateFileKey();
        return StringUtils.isEmpty(extension) ? key : String.format("%s%s", key, extension);
    }

    public static String generateFileKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean diskUpload(String basePath, String path, String bucket, InputStream inStream) {
        boolean success = false;
        try {
            String key;
            String split = "";
            if (System.getProperty("file.separator").equals("\\")) {
                split = "\\";
            }
            String[] containers = bucket.split(split + System.getProperty("file.separator"));
            String bucketNames = containers[containers.length - 1];
            String fullBasePath = basePath + System.getProperty("file.separator") + containers[0];
            ObjectStorageService objService = BlobUtils.buildDiskManager(fullBasePath, bucketNames);
            boolean existObject = objService.existObject(key = path.replace(fullBasePath, "").replace(System.getProperty("file.separator") + bucketNames + System.getProperty("file.separator"), ""));
            if (existObject) {
                objService.deleteObject(key);
            }
            ObjectInfo oi = new ObjectInfo();
            oi.setKey(key);
            oi.setSize((long)inStream.available());
            oi.setName(key);
            objService.upload(key, inStream, oi);
            success = true;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InputStream diskDownload(String basePath, String path, String bucket) {
        String key;
        ObjectStorageService objService;
        block6: {
            String split = "";
            if (System.getProperty("file.separator").equals("\\")) {
                split = "\\";
            }
            String[] containers = bucket.split(split + System.getProperty("file.separator"));
            String bucketNames = containers[containers.length - 1];
            String fullBasePath = basePath + System.getProperty("file.separator") + containers[0];
            objService = BlobUtils.buildDiskManager(fullBasePath, bucketNames);
            boolean existObject = objService.existObject(key = path.replace(fullBasePath, "").replace(System.getProperty("file.separator") + bucketNames + System.getProperty("file.separator"), ""));
            if (existObject) break block6;
            InputStream inputStream = null;
            return inputStream;
        }
        try {
            InputStream inputStream = objService.download(key);
            return inputStream;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean diskDelete(String basePath, String path, String bucket) {
        boolean success = false;
        try {
            String key;
            String split = "";
            if (System.getProperty("file.separator").equals("\\")) {
                split = "\\";
            }
            String[] containers = bucket.split(split + System.getProperty("file.separator"));
            String bucketNames = containers[containers.length - 1];
            String fullBasePath = basePath + System.getProperty("file.separator") + containers[0];
            ObjectStorageService objService = BlobUtils.buildDiskManager(fullBasePath, bucketNames);
            boolean existObject = objService.existObject(key = path.replace(fullBasePath, "").replace(System.getProperty("file.separator") + bucketNames + System.getProperty("file.separator"), ""));
            if (existObject) {
                objService.deleteObject(key);
                success = true;
            }
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean diskExist(String basePath, String path, String bucket) {
        boolean success = false;
        try {
            boolean existObject;
            String split = "";
            if (System.getProperty("file.separator").equals("\\")) {
                split = "\\";
            }
            String[] containers = bucket.split(split + System.getProperty("file.separator"));
            String bucketNames = containers[containers.length - 1];
            String fullBasePath = basePath + System.getProperty("file.separator") + containers[0];
            ObjectStorageService objService = BlobUtils.buildDiskManager(fullBasePath, bucketNames);
            String key = path.replace(fullBasePath, "").replace(System.getProperty("file.separator") + bucketNames + System.getProperty("file.separator"), "");
            boolean bl = existObject = objService.existObject(key);
            return bl;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    private static ObjectStorageService buildDiskManager(String basePath, String bucketName) throws ObjectStorageException {
        if (objectStorageService.get(bucketName) != null) {
            return objectStorageService.get(bucketName);
        }
        if (!objectStorageService.containsKey(LOAD_STORAGE)) {
            OSSConnectionProvider connProvider = new OSSConnectionProvider();
            DBMetaAdapter meta = new DBMetaAdapter((IConnectionProvider)connProvider);
            DiskStorageConfig diskConfig = new DiskStorageConfig(basePath);
            if (ObjectStorageManager.getInstance().isLoaded()) {
                ObjectStorageManager.getInstance().reloadStorageConfig("DISK", (StorageConfig)diskConfig);
            } else {
                ObjectStorageManager.getInstance().loadStorage((IObjectMetaAdapter)meta, (StorageConfig)diskConfig);
            }
            objectStorageService.put(LOAD_STORAGE, null);
        }
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        Bucket bucket = new Bucket(bucketName);
        boolean exist = bucketService.existBucket(bucketName);
        if (!exist) {
            bucketService.createBucket(bucket);
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(bucketName);
        objectStorageService.put(bucketName, objService);
        bucketService.close();
        return objService;
    }

    private static ObjectStorageService buildDBManager(String buckets) throws ObjectStorageException {
        NpBlobProperties properties = BeanUtil.getBean(NpBlobProperties.class);
        if (properties.getDataSourceType().toUpperCase().equals("MONGO")) {
            return BlobUtils.buildMongoManager(buckets);
        }
        if (objectStorageService.get(buckets) != null) {
            return objectStorageService.get(buckets);
        }
        String bucketName = buckets.replace("FS_", "").replace("fs_", "");
        if (!objectStorageService.containsKey(LOAD_STORAGE)) {
            OSSConnectionProvider connProvider = new OSSConnectionProvider();
            DBMetaAdapter meta = new DBMetaAdapter((IConnectionProvider)connProvider);
            DBStorageConfig dbConfig = new DBStorageConfig((IConnectionProvider)connProvider);
            if (ObjectStorageManager.getInstance().isLoaded()) {
                ObjectStorageManager.getInstance().reloadStorageConfig("DATABASE", (StorageConfig)dbConfig);
            } else {
                ObjectStorageManager.getInstance().loadStorage((IObjectMetaAdapter)meta, (StorageConfig)dbConfig);
            }
            objectStorageService.put(LOAD_STORAGE, null);
        }
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        Bucket bucket = new Bucket(bucketName);
        boolean exist = bucketService.existBucket(bucketName);
        if (!exist) {
            bucketService.createBucket(bucket);
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(bucketName);
        objectStorageService.put(bucketName, objService);
        bucketService.close();
        return objService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean dbUpload(String key, String bucket, InputStream inStream) {
        boolean success = false;
        try {
            ObjectStorageService objService = BlobUtils.buildDBManager(bucket);
            boolean existObject = objService.existObject(key);
            if (existObject) {
                objService.deleteObject(key);
            }
            ObjectInfo oi = new ObjectInfo();
            oi.setKey(key);
            oi.setSize((long)inStream.available());
            oi.setName(key);
            objService.upload(key, inStream, oi);
            success = true;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InputStream dbDownload(String key, String bucket) {
        ObjectStorageService objService;
        block5: {
            objService = BlobUtils.buildDBManager(bucket);
            boolean existObject = objService.existObject(key);
            if (existObject) break block5;
            InputStream inputStream = null;
            return inputStream;
        }
        try {
            InputStream inputStream = objService.download(key);
            return inputStream;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean dbDelete(String key, String bucket) {
        boolean success = false;
        try {
            ObjectStorageService objService = BlobUtils.buildDBManager(bucket);
            boolean existObject = objService.existObject(key);
            if (existObject) {
                objService.deleteObject(key);
                success = true;
            }
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean dbExist(String key, String bucket) {
        boolean success = false;
        try {
            boolean existObject;
            ObjectStorageService objService = BlobUtils.buildDBManager(bucket);
            boolean bl = existObject = objService.existObject(key);
            return bl;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    private static ObjectStorageService buildMongoManager(String buckets) throws ObjectStorageException {
        if (objectStorageService.get(buckets) != null) {
            return objectStorageService.get(buckets);
        }
        String bucketName = buckets.replace("FS_", "").replace("fs_", "");
        if (bucketName.lastIndexOf("__") > -1) {
            bucketName = bucketName.substring(bucketName.lastIndexOf("__") + 2, bucketName.length());
        }
        if (!objectStorageService.containsKey(LOAD_STORAGE)) {
            OSSConnectionProvider connProvider = new OSSConnectionProvider();
            DBMetaAdapter meta = new DBMetaAdapter((IConnectionProvider)connProvider);
            MongoProperties mongoProperties = BeanUtil.getBean(MongoProperties.class);
            MongoStorageConfig dbConfig = new MongoStorageConfig();
            JSONObject cfg = new JSONObject();
            cfg.put("host", (Object)mongoProperties.getHost());
            cfg.put("port", (Object)mongoProperties.getPort());
            cfg.put("dbName", (Object)mongoProperties.getDatabase());
            cfg.put("chunkSize", (Object)"99999");
            dbConfig.fromJson(cfg);
            if (ObjectStorageManager.getInstance().isLoaded()) {
                ObjectStorageManager.getInstance().reloadStorageConfig("MongoDB", (StorageConfig)dbConfig);
            } else {
                ObjectStorageManager.getInstance().loadStorage((IObjectMetaAdapter)meta, (StorageConfig)dbConfig);
            }
            objectStorageService.put(LOAD_STORAGE, null);
        }
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        Bucket bucket = new Bucket(bucketName);
        boolean exist = bucketService.existBucket(bucketName);
        if (!exist) {
            bucketService.createBucket(bucket);
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(bucketName);
        objectStorageService.put(bucketName, objService);
        bucketService.close();
        return objService;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean mongoUpload(String key, String bucket, InputStream inStream) {
        boolean success = false;
        try {
            ObjectStorageService objService = BlobUtils.buildMongoManager(bucket);
            boolean existObject = objService.existObject(key);
            if (existObject) {
                objService.deleteObject(key);
            }
            ObjectInfo oi = new ObjectInfo();
            oi.setKey(key);
            oi.setSize((long)inStream.available());
            oi.setName(key);
            objService.upload(key, inStream, oi);
            success = true;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static InputStream mongoDownload(String key, String bucket) {
        ObjectStorageService objService;
        block5: {
            objService = BlobUtils.buildMongoManager(bucket);
            boolean existObject = objService.existObject(key);
            if (existObject) break block5;
            InputStream inputStream = null;
            return inputStream;
        }
        try {
            InputStream inputStream = objService.download(key);
            return inputStream;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean mongoDelete(String key, String bucket) {
        boolean success = false;
        try {
            ObjectStorageService objService = BlobUtils.buildMongoManager(bucket);
            boolean existObject = objService.existObject(key);
            if (existObject) {
                objService.deleteObject(key);
                success = true;
            }
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean mongoExist(String key, String bucket) {
        boolean success = false;
        try {
            boolean existObject;
            ObjectStorageService objService = BlobUtils.buildMongoManager(bucket);
            boolean bl = existObject = objService.existObject(key);
            return bl;
        }
        catch (Exception e) {
            LOG.info(e.getMessage() + "{}", e);
        }
        return success;
    }
}

