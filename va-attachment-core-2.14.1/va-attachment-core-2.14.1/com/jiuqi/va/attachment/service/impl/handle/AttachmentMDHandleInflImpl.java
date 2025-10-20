/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.config.ConfigItem
 *  com.jiuqi.va.attachment.config.ElementType
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.attachment.domain.exception.VaAttachmentException
 *  com.jiuqi.va.domain.common.AesCipherUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.mongodb.client.MongoClient
 *  com.mongodb.client.MongoDatabase
 *  com.mongodb.client.gridfs.GridFSBucket
 *  com.mongodb.client.gridfs.GridFSBuckets
 *  com.mongodb.client.gridfs.GridFSDownloadStream
 *  com.mongodb.client.gridfs.model.GridFSUploadOptions
 *  org.bson.BsonString
 *  org.bson.BsonValue
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl.handle;

import com.jiuqi.va.attachment.common.AttachmentConst;
import com.jiuqi.va.attachment.common.FileUtil;
import com.jiuqi.va.attachment.common.MongoDBUtil;
import com.jiuqi.va.attachment.config.ConfigItem;
import com.jiuqi.va.attachment.config.ElementType;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.domain.exception.VaAttachmentException;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.utils.VaAttachmentIOUtils;
import com.jiuqi.va.domain.common.AesCipherUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipOutputStream;
import org.bson.BsonString;
import org.bson.BsonValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentMDHandleInflImpl
implements AttachmentHandleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentMDHandleInflImpl.class);
    @Autowired
    private AttachmentBizHelpService bizHelpService;
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;

    public String getStoreTitle() {
        return "MongoDB";
    }

    public int getStoremode() {
        return AttachmentConst.MONGODB;
    }

    public R upload(MultipartFile file, AttachmentBizDTO bizAttachmentDTO, SchemeEntity schemeEntity, AttachmentModeDTO attmode) {
        long fileSize;
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        String modeConfig = schemeEntity.getModeConfig();
        Map modeConfigMap = JSONUtil.parseMap((String)modeConfig);
        String dbname = schemeConfig.get("dbname").toString();
        String collectionflag = modeConfigMap.get("collectionflag").toString();
        String qcode = bizAttachmentDTO.getQuotecode();
        String fileName = file.getOriginalFilename();
        R fileCheckRs = this.bizHelpService.checkFile(attmode, fileName, fileSize = file.getSize());
        if (fileCheckRs.getCode() != 0) {
            return fileCheckRs;
        }
        String schemename = schemeEntity.getSchemename();
        String fileNameNew = FileUtil.renameToUUID(fileName);
        String year = qcode.substring(0, 4);
        String month = qcode.substring(4, 6);
        String filePath = "";
        String mongoDBPath = "";
        Integer degree = schemeEntity.getDegree();
        if (degree == 1) {
            filePath = "/" + dbname + "/" + collectionflag + "_" + year + "/";
            mongoDBPath = collectionflag + "_" + year;
        } else if (degree == 2) {
            filePath = "/" + dbname + "/" + collectionflag + "_" + year + month + "/";
            mongoDBPath = collectionflag + "_" + year + month;
        }
        try {
            schemeEntity.setFile(file.getBytes());
        }
        catch (IOException e1) {
            LOGGER.error(" \u6587\u4ef6 MongoDB \u4e0a\u4f20\u5931\u8d25\uff1a", e1);
        }
        schemeEntity.setFileName(fileNameNew);
        schemeEntity.setFilePath(mongoDBPath);
        BigDecimal bd = new BigDecimal(file.getSize() / 1024L);
        bizAttachmentDTO.setFilesize(bd);
        bizAttachmentDTO.setName(fileName);
        bizAttachmentDTO.setFilepath(filePath + fileNameNew);
        bizAttachmentDTO.setSuffix(qcode.substring(0, 6));
        bizAttachmentDTO.setId(this.bizHelpService.getFileId(fileName, bizAttachmentDTO));
        if (bizAttachmentDTO.getCreatetime() == null) {
            bizAttachmentDTO.setCreatetime(new Date());
        }
        bizAttachmentDTO.setSchemename(schemename);
        schemeEntity.setKey(bizAttachmentDTO.getId().toString());
        if (bizAttachmentDTO.getStatus() == null) {
            bizAttachmentDTO.setStatus(Integer.valueOf(1));
        }
        boolean flag = false;
        flag = MongoDBUtil.uploadFileToGridFSByUUID(schemeEntity, schemeConfig);
        if (flag && this.bizHelpService.add(fileName, bizAttachmentDTO) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean copyFile(AttachmentBizDTO param, SchemeEntity schemeEntity, String newQuoteCode) {
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        MongoClient mongoClient = MongoDBUtil.getMonoDBClient(schemeEntity, schemeConfig);
        String oldPath = param.getFilepath();
        String suffix = oldPath.split("/")[2];
        String dbname = schemeConfig.get("dbname").toString();
        Integer datasize = (Integer)schemeConfig.get("datasize");
        GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)MongoDBUtil.getDatabase(mongoClient, dbname), (String)suffix);
        BsonString keyString = new BsonString(param.getId().toString());
        GridFSDownloadStream inputStream = bucket.openDownloadStream((BsonValue)keyString);
        try {
            UUID uuid = UUID.randomUUID();
            BsonString newKeyString = new BsonString(uuid.toString());
            String fileName = uuid + param.getName().substring(param.getName().lastIndexOf("."));
            if (datasize != null) {
                GridFSUploadOptions options = new GridFSUploadOptions().chunkSizeBytes(Integer.valueOf(datasize * 1024));
                bucket.uploadFromStream((BsonValue)newKeyString, fileName, (InputStream)inputStream, options);
            } else {
                bucket.uploadFromStream((BsonValue)newKeyString, fileName, (InputStream)inputStream);
            }
            String newPath = oldPath.substring(0, oldPath.lastIndexOf("/")) + "/" + fileName;
            param.setId(uuid);
            param.setFilepath(newPath);
            param.setQuotecode(newQuoteCode);
            param.setSuffix(newQuoteCode.split("-")[0]);
            boolean bl = this.bizAttachmentDao.add((AttachmentBizDO)param) > 0;
            return bl;
        }
        catch (Exception e) {
            LOGGER.error(" \u6587\u4e0a\u4f20 MongoDB \u5931\u8d25", e);
            boolean bl = false;
            return bl;
        }
        finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (mongoClient != null) {
                    mongoClient.close();
                }
            }
            catch (IOException e) {
                LOGGER.error(" MongoDB \u5173\u95ed\u8f93\u5165\u6d41\u5931\u8d25", e);
            }
        }
    }

    public R remove(AttachmentBizDTO attachmentBizDTO) {
        boolean flag = false;
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDTO.getSchemename());
        schemeEntity.setFilePath(attachmentBizDTO.getFilepath());
        String qcode = attachmentBizDTO.getQuotecode();
        if (schemeEntity.getDegree() == 1) {
            schemeEntity.setSuffix(qcode.substring(0, 4));
        } else if (schemeEntity.getDegree() == 2) {
            schemeEntity.setSuffix(qcode.substring(0, 6));
        }
        AttachmentBizDO attachmentBizDO = new AttachmentBizDO();
        attachmentBizDO.setQuotecode(qcode);
        attachmentBizDO.setSuffix(qcode.substring(0, 6));
        schemeEntity.setKey(attachmentBizDTO.getId().toString());
        String filePath = schemeEntity.getFilePath();
        String[] resultPath = filePath.split("/");
        flag = MongoDBUtil.deleteFileById(schemeEntity, resultPath[2]);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    public void download(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        param.setSuffix(qcode.substring(0, 6));
        AttachmentBizDTO attachmentBizDO = this.bizHelpService.get((AttachmentBizDO)param);
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDO.getSchemename());
        schemeEntity.setFileName(attachmentBizDO.getName());
        schemeEntity.setFilePath(attachmentBizDO.getFilepath());
        schemeEntity.setKey(attachmentBizDO.getId().toString());
        String filePath = attachmentBizDO.getFilepath();
        String[] resultPath = filePath.split("/");
        MongoDBUtil.downloadFileFromMD(schemeEntity, resultPath[2]);
    }

    public byte[] getFile(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        param.setSuffix(qcode.substring(0, 6));
        AttachmentBizDTO attachmentBizDO = this.bizHelpService.get((AttachmentBizDO)param);
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDO.getSchemename());
        schemeEntity.setFileName(attachmentBizDO.getName());
        schemeEntity.setFilePath(attachmentBizDO.getFilepath());
        schemeEntity.setKey(attachmentBizDO.getId().toString());
        String filePath = attachmentBizDO.getFilepath();
        String[] resultPath = filePath.split("/");
        return MongoDBUtil.getFileFromMD(schemeEntity, resultPath[2]);
    }

    public void zipDownLoad(AttachmentBizDTO param, ZipOutputStream zipOutputStream, String zipPath) {
        SchemeEntity schemeEntity = (SchemeEntity)param.getExtInfo("schemeEntity");
        schemeEntity.setFileName(param.getName());
        schemeEntity.setFilePath(param.getFilepath());
        UUID id = param.getId();
        schemeEntity.setKey(id.toString());
        String filePath = param.getFilepath();
        String[] resultPath = filePath.split("/");
        AttachmentMDHandleInflImpl.zipDownLoadByMd(schemeEntity, resultPath[2], zipOutputStream, zipPath);
    }

    private static void zipDownLoadByMd(SchemeEntity fileEntity, String collectionName, ZipOutputStream zipOutputStream, String zipPath) {
        String config = fileEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        String dbname = schemeConfig.get("dbname").toString();
        try (MongoClient mongoClient = MongoDBUtil.getMonoDBClient(fileEntity, schemeConfig);){
            MongoDatabase mongoDatabase = MongoDBUtil.getDatabase(mongoClient, dbname);
            GridFSBucket bucket = GridFSBuckets.create((MongoDatabase)mongoDatabase, (String)collectionName);
            BsonString keyString = new BsonString(fileEntity.getKey());
            try (GridFSDownloadStream is = bucket.openDownloadStream((BsonValue)keyString);){
                VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipPath, (InputStream)is);
            }
            catch (IOException e) {
                LOGGER.error("MongoDB\u6587\u4ef6\u6d41\u5904\u7406\u5931\u8d25 [\u6587\u4ef6ID:{}]\uff1a{}", (Object)fileEntity.getKey(), (Object)e.getMessage());
                throw new VaAttachmentException("\u6587\u4ef6\u6d41\u5904\u7406\u5f02\u5e38", (Throwable)e);
            }
        }
        catch (Exception e) {
            LOGGER.error("MongoDB\u8fde\u63a5\u5f02\u5e38 [\u6570\u636e\u5e93:{}]\uff1a{}", (Object)dbname, (Object)e.getMessage());
            throw new VaAttachmentException("\u6570\u636e\u5e93\u8fde\u63a5\u5f02\u5e38", (Throwable)e);
        }
    }

    public List<ConfigItem> getSchemeConfigItems() {
        ArrayList<ConfigItem> configItems = new ArrayList<ConfigItem>();
        ConfigItem address = new ConfigItem();
        address.setName("attaddress");
        address.setTitle("\u670d\u52a1\u5730\u5740");
        address.setElementType(ElementType.TEXT);
        address.setRequired(true);
        configItems.add(address);
        ConfigItem port = new ConfigItem();
        port.setName("port");
        port.setTitle("\u670d\u52a1\u7aef\u53e3");
        port.setElementType(ElementType.TEXT);
        port.setRequired(true);
        configItems.add(port);
        ConfigItem dbname = new ConfigItem();
        dbname.setName("dbname");
        dbname.setTitle("\u6570\u636e\u5e93\u540d\u79f0");
        dbname.setElementType(ElementType.TEXT);
        dbname.setRequired(true);
        configItems.add(dbname);
        ConfigItem username = new ConfigItem();
        username.setName("username");
        username.setTitle("\u7528\u6237\u540d");
        username.setElementType(ElementType.TEXT);
        username.setRequired(true);
        configItems.add(username);
        ConfigItem pwd = new ConfigItem();
        pwd.setName("pwd");
        pwd.setTitle("\u5bc6\u7801");
        pwd.setElementType(ElementType.PASSWORD);
        pwd.setRequired(true);
        configItems.add(pwd);
        ConfigItem datasize = new ConfigItem();
        datasize.setName("datasize");
        datasize.setTitle("\u6570\u636e\u5757\u5927\u5c0f");
        datasize.setElementType(ElementType.NUMBER);
        datasize.setToolTip("\u6587\u4ef6\u5206\u7247\u5927\u5c0f");
        configItems.add(datasize);
        ConfigItem authflag = new ConfigItem();
        authflag.setName("authflag");
        authflag.setTitle("\u542f\u7528\u8ba4\u8bc1");
        authflag.setElementType(ElementType.BOOLEAN);
        configItems.add(authflag);
        return configItems;
    }

    public List<ConfigItem> getModelConfigItems() {
        ArrayList<ConfigItem> configItems = new ArrayList<ConfigItem>();
        ConfigItem collectionflag = new ConfigItem();
        collectionflag.setName("collectionflag");
        collectionflag.setTitle("\u96c6\u5408\u6807\u8bc6");
        collectionflag.setToolTip("\u5b58\u50a8\u6587\u6863\u7684\u96c6\u5408\u540d\u79f0");
        collectionflag.setElementType(ElementType.TEXT);
        collectionflag.setRequired(true);
        configItems.add(collectionflag);
        return configItems;
    }

    public void processSchemeConfig(AttachmentSchemeDO attachmentSchemeDO) {
        if (attachmentSchemeDO == null || !StringUtils.hasText(attachmentSchemeDO.getConfig())) {
            return;
        }
        Map config = JSONUtil.parseMap((String)attachmentSchemeDO.getConfig());
        if (config != null && config.get("pwd") != null && StringUtils.hasText(config.get("pwd").toString())) {
            config.put("pwd", AesCipherUtil.encode((String)config.get("pwd").toString()));
            attachmentSchemeDO.setConfig(JSONUtil.toJSONString((Object)config));
        }
    }

    public void parseSchemeConfig(AttachmentSchemeDO attachmentSchemeDO) {
        if (attachmentSchemeDO == null || !StringUtils.hasText(attachmentSchemeDO.getConfig())) {
            return;
        }
        Map config = JSONUtil.parseMap((String)attachmentSchemeDO.getConfig());
        if (config != null && config.get("pwd") != null && StringUtils.hasText(config.get("pwd").toString())) {
            config.put("pwd", AesCipherUtil.decode((String)config.get("pwd").toString()));
            attachmentSchemeDO.setConfig(JSONUtil.toJSONString((Object)config));
        }
    }

    public boolean testConnectFlag() {
        return true;
    }

    public R checkModeConfig(AttachmentModeDO attachmentModeDO) {
        if (!StringUtils.hasText(attachmentModeDO.getConfig())) {
            return R.ok();
        }
        Map config = JSONUtil.parseMap((String)attachmentModeDO.getConfig());
        if (config.get("collectionflag") == null) {
            return R.error((String)"\u96c6\u5408\u6807\u8bc6\u4e0d\u5141\u8bb8\u4e3a\u7a7a");
        }
        String collectionflag = config.get("collectionflag").toString();
        if (collectionflag.startsWith("system.")) {
            return R.error((String)"\u96c6\u5408\u6807\u8bc6\u4e0d\u5141\u8bb8\u4ee5\u2018system.\u2019\u5f00\u5934");
        }
        Pattern pattern = Pattern.compile("^[A-Za-z0-9_][A-Za-z0-9_]*$");
        Matcher check = pattern.matcher(collectionflag);
        if (!check.matches()) {
            return R.error((String)"\u96c6\u5408\u6807\u8bc6\u5fc5\u987b\u4ee5\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u4e0b\u5212\u7ebf\u5f00\u5934,\u53ef\u5305\u542b\u5b57\u6bcd\u3001\u6570\u5b57\u3001\u4e0b\u5212\u7ebf");
        }
        return R.ok();
    }
}

