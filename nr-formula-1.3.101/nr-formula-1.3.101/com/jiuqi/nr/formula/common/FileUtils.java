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
 *  com.jiuqi.np.core.context.NpContext
 *  com.jiuqi.np.core.context.NpContextHolder
 *  com.jiuqi.nr.file.exception.FileException
 *  com.jiuqi.nr.file.impl.FileInfoBuilder
 *  com.jiuqi.nr.file.utils.FileUtils
 *  com.jiuqi.nr.fileupload.exception.FileOssException
 */
package com.jiuqi.nr.formula.common;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.np.core.context.NpContext;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.file.exception.FileException;
import com.jiuqi.nr.file.impl.FileInfoBuilder;
import com.jiuqi.nr.fileupload.exception.FileOssException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class FileUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);
    private static final String formulaFileName = "NR_FORMULA_IMPORT";

    public static void deleteAllFilesOfDir(File path) {
        if (path.exists()) {
            if (path.isFile()) {
                boolean delete = path.delete();
                if (!delete) {
                    logger.error("\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u5931\u8d25");
                }
                return;
            }
            try {
                org.apache.commons.io.FileUtils.deleteDirectory(path);
            }
            catch (Exception e) {
                logger.error("\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u5931\u8d25" + e.getMessage());
            }
        } else {
            logger.info("\u8981\u5220\u9664\u4e34\u65f6\u6587\u4ef6\u4e0d\u5b58\u5728");
        }
    }

    public static void deleteAllFilesOfDirByPath(String path) {
        if (StringUtils.hasText(path)) {
            File tempFile2 = new File(path);
            FileUtils.deleteAllFilesOfDir(tempFile2);
        }
    }

    public static ObjectStorageService objService() throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(formulaFileName);
        if (!exist) {
            Bucket bucket = new Bucket(formulaFileName);
            bucket.setDesc("\u516c\u5f0f\u5bfc\u5165\u4e34\u65f6\u6587\u4ef6");
            bucketService.createBucket(bucket);
            bucketService.close();
        }
        return ObjectStorageManager.getInstance().createObjectService(formulaFileName);
    }

    public static String fileUpload(InputStream input) throws IOException {
        try {
            if (input == null) {
                throw new IllegalArgumentException("\u6587\u4ef6\u4e0d\u80fd\u4e3a\u7a7a\uff01");
            }
            String currentUser = FileInfoBuilder.resolveCurrentUserName();
            String newKey = com.jiuqi.nr.file.utils.FileUtils.generateFileKey();
            ObjectInfo info = new ObjectInfo();
            info.setKey(newKey);
            info.setOwner(currentUser);
            FileUtils.objService().upload(newKey, input, info);
            return newKey;
        }
        catch (Exception e) {
            throw new FileException("\u6587\u4ef6\u4fdd\u5b58\u5931\u8d25\uff01", (Throwable)e);
        }
    }

    public static void fileDownLoad(String fileKey, OutputStream outputStream) throws Exception {
        boolean b = false;
        ObjectStorageService objectStorageService = null;
        try {
            objectStorageService = FileUtils.objService();
            b = objectStorageService.existObject(fileKey);
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
        if (!b) {
            throw new FileOssException("\u6587\u4ef6\u4e0d\u5b58\u5728\uff01");
        }
        try {
            InputStream download = objectStorageService.download(fileKey);
            if (download != null) {
                FileUtils.writeInput2Output(outputStream, download);
            }
        }
        catch (Exception e) {
            throw new FileOssException("\u6587\u4ef6\u4e0d\u5b58\u5728\uff01", (Throwable)e);
        }
    }

    public static boolean fileDelete(String fileKey) {
        boolean deleteResult = true;
        try {
            boolean b = FileUtils.objService().existObject(fileKey);
            if (b) {
                deleteResult = FileUtils.objService().deleteObject(fileKey);
            }
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
            return false;
        }
        return deleteResult;
    }

    public static void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    public static boolean isEnglish() {
        NpContext context = NpContextHolder.getContext();
        Locale locale = context.getLocale();
        return Locale.US.equals(locale);
    }
}

