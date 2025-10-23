/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 */
package com.jiuqi.nr.migration.attachment.business;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.nr.migration.attachment.bean.AttachmentInfo;
import com.jiuqi.nr.migration.attachment.bean.IndexFileInfo;
import com.jiuqi.nr.migration.attachment.bean.ReturnObject;
import com.jiuqi.nr.migration.attachment.business.AttachmentMigrationBuilder;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AttachmentMigrationBusiness {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentMigrationBusiness.class);
    private static final String ATTACHMENTDIR = "FILE";
    private static final String ATTACHMENTINDEX = "index.json";

    public static void migrationOpertion(AttachmentInfo attachmentInfo, InputStream attachmentContentIStream, ObjectStorageService objectStorageService) throws ObjectStorageException {
        if (attachmentInfo.getId() == null || attachmentInfo.getGroupId() == null) {
            logger.warn("\u9644\u4ef6\u8fc1\u79fb\uff1a\u9644\u4ef6\uff1a" + attachmentInfo.getFile() + " \u7d22\u5f15\u4fe1\u606f\u6709\u95ee\u9898\u3002");
            ReturnObject.Error("\u7d22\u5f15\u4fe1\u606f\u6709\u8bef\u3002");
            return;
        }
        ObjectInfo info = AttachmentMigrationBuilder.getObjInfo(attachmentInfo);
        if (objectStorageService == null) {
            objectStorageService = AttachmentMigrationBuilder.objService();
        }
        if (objectStorageService.existObject(info.getKey(), 1) || objectStorageService.existObject(info.getKey(), 2)) {
            logger.warn("\u9644\u4ef6\u8fc1\u79fb\uff1a\u9644\u4ef6\uff1a" + info.getName() + " \u7684key: " + info.getKey() + "\u5df2\u5b58\u5728\uff0c\u6267\u884c\u5148\u5220\u540e\u4e0a\u4f20\u64cd\u4f5c\u3002");
            objectStorageService.deleteObject(info.getKey());
        }
        objectStorageService.upload(info.getKey(), attachmentContentIStream, info);
        logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u9644\u4ef6\uff1a" + info.getName() + "\u4e0a\u4f20\u6210\u529f\u3002");
        try {
            attachmentContentIStream.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        ReturnObject.Success();
    }

    public static void batchMigrationOpertion(File unzipFile) throws ObjectStorageException, IOException {
        if (unzipFile != null && unzipFile.isDirectory()) {
            List<AttachmentInfo> attachmentInfos;
            IndexFileInfo indexFileInfo = null;
            HashMap<String, FileInputStream> map = new HashMap<String, FileInputStream>();
            for (File file : unzipFile.listFiles()) {
                File[] attachFiles;
                if (file.isFile() && file.getName().equals(ATTACHMENTINDEX)) {
                    ObjectMapper mapper = new ObjectMapper();
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    indexFileInfo = (IndexFileInfo)mapper.readValue(file, IndexFileInfo.class);
                }
                if (!file.isDirectory() || !file.getName().equals(ATTACHMENTDIR)) continue;
                for (File attachFile : attachFiles = file.listFiles()) {
                    if (attachFile == null) continue;
                    map.put(attachFile.getName(), new FileInputStream(attachFile));
                }
            }
            if (indexFileInfo != null && (attachmentInfos = indexFileInfo.getData()).size() > 0 && map.size() > 0) {
                List attachmentFilenames = attachmentInfos.stream().map(AttachmentInfo::getTitle).collect(Collectors.toList());
                String names = String.join((CharSequence)"; ", attachmentFilenames);
                logger.info("\u9644\u4ef6\u8fc1\u79fb\uff1a\u8fc1\u79fb\u7684\u9644\u4ef6\u6570\uff1a" + attachmentInfos.size() + " \u4e2a\uff1b\u8fc1\u79fb\u7684\u9644\u4ef6\u662f\uff1a" + names);
                ObjectStorageService objectStorageService = AttachmentMigrationBuilder.objService();
                for (AttachmentInfo attachmentInfo : attachmentInfos) {
                    if (map.get(attachmentInfo.getFile()) == null) continue;
                    AttachmentMigrationBusiness.migrationOpertion(attachmentInfo, (InputStream)map.get(attachmentInfo.getFile()), objectStorageService);
                }
            }
        }
    }
}

