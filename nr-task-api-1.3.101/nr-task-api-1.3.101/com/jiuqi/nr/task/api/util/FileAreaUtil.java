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
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.task.api.util;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.task.api.exception.FileAreaException;
import com.jiuqi.nr.task.api.file.dto.FileAreaDTO;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FileAreaUtil {
    private static final Logger logger = LoggerFactory.getLogger(FileAreaUtil.class);
    private static final String AREA_NAME = "NR_TASK_TEMP";
    private static final int BUCKET_EXPIRE_MILLS = 0x6DDD00;
    public static final String FILE_AREA_NAME_REGEXP = "^[0-9a-zA-Z_]{1,}$";

    public static ObjectStorageService getObjService(FileAreaDTO fileAreaDTO) {
        ObjectStorageService objService;
        String name;
        String string = name = fileAreaDTO.getDefaultArea() ? AREA_NAME : fileAreaDTO.getAreaName();
        if (!fileAreaDTO.getDefaultArea()) {
            FileAreaUtil.checkName(name);
        }
        int time = fileAreaDTO.getDefaultArea() ? 0x6DDD00 : fileAreaDTO.getAreaExpireMills();
        try {
            BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
            boolean exist = bucketService.existBucket(name);
            if (!exist) {
                Bucket bucket = new Bucket(name);
                bucket.setDesc("\u4efb\u52a1\u8bbe\u8ba1\u4e34\u65f6\u6587\u4ef6");
                bucket.setExpireTime(time);
                bucketService.createBucket(bucket);
                bucketService.close();
            }
            objService = ObjectStorageManager.getInstance().createObjectService(name);
        }
        catch (Exception e) {
            throw new FileAreaException(" bucket get failed " + e.getMessage(), e);
        }
        return objService;
    }

    public static ObjectInfo upload(String fileName, InputStream input, String fileKey, FileAreaDTO fileAreaDTO) {
        ObjectInfo objectInfo;
        ObjectStorageService objectStorageService = null;
        try {
            String currentUser = FileAreaUtil.getUserName();
            ObjectInfo info = new ObjectInfo();
            String extension = FileAreaUtil.tryParseExtension(fileName);
            if (StringUtils.hasText(FileAreaUtil.tryParseExtension(fileName))) {
                info.setExtension(extension);
            }
            if (StringUtils.hasText(fileKey)) {
                info.setKey(fileKey);
            } else {
                fileKey = FileAreaUtil.generateFileKey();
                info.setKey(fileKey);
            }
            info.setName(fileName);
            info.setOwner(currentUser);
            objectStorageService = FileAreaUtil.getObjService(fileAreaDTO);
            objectStorageService.upload(fileKey, input, info);
            objectInfo = info;
        }
        catch (Exception e) {
            try {
                throw new FileAreaException("upload file failed." + e.getMessage(), e);
            }
            catch (Throwable throwable) {
                FileAreaUtil.closeObjService(objectStorageService);
                throw throwable;
            }
        }
        FileAreaUtil.closeObjService(objectStorageService);
        return objectInfo;
    }

    private static String getUserName() {
        ContextUser operator = NpContextHolder.getContext().getUser();
        return operator == null ? null : operator.getName();
    }

    private static String tryParseExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int extensionPos = fileName.lastIndexOf(".");
        if (extensionPos <= 0) {
            return null;
        }
        return fileName.substring(extensionPos);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static byte[] fileDownLoad(String fileKey, OutputStream outputStream, FileAreaDTO fileAreaDTO) {
        boolean fileExist = false;
        try {
            fileExist = FileAreaUtil.getObjService(fileAreaDTO).existObject(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        if (!fileExist) {
            throw new FileAreaException("file is not exist.");
        }
        ObjectStorageService objService = null;
        try {
            objService = FileAreaUtil.getObjService(fileAreaDTO);
            try (InputStream downloadStream = objService.download(fileKey);){
                if (downloadStream != null) {
                    if (outputStream != null) {
                        FileAreaUtil.writeInput2Output(outputStream, downloadStream);
                        return null;
                    }
                    try (ByteArrayOutputStream os = new ByteArrayOutputStream();){
                        FileAreaUtil.writeInput2Output(os, downloadStream);
                        byte[] byArray = os.toByteArray();
                        return byArray;
                    }
                }
                byte[] byArray = null;
                return byArray;
            }
        }
        catch (Exception e) {
            throw new FileAreaException("failed to down load file." + e.getMessage(), e);
        }
    }

    public static void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    public static String generateFileKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    private static void checkName(String name) {
        Pattern nameCompile = Pattern.compile(FILE_AREA_NAME_REGEXP);
        Matcher titleMatcher = nameCompile.matcher(name);
        if (!titleMatcher.matches()) {
            throw new FileAreaException("\u540d\u79f0\u5fc5\u987b\u7531\u6570\u5b57\u3001\u5b57\u6bcd\u3001\u4e0b\u5212\u7ebf\u7ec4\u6210");
        }
        if (name.length() > 18 || name.length() < 1) {
            throw new FileAreaException("\u540d\u79f0\u603b\u957f\u5ea6\u4e3a1~18");
        }
    }

    public static void closeObjService(ObjectStorageService objService) {
        if (objService != null) {
            try {
                objService.close();
            }
            catch (ObjectStorageException e) {
                throw new FileAreaException(" service closed failed." + e.getMessage(), e);
            }
        }
    }
}

