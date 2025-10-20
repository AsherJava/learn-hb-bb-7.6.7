/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.logging.ConsoleLogger
 *  com.jiuqi.bi.logging.ILogger
 *  com.jiuqi.bi.monitor.IProgressMonitor
 *  com.jiuqi.bi.util.StringUtils
 *  org.json.JSONObject
 */
package com.jiuqi.bi.oss;

import com.jiuqi.bi.logging.ConsoleLogger;
import com.jiuqi.bi.logging.ILogger;
import com.jiuqi.bi.monitor.IProgressMonitor;
import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectNotFoundException;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.oss.storage.DBMetaAdapter;
import com.jiuqi.bi.oss.storage.IConnectionProvider;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.IObjectStorage;
import com.jiuqi.bi.oss.storage.ObjectStorageFactory;
import com.jiuqi.bi.oss.storage.ObjectStorageFactoryManager;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageUtils;
import com.jiuqi.bi.util.StringUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

public class ObjectStorageManager {
    private static ObjectStorageManager mgr = new ObjectStorageManager();
    private IObjectMetaAdapter metaAdapter;
    private IConnectionProvider connProvider;
    private String defaultStorageType;
    private StorageConfig defaultStorageConfig;
    private Map<String, StorageConfig> exclusiveStorageConfigs = new HashMap<String, StorageConfig>();
    private boolean hasLoad = false;
    private ILogger logger = new ConsoleLogger("\u5bf9\u8c61\u5b58\u50a8", 3);

    private ObjectStorageManager() {
    }

    public boolean isLoaded() {
        return this.hasLoad;
    }

    public static ObjectStorageManager getInstance() {
        return mgr;
    }

    public synchronized void loadStorage(StorageConfig config) throws ObjectStorageException {
        if (this.connProvider == null) {
            throw new NullPointerException("\u672a\u8bbe\u7f6e\u6570\u636e\u5e93\u8fde\u63a5\u63d0\u4f9b\u5668");
        }
        this.metaAdapter = new DBMetaAdapter(this.connProvider);
        this.loadStorage(this.metaAdapter, this.findStorageTypeByConfig(config), config);
    }

    public synchronized void loadStorage(IObjectMetaAdapter metaAdapter, StorageConfig config) throws ObjectStorageException {
        this.loadStorage(metaAdapter, this.findStorageTypeByConfig(config), config);
    }

    public synchronized void loadStorage(IObjectMetaAdapter metaAdapter, String storageType, StorageConfig config) throws ObjectStorageException {
        if (config == null) {
            throw new NullPointerException("\u914d\u7f6e\u4fe1\u606f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (this.hasLoad) {
            throw new ObjectStorageException("storage\u4e0d\u5141\u8bb8\u91cd\u590d\u52a0\u8f7d");
        }
        this.metaAdapter = metaAdapter;
        this.defaultStorageConfig = config;
        this.defaultStorageType = storageType;
        this.hasLoad = true;
    }

    public void setExclusiveStorageConfig(String bucketName, StorageConfig storageConfig) {
        this.exclusiveStorageConfigs.put(bucketName.toUpperCase(), storageConfig);
    }

    public void setLogger(ILogger logger) {
        this.logger = logger;
    }

    public void reloadStorageConfig(String storageType, StorageConfig config) {
        this.defaultStorageConfig = config;
        this.defaultStorageType = storageType;
        this.hasLoad = true;
    }

    public void setConnectionProvider(IConnectionProvider provider) {
        this.connProvider = provider;
    }

    public IConnectionProvider getConnProvider() {
        return this.connProvider;
    }

    public BucketService createBucketService() throws ObjectStorageException {
        return this.createBucketService(null);
    }

    BucketService createBucketService(String bucketName) throws ObjectStorageException {
        if (!this.hasLoad) {
            throw new RuntimeException("storage\u672a\u88ab\u52a0\u8f7d");
        }
        Bucket bucket = null;
        if (StringUtils.isNotEmpty((String)bucketName) && (bucket = this.metaAdapter.getBucket(bucketName)) == null) {
            throw new ObjectNotFoundException("\u672a\u77e5\u7684bucket\uff1a" + bucketName);
        }
        IObjectStorage storage = this.getObjectStorage(bucket);
        return new BucketService(this.metaAdapter, storage);
    }

    void createBucket(Bucket bucket) throws ObjectStorageException {
        StorageUtils.checkBucketName(bucket.getName());
        bucket = bucket.clone();
        bucket.setName(bucket.getName().toUpperCase());
        try (IObjectStorage storage = this.getObjectStorage(bucket);){
            storage.createBucket(bucket);
        }
        this.metaAdapter.createBucket(bucket);
    }

    void deleteBucket(String bucketName) throws ObjectStorageException {
        if (this.isMigrating(bucketName)) {
            throw new ObjectStorageException("\u5bf9\u8c61\u5b58\u50a8\u6570\u636e\u6b63\u5728\u8fc1\u79fb\uff0c\u65e0\u6cd5\u6267\u884c\u5220\u9664\u64cd\u4f5c\uff1a" + bucketName + "\u3002\u5982\u5728\u5373\u65f6\u4efb\u52a1\u76d1\u63a7\u4e2d\u786e\u8ba4\u8fc1\u79fb\u4efb\u52a1\u5df2\u7ed3\u675f\uff0c\u5c1d\u8bd5\u8fdb\u884c\u91cd\u7f6e\u8fc1\u79fb\u72b6\u6001\u3002");
        }
        StorageUtils.checkBucketName(bucketName);
        Bucket bucket = this.metaAdapter.getBucket(bucketName);
        try (IObjectStorage storage = this.getObjectStorage(bucket);){
            storage.deleteBucket(bucketName);
        }
        catch (Exception e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
        this.metaAdapter.deleteBucket(bucketName);
    }

    public Bucket getBucket(String bucketName) throws ObjectStorageException {
        return this.metaAdapter.getBucket(bucketName);
    }

    public boolean existBucket(String bucketName) throws ObjectStorageException {
        return this.metaAdapter.existBucket(bucketName);
    }

    public void setBucketDesc(String bucketName, String desc) throws ObjectStorageException {
        this.metaAdapter.updateBucketDesc(bucketName, desc);
    }

    public void updateBucketConfig(Bucket bucket) throws ObjectStorageException {
        this.metaAdapter.updateBucketConfig(bucket);
    }

    public void makeObjectLinkEnable(String bucketName) throws ObjectStorageException {
        this.metaAdapter.makeObjectLinkEnable(bucketName);
    }

    public List<Bucket> listBucket() throws ObjectStorageException {
        return this.metaAdapter.listBucket();
    }

    private IObjectStorage getObjectStorage(Bucket bucket) throws ObjectStorageException {
        ObjectStorageFactoryManager factoryMgr = ObjectStorageFactoryManager.getInstance();
        factoryMgr.setMetaAdapter(this.metaAdapter);
        IObjectStorage storage = null;
        if (bucket == null) {
            storage = factoryMgr.createStorage(this.defaultStorageType, this.defaultStorageConfig);
        } else if (StringUtils.isNotEmpty((String)bucket.getStorageType()) && StringUtils.isNotEmpty((String)bucket.getStorageConfig())) {
            ObjectStorageFactory factory = factoryMgr.getFactory(bucket.getStorageType());
            if (factory == null) {
                throw new ObjectStorageException("\u672a\u77e5\u7684\u5b58\u50a8\u4ecb\u8d28\uff1a" + bucket.getStorageType());
            }
            StorageConfig cfg = factory.newInstanceConfig();
            cfg.fromJson(new JSONObject(bucket.getStorageConfig()));
            storage = factoryMgr.createStorage(bucket.getStorageType(), cfg);
        } else {
            StorageConfig cfg = this.exclusiveStorageConfigs.get(bucket.getName().toUpperCase());
            storage = cfg != null ? factoryMgr.createStorage(this.findStorageTypeByConfig(cfg), cfg) : factoryMgr.createStorage(this.defaultStorageType, this.defaultStorageConfig);
        }
        return storage;
    }

    public ObjectStorageService createObjectService(String bucketName) throws ObjectStorageException {
        if (!this.hasLoad) {
            throw new RuntimeException("storage\u672a\u88ab\u52a0\u8f7d");
        }
        StorageUtils.checkBucketName(bucketName);
        bucketName = bucketName.toUpperCase();
        ObjectStorageFactoryManager factoryMgr = ObjectStorageFactoryManager.getInstance();
        factoryMgr.setMetaAdapter(this.metaAdapter);
        Bucket bucket = this.metaAdapter.getBucket(bucketName);
        IObjectStorage storage = this.getObjectStorage(bucket);
        return new ObjectStorageService(this.metaAdapter, storage, bucketName);
    }

    public ObjectStorageService createTemporaryObjectService() throws ObjectStorageException {
        BucketService bucketService = this.createBucketService();
        if (!bucketService.existBucket("TEMP")) {
            Bucket bucket = new Bucket();
            bucket.setName("TEMP");
            bucket.setDesc("\u6587\u4ef6\u4e34\u65f6\u5b58\u653e\u533a\u57df");
            bucket.setExpireTime(43200000);
            bucket.setOwner("system");
            this.createBucket(bucket);
        }
        return this.createObjectService("TEMP");
    }

    public String getTemporaryBucketName() {
        return "TEMP";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void move(String objKey, String srcBucket, String destBucket) throws ObjectStorageException {
        block11: {
            ObjectInfo info = this.metaAdapter.getObjectInfo(srcBucket, objKey);
            if (info == null) {
                throw new ObjectNotFoundException("\u5bf9\u8c61\u4e0d\u5b58\u5728\uff1a" + objKey);
            }
            ObjectInfo destInfo = this.metaAdapter.getObjectInfo(destBucket, objKey);
            if (destInfo != null) {
                throw new ObjectStorageException("\u76ee\u6807bucket\u4e2d\u5df2\u5b58\u5728\u5bf9\u8c61\uff1a" + objKey);
            }
            this.metaAdapter.addObjectInfo(destBucket, info);
            ObjectStorageFactoryManager factoryMgr = ObjectStorageFactoryManager.getInstance();
            factoryMgr.setMetaAdapter(this.metaAdapter);
            IObjectStorage storage = factoryMgr.createStorage(this.defaultStorageType, this.defaultStorageConfig);
            try {
                boolean notSupport = false;
                try {
                    storage.move(objKey, srcBucket, destBucket);
                }
                catch (UnsupportedOperationException e) {
                    notSupport = true;
                }
                if (!notSupport) break block11;
                try (InputStream input = storage.download(srcBucket, objKey);){
                    storage.upload(destBucket, objKey, input, info);
                }
                catch (IOException e) {
                    throw new ObjectStorageException(e.getMessage(), e);
                }
            }
            catch (ObjectStorageException e) {
                this.metaAdapter.deleteObject(destBucket, objKey);
                throw e;
            }
        }
        this.metaAdapter.deleteObject(srcBucket, objKey);
    }

    public String currentStorageName() {
        if (!this.hasLoad) {
            return null;
        }
        ObjectStorageFactory factory = ObjectStorageFactoryManager.getInstance().getFactory(this.defaultStorageType);
        if (factory == null) {
            return null;
        }
        return factory.getTitle();
    }

    public int removeDeadObjectLink() throws ObjectStorageException {
        Map<String, String> realKeys = this.metaAdapter.getDeadObjectLinks();
        for (Map.Entry<String, String> entry : realKeys.entrySet()) {
            String bucketName = entry.getValue();
            String objRealKey = entry.getKey();
            Bucket bucket = this.metaAdapter.getBucket(bucketName);
            IObjectStorage storage = this.getObjectStorage(bucket);
            storage.deleteObject(bucketName, objRealKey);
            this.metaAdapter.deleteObjectLink(bucketName, objRealKey);
        }
        return realKeys.size();
    }

    public boolean isMigrating() throws ObjectStorageException {
        List<Bucket> buckets = this.listBucket();
        for (Bucket b : buckets) {
            if (!this.metaAdapter.isBucketLocked(b.getName())) continue;
            return true;
        }
        return false;
    }

    public boolean isMigrating(String bucketName) throws ObjectStorageException {
        return this.metaAdapter.isBucketLocked(bucketName);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void migrateBucketWithoutData(Bucket bucket, StorageConfig newStoreInfo, IProgressMonitor monitor) throws ObjectStorageException {
        if (this.isMigrating(bucket.getName())) {
            throw new ObjectStorageException("\u5bf9\u8c61\u5b58\u50a8\u6570\u636e\u6b63\u5728\u8fc1\u79fb\uff1a" + bucket.getName() + "\u3002\u5982\u5728\u5373\u65f6\u4efb\u52a1\u76d1\u63a7\u4e2d\u786e\u8ba4\u8fc1\u79fb\u4efb\u52a1\u5df2\u7ed3\u675f\uff0c\u5c1d\u8bd5\u8fdb\u884c\u91cd\u7f6e\u8fc1\u79fb\u72b6\u6001\u3002");
        }
        if (this.exclusiveStorageConfigs.containsKey(bucket.getName().toUpperCase())) {
            this.logger.warn("\u7531\u4e8e\u3010" + bucket.getName() + "\u3011\u7684\u914d\u7f6e\u4fe1\u606f\u5df2\u901a\u8fc7\u5916\u90e8\u6307\u5b9a\uff0c\u8be5bucket\u7684\u6570\u636e\u4e0d\u8fdb\u884c\u8fc1\u79fb");
            return;
        }
        ObjectStorageFactoryManager factoryMgr = ObjectStorageFactoryManager.getInstance();
        factoryMgr.setMetaAdapter(this.metaAdapter);
        ObjectStorageFactoryManager factory = ObjectStorageFactoryManager.getInstance();
        String newstorageType = this.findStorageTypeByConfig(newStoreInfo);
        IObjectStorage newstorage = factory.createStorage(newstorageType, newStoreInfo);
        monitor.startTask("copy_bucket", 1);
        this.metaAdapter.markBucketStatus(bucket.getName(), "r");
        try {
            monitor.prompt("\u5f00\u59cb\u8fc1\u79fbbucket->" + bucket.getName());
            this.logger.info("\u5f00\u59cb\u8fc1\u79fbbucket->" + bucket.getName() + "\uff08\u5ffd\u7565\u6570\u636e\uff09");
            boolean rs = newstorage.createBucket(bucket);
            if (!rs) {
                this.logger.warn(bucket.getName() + "\u5df2\u5b58\u5728\u76ee\u6807\u4ecb\u8d28\u4e2d");
            }
            monitor.stepIn();
            if (monitor.isCanceled()) {
                this.logger.info("\u8fc1\u79fb\u4efb\u52a1\u53d6\u6d88\uff0c\u6267\u884c\u6570\u636e\u56de\u6eda\u64cd\u4f5c");
                monitor.prompt("\u4efb\u52a1\u53d6\u6d88\uff0c\u6b63\u5728\u8fdb\u884c\u6570\u636e\u56de\u6eda\u64cd\u4f5c");
                newstorage.deleteBucket(bucket.getName());
            }
        }
        finally {
            this.metaAdapter.markBucketStatus(bucket.getName(), "rw");
        }
        newstorage.close();
        monitor.finishTask("copy_bucket");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void migrateBucket(Bucket bucket, StorageConfig newStoreInfo, IProgressMonitor monitor) throws ObjectStorageException {
        if (this.isMigrating(bucket.getName())) {
            throw new ObjectStorageException("\u5bf9\u8c61\u5b58\u50a8\u6570\u636e\u6b63\u5728\u8fc1\u79fb\u3002\u5982\u5728\u5373\u65f6\u4efb\u52a1\u76d1\u63a7\u4e2d\u786e\u8ba4\u8fc1\u79fb\u4efb\u52a1\u5df2\u7ed3\u675f\uff0c\u5c1d\u8bd5\u8fdb\u884c\u91cd\u7f6e\u8fc1\u79fb\u72b6\u6001\u3002");
        }
        if (this.exclusiveStorageConfigs.containsKey(bucket.getName().toUpperCase())) {
            this.logger.warn("\u7531\u4e8e\u3010" + bucket.getName() + "\u3011\u7684\u914d\u7f6e\u4fe1\u606f\u5df2\u901a\u8fc7\u5916\u90e8\u6307\u5b9a\uff0c\u8be5bucket\u7684\u6570\u636e\u4e0d\u8fdb\u884c\u8fc1\u79fb");
            return;
        }
        int objSize = 0;
        int succObjs = 0;
        int loseObjs = 0;
        int existObjs = 0;
        ObjectStorageFactoryManager factory = ObjectStorageFactoryManager.getInstance();
        String newstorageType = this.findStorageTypeByConfig(newStoreInfo);
        try (IObjectStorage newstorage = factory.createStorage(newstorageType, newStoreInfo);
             IObjectStorage storage = this.getObjectStorage(bucket);){
            this.metaAdapter.markBucketStatus(bucket.getName(), "r");
            try {
                monitor.prompt("\u5f00\u59cb\u8fc1\u79fbbucket->" + bucket.getName());
                this.logger.info("\u5f00\u59cb\u8fc1\u79fbbucket->" + bucket.getName());
                boolean rs = newstorage.createBucket(bucket);
                if (!rs) {
                    this.logger.warn(bucket.getName() + "\u5df2\u5b58\u5728\u76ee\u6807\u4ecb\u8d28\u4e2d");
                }
                if ((objSize = this.metaAdapter.getObjectSize(bucket.getName())) <= 0) {
                    return;
                }
                monitor.startTask(bucket.getName(), objSize);
                int count = 0;
                List<ObjectInfo> objs = this.metaAdapter.listObject(bucket.getName(), 0, -1);
                for (ObjectInfo info : objs) {
                    String name;
                    String realKey;
                    if (StringUtils.isNotEmpty((String)info.getName())) {
                        monitor.prompt("\u6b63\u5728\u590d\u5236\u5bf9\u8c61->" + info.getName() + ",\u5df2\u590d\u5236\u5b8c\u6210" + count++ + "\u4e2a\u5bf9\u8c61");
                    } else {
                        monitor.prompt("\u6b63\u5728\u590d\u5236\u5bf9\u8c61->" + info.getKey() + ",\u5df2\u590d\u5236\u5b8c\u6210" + count++ + "\u4e2a\u5bf9\u8c61");
                    }
                    String key = info.getKey();
                    if (bucket.isLinkWhenExist() && (realKey = this.metaAdapter.findObjectStoreKeyByObjKey(bucket.getName(), info.getKey())) != null && !key.equals(realKey)) {
                        if (!this.metaAdapter.existObject(bucket.getName(), realKey) && !newstorage.existObject(bucket.getName(), realKey)) {
                            key = realKey;
                        } else {
                            ++succObjs;
                            monitor.stepIn();
                            continue;
                        }
                    }
                    if (newstorage.existObject(bucket.getName(), key)) {
                        name = StringUtils.isEmpty((String)info.getName()) ? info.getKey() : info.getName();
                        this.logger.warn("\u76ee\u6807\u4ecb\u8d28\u4e2d\u5df2\u5b58\u5728\u5bf9\u8c61\u3010" + name + "\u3011\uff0c\u6240\u5c5ebucket\u3010" + bucket.getName() + "\u3011");
                        ++existObjs;
                        continue;
                    }
                    if (!storage.existObject(bucket.getName(), key)) {
                        name = StringUtils.isEmpty((String)info.getName()) ? info.getKey() : info.getName();
                        this.logger.warn("\u5f53\u524d\u4ecb\u8d28\u4e2d\u5bf9\u8c61\u4e22\u5931\u3010" + name + "\u3011\uff0c\u6240\u5c5ebucket\u3010" + bucket.getName() + "\u3011");
                        ++loseObjs;
                        continue;
                    }
                    InputStream input = storage.download(bucket.getName(), key);
                    try {
                        try {
                            newstorage.upload(bucket.getName(), key, input, info);
                        }
                        finally {
                            input.close();
                        }
                    }
                    catch (Exception e) {
                        throw new ObjectStorageException(e.getMessage(), e);
                    }
                    ++succObjs;
                    monitor.stepIn();
                }
                monitor.finishTask(bucket.getName());
                if (monitor.isCanceled()) {
                    monitor.prompt("\u4efb\u52a1\u53d6\u6d88\uff0c\u6b63\u5728\u8fdb\u884c\u6570\u636e\u56de\u6eda\u64cd\u4f5c");
                    this.logger.info("\u4efb\u52a1\u53d6\u6d88\uff0c\u6b63\u5728\u8fdb\u884c\u6570\u636e\u56de\u6eda\u64cd\u4f5c");
                    newstorage.deleteBucket(bucket.getName());
                }
            }
            finally {
                this.metaAdapter.markBucketStatus(bucket.getName(), "rw");
            }
        }
        StringBuilder b = new StringBuilder();
        b.append(bucket.getName()).append("\u8fc1\u79fb\u5b8c\u6210\u3002\u5bf9\u8c61\u603b\u4e2a\u6570\u4e3a").append(objSize);
        b.append("\u4e2a\u3002\u5176\u4e2d\uff0c\u8fc1\u79fb\u6210\u529f\u7684\u5bf9\u8c61").append(succObjs).append("\u4e2a\uff0c\u5df2\u5b58\u5728\u7684").append(existObjs).append("\u4e2a\uff0c\u4e22\u5931\u7684").append(loseObjs).append("\u4e2a");
        this.logger.info(b.toString());
    }

    private String findStorageTypeByConfig(StorageConfig config) {
        List<String> types = ObjectStorageFactoryManager.getInstance().types();
        for (String type : types) {
            ObjectStorageFactory factory = ObjectStorageFactoryManager.getInstance().getFactory(type);
            if (config.getClass() != factory.newInstanceConfig().getClass()) continue;
            return type;
        }
        return null;
    }
}

