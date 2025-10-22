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
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.fileupload.exception.FileOssException
 */
package com.jiuqi.nr.designer.util;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.fileupload.exception.FileOssException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OssUploadUtil {
    private static final Logger logger = LoggerFactory.getLogger(OssUploadUtil.class);
    private static final String AREA_NAME = "NR_DESIGNER_TEMP";
    private static final int BUCKET_EXPIRE_MILLS = 3600000;

    public static ObjectStorageService getObjService() throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(AREA_NAME);
        if (!exist) {
            Bucket bucket = new Bucket(AREA_NAME);
            bucket.setDesc("\u5efa\u6a21\u5bfc\u51fa\u53c2\u6570");
            bucket.setExpireTime(3600000);
            bucketService.createBucket(bucket);
            bucketService.close();
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(AREA_NAME);
        return objService;
    }

    public static ObjectInfo upload(String fileName, InputStream input, String fileKey) {
        ObjectStorageService objectStorageService = null;
        try {
            String currentUser = OssUploadUtil.getUserName();
            ObjectInfo info = new ObjectInfo();
            String extension = OssUploadUtil.tryParseExtension(fileName);
            if (StringUtils.isNotEmpty((String)OssUploadUtil.tryParseExtension(fileName))) {
                info.setExtension(extension);
            }
            info.setKey(fileKey);
            info.setName(fileName);
            info.setOwner(currentUser);
            objectStorageService = OssUploadUtil.getObjService();
            objectStorageService.upload(fileKey, input, info);
            ObjectInfo objectInfo = info;
            return objectInfo;
        }
        catch (Exception e) {
            throw new FileOssException("faild to save file." + e.getMessage(), (Throwable)e);
        }
        finally {
            if (objectStorageService != null) {
                try {
                    objectStorageService.close();
                }
                catch (ObjectStorageException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
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

    public static void fileDownLoad(String fileKey, OutputStream outputStream) {
        boolean b = false;
        try {
            b = OssUploadUtil.getObjService().existObject(fileKey);
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
        if (!b) {
            throw new FileOssException("file is not exist.");
        }
        try {
            InputStream download = OssUploadUtil.getObjService().download(fileKey);
            if (download != null) {
                OssUploadUtil.writeInput2Output(outputStream, download);
            }
        }
        catch (Exception e) {
            throw new FileOssException("failed to down load file." + e.getMessage(), (Throwable)e);
        }
    }

    private static void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }
}

