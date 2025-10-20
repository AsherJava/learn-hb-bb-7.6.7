/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mongodb.BasicDBObject
 *  com.mongodb.client.MongoClient
 *  com.mongodb.client.MongoClients
 *  com.mongodb.client.MongoCursor
 *  com.mongodb.client.MongoDatabase
 *  com.mongodb.client.gridfs.GridFSBucket
 *  com.mongodb.client.gridfs.GridFSBuckets
 *  com.mongodb.client.gridfs.GridFSFindIterable
 *  com.mongodb.client.gridfs.model.GridFSFile
 *  com.mongodb.client.gridfs.model.GridFSUploadOptions
 *  org.bson.conversions.Bson
 */
package com.jiuqi.bi.oss.storage.mongo;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.storage.AbstractObjectStorage;
import com.jiuqi.bi.oss.storage.IObjectMetaAdapter;
import com.jiuqi.bi.oss.storage.MultipartObjectUploader;
import com.jiuqi.bi.oss.storage.StorageConfig;
import com.jiuqi.bi.oss.storage.StorageUtils;
import com.jiuqi.bi.oss.storage.TemporaryFileInputStream;
import com.jiuqi.bi.oss.storage.mongo.MongoStorageConfig;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MongoStorage
extends AbstractObjectStorage {
    private MongoClient client;
    private MongoDatabase mongoDb;
    private int chunkSize;
    private Logger logger = LoggerFactory.getLogger(MongoStorage.class);

    public MongoStorage(IObjectMetaAdapter metaAdapter) {
        super(metaAdapter);
    }

    @Override
    public void initialize(StorageConfig context) throws ObjectStorageException {
        super.initialize(context);
        MongoStorageConfig cxt = (MongoStorageConfig)context;
        this.chunkSize = cxt.getChunkSize();
        if (this.isHostConnectable(cxt.getHost(), cxt.getPort())) {
            StringBuilder connectionString = new StringBuilder();
            connectionString.append("mongodb://");
            if (cxt.isCredential()) {
                connectionString.append(cxt.getUsername());
                connectionString.append(":");
                connectionString.append(cxt.getPwd());
                connectionString.append("@");
            }
            connectionString.append(cxt.getHost());
            connectionString.append(":");
            connectionString.append(cxt.getPort());
            connectionString.append("/");
            connectionString.append(cxt.getDbName());
            connectionString.append("?maxIdleTimeMS=60000");
            this.client = MongoClients.create((String)connectionString.toString());
            this.logger.info("[MongoStorage.initialize] \u521b\u5efa MongoDB \u8fde\u63a5, {}", (Object)this.client);
            this.mongoDb = this.client.getDatabase(cxt.getDbName());
        } else {
            this.logger.error("\u65e0\u6cd5\u8fde\u63a5mongoDB\u6570\u636e\u5e93\uff1ahost=" + cxt.getHost() + ";port=" + cxt.getPort());
        }
    }

    @Override
    public boolean createBucket(Bucket bucket) throws ObjectStorageException {
        StorageUtils.checkBucketName(bucket.getName());
        GridFSBuckets.create((MongoDatabase)this.mongoDb, (String)bucket.getName());
        return true;
    }

    @Override
    public boolean deleteBucket(String bucketName) throws ObjectStorageException {
        if (this.mongoDb == null) {
            throw new ObjectStorageException("MongoDB\u8fde\u63a5\u5931\u8d25");
        }
        GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)this.mongoDb, (String)bucketName);
        bucket.drop();
        return true;
    }

    @Override
    public void upload(String bucketName, String key, InputStream input, ObjectInfo info) throws ObjectStorageException {
        GridFSBucket bucket;
        GridFSFile file;
        GridFSUploadOptions options = new GridFSUploadOptions();
        if (this.chunkSize > 0) {
            options.chunkSizeBytes(Integer.valueOf(this.chunkSize));
        }
        if ((file = this.findOne(bucket = GridFSBuckets.create((MongoDatabase)this.mongoDb, (String)bucketName), key)) != null) {
            bucket.delete(file.getObjectId());
        }
        bucket.uploadFromStream(key, input, options);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public InputStream download(String bucketName, String key) throws ObjectStorageException {
        try {
            File tmpFile = StorageUtils.createTemporaryFile(key, System.currentTimeMillis());
            try (FileOutputStream fos = new FileOutputStream(tmpFile);){
                GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)this.mongoDb, (String)bucketName);
                bucket.downloadToStream(key, (OutputStream)fos);
            }
            return new TemporaryFileInputStream(tmpFile);
        }
        catch (IOException e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
    }

    @Override
    public void download(String bucketName, String key, OutputStream output) throws ObjectStorageException {
        GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)this.mongoDb, (String)bucketName);
        bucket.downloadToStream(key, output);
    }

    @Override
    public boolean existObject(String bucketName, String key) throws ObjectStorageException {
        GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)this.mongoDb, (String)bucketName);
        GridFSFile file = this.findOne(bucket, key);
        return file != null;
    }

    @Override
    public boolean deleteObject(String bucketName, String key) throws ObjectStorageException {
        GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)this.mongoDb, (String)bucketName);
        GridFSFile file = this.findOne(bucket, key);
        if (file != null) {
            bucket.delete(file.getObjectId());
            return true;
        }
        return false;
    }

    @Override
    public MultipartObjectUploader createMultipartObjectUploader(Bucket bucket) throws ObjectStorageException {
        return null;
    }

    @Override
    public void close() throws ObjectStorageException {
        this.logger.info("[MongoStorage.close] \u5173\u95ed MongoDB \u8fde\u63a5, {}", (Object)this.client);
        if (this.client == null) {
            throw new ObjectStorageException("MongoDB\u8fde\u63a5\u5931\u8d25");
        }
        this.client.close();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private GridFSFile findOne(GridFSBucket bucket, String keyScript) throws ObjectStorageException {
        BasicDBObject queryObject = new BasicDBObject("filename", (Object)keyScript);
        GridFSFindIterable ls = bucket.find((Bson)queryObject);
        try (MongoCursor mongoCursor = ls.cursor();){
            if (!mongoCursor.hasNext()) return null;
            GridFSFile gridFSFile = (GridFSFile)mongoCursor.next();
            return gridFSFile;
        }
        catch (Exception e) {
            throw new ObjectStorageException("Mongo\u67e5\u627e\u6587\u4ef6\u5f02\u5e38", e);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private boolean isHostConnectable(String host, int port) {
        boolean bl;
        Socket socket = new Socket();
        try {
            socket.connect(new InetSocketAddress(host, port));
            bl = true;
        }
        catch (IOException e) {
            boolean bl2;
            try {
                bl2 = false;
            }
            catch (Throwable throwable) {
                try {
                    socket.close();
                    throw throwable;
                }
                catch (IOException e2) {
                    return false;
                }
            }
            socket.close();
            return bl2;
        }
        socket.close();
        return bl;
    }
}

