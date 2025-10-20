/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.mongodb.MongoClientSettings
 *  com.mongodb.MongoCredential
 *  com.mongodb.MongoException
 *  com.mongodb.ServerAddress
 *  com.mongodb.client.MongoClient
 *  com.mongodb.client.MongoClients
 *  com.mongodb.client.MongoDatabase
 *  com.mongodb.client.gridfs.GridFSBucket
 *  com.mongodb.client.gridfs.GridFSBuckets
 *  com.mongodb.client.gridfs.model.GridFSUploadOptions
 *  org.bson.BsonDocument
 *  org.bson.BsonInt64
 *  org.bson.BsonString
 *  org.bson.BsonValue
 *  org.bson.conversions.Bson
 */
package com.jiuqi.va.attachment.common;

import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.MongoException;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Map;
import org.bson.BsonDocument;
import org.bson.BsonInt64;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;

public class MongoDBUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDBUtil.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static R connectMD(SchemeEntity fileEntity) {
        String config = fileEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        if (schemeConfig == null) {
            return R.error((String)"\u672a\u8bbe\u7f6e\u9644\u4ef6\u65b9\u6848\u914d\u7f6e\u4fe1\u606f");
        }
        if (!(schemeConfig.containsKey("port") && schemeConfig.containsKey("attaddress") && schemeConfig.containsKey("username") && schemeConfig.containsKey("pwd") && schemeConfig.containsKey("dbname"))) {
            return R.error((String)"\u9644\u4ef6\u65b9\u6848\u8fde\u63a5\u4fe1\u606f\u4e0d\u5b8c\u6574");
        }
        int port = Integer.valueOf(schemeConfig.get("port").toString());
        String address = schemeConfig.get("attaddress").toString();
        String username = schemeConfig.get("username").toString();
        String pwd = schemeConfig.get("pwd").toString();
        String dbname = schemeConfig.get("dbname").toString();
        MongoCredential credential = MongoCredential.createCredential((String)username, (String)dbname, (char[])pwd.toCharArray());
        MongoClient mongoClients = MongoClients.create((MongoClientSettings)MongoClientSettings.builder().applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(address, port)))).credential(credential).build());
        MongoDatabase database = mongoClients.getDatabase(dbname);
        try {
            BsonDocument command = new BsonDocument("ping", (BsonValue)new BsonInt64(1L));
            database.runCommand((Bson)command);
            R r = R.ok((String)"\u8fde\u63a5 MongoDB \u6210\u529f");
            return r;
        }
        catch (MongoException me) {
            LOGGER.error(" \u8fde\u63a5 MongoDB \u5931\u8d25" + (Object)((Object)me));
            R r = R.error((String)"\u8fde\u63a5\u5931\u8d25");
            return r;
        }
        finally {
            if (mongoClients != null) {
                mongoClients.close();
            }
        }
    }

    public static MongoClient getMonoDBClient(SchemeEntity fileEntity, Map<String, Object> schemeConfig) {
        int port = Integer.valueOf(schemeConfig.get("port").toString());
        String address = schemeConfig.get("attaddress").toString();
        String username = schemeConfig.get("username").toString();
        String pwd = schemeConfig.get("pwd").toString();
        String dbname = schemeConfig.get("dbname").toString();
        MongoCredential credential = MongoCredential.createCredential((String)username, (String)dbname, (char[])pwd.toCharArray());
        MongoClient mongoClient = MongoClients.create((MongoClientSettings)MongoClientSettings.builder().applyToClusterSettings(builder -> builder.hosts(Arrays.asList(new ServerAddress(address, port)))).credential(credential).build());
        return mongoClient;
    }

    public static MongoDatabase getDatabase(MongoClient mongoClient, String Dbname) {
        return mongoClient.getDatabase(Dbname);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Boolean uploadFileToGridFSByUUID(SchemeEntity schemeEntity, Map<String, Object> schemeConfig) {
        MongoClient mongoClient = MongoDBUtil.getMonoDBClient(schemeEntity, schemeConfig);
        String fileName = schemeEntity.getFileName();
        byte[] files = schemeEntity.getFile();
        String suffix = schemeEntity.getFilePath();
        ByteArrayInputStream inputStream = new ByteArrayInputStream(files);
        String dbname = schemeConfig.get("dbname").toString();
        Integer datasize = (Integer)schemeConfig.get("datasize");
        try {
            GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)MongoDBUtil.getDatabase(mongoClient, dbname), (String)suffix);
            BsonString keyString = new BsonString(schemeEntity.getKey());
            if (datasize != null) {
                GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(Integer.valueOf(datasize * 1024));
                bucket.uploadFromStream((BsonValue)keyString, fileName, (InputStream)inputStream, options);
            } else {
                bucket.uploadFromStream((BsonValue)keyString, fileName, (InputStream)inputStream);
            }
        }
        catch (Exception e) {
            LOGGER.error(" \u6587\u4e0a\u4f20 MongoDB \u5931\u8d25", e);
            Boolean bl = false;
            return bl;
        }
        finally {
            try {
                if (inputStream != null) {
                    ((InputStream)inputStream).close();
                }
                if (mongoClient != null) {
                    mongoClient.close();
                }
            }
            catch (IOException e) {
                LOGGER.error(" MongoDB \u5173\u95ed\u8f93\u5165\u6d41\u5931\u8d25", e);
            }
        }
        return true;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void downloadFileFromMD(SchemeEntity fileEntity, String collectionName) {
        block17: {
            String config = fileEntity.getSchemeConfig();
            Map schemeConfig = JSONUtil.parseMap((String)config);
            InputStream bis = null;
            OutputStream os = null;
            String fileName = fileEntity.getFileName();
            String dbname = schemeConfig.get("dbname").toString();
            MongoClient mongoClient = MongoDBUtil.getMonoDBClient(fileEntity, schemeConfig);
            try {
                MongoDatabase mongoDatabase = MongoDBUtil.getDatabase(mongoClient, dbname);
                if (mongoDatabase == null) break block17;
                RequestContextUtil.setResponseContentType((String)"application/x-download");
                try {
                    RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8''" + URLEncoder.encode(new String(fileName.getBytes("UTF-8"), "iso-8859-1"), "iso-8859-1")));
                }
                catch (Exception e) {
                    LOGGER.error(" MongoDB \u4e0b\u8f7d\u5931\u8d25", e);
                }
                GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)mongoDatabase, (String)collectionName);
                BsonString keyString = new BsonString(fileEntity.getKey());
                bis = bucket.openDownloadStream((BsonValue)keyString);
                os = RequestContextUtil.getOutputStream();
                int cont = 0;
                while ((cont = bis.read()) != -1) {
                    os.write(cont);
                }
            }
            catch (IOException e) {
                LOGGER.error(" MongoDB \u4e0b\u8f7d\u5931\u8d25", e);
            }
            finally {
                try {
                    if (bis != null) {
                        bis.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                    if (mongoClient != null) {
                        mongoClient.close();
                    }
                }
                catch (IOException e) {
                    LOGGER.error(" MongoDB \u4e0b\u8f7d\u5173\u95ed\u5931\u8d25", e);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] getFileFromMD(SchemeEntity fileEntity, String collectionName) {
        String config = fileEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        InputStream bis = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String dbname = schemeConfig.get("dbname").toString();
        MongoClient mongoClient = MongoDBUtil.getMonoDBClient(fileEntity, schemeConfig);
        try {
            MongoDatabase mongoDatabase = MongoDBUtil.getDatabase(mongoClient, dbname);
            if (mongoDatabase != null) {
                GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)mongoDatabase, (String)collectionName);
                BsonString keyString = new BsonString(fileEntity.getKey());
                bis = bucket.openDownloadStream((BsonValue)keyString);
                StreamUtils.copy(bis, (OutputStream)os);
                byte[] byArray = os.toByteArray();
                return byArray;
            }
        }
        catch (IOException e) {
            LOGGER.error(" MongoDB \u4e0b\u8f7d\u5931\u8d25", e);
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                os.close();
                if (mongoClient != null) {
                    mongoClient.close();
                }
            }
            catch (IOException e) {
                LOGGER.error(" MongoDB \u4e0b\u8f7d\u5173\u95ed\u5931\u8d25", e);
            }
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Boolean deleteFileById(SchemeEntity schemeEntity, String collectionName) {
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        MongoClient mongoClient = MongoDBUtil.getMonoDBClient(schemeEntity, schemeConfig);
        String dbname = schemeConfig.get("dbname").toString();
        try {
            MongoDatabase mongoDatabase = MongoDBUtil.getDatabase(mongoClient, dbname);
            GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)mongoDatabase, (String)collectionName);
            BsonString keyString = new BsonString(schemeEntity.getKey());
            bucket.delete((BsonValue)keyString);
        }
        catch (Exception e) {
            LOGGER.error(" MongoDB \u5220\u9664\u5931\u8d25" + e);
            Boolean bl = false;
            return bl;
        }
        finally {
            if (mongoClient != null) {
                mongoClient.close();
            }
        }
        return true;
    }
}

