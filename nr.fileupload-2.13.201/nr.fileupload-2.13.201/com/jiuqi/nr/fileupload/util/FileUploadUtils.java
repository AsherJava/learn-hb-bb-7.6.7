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
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.StringUtils
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.util;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.BucketService;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageManager;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.StringUtils;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.fileupload.exception.FileOssException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);
    public static final String GROUPKEY = "fileGroupKey";
    private static final String TEMP_AREA_NAME = "NR_TEMP";
    private static final int TEMP_BUCKET_EXPIRE_MILLS = 345600000;
    public static final String ROOT_LOCATION = System.getProperty(FilenameUtils.normalize("java.io.tmpdir"));
    public static final String FILE_UPLOAD_LOCATION = ROOT_LOCATION + File.separator + ".nr" + File.separator + "AppData" + File.separator + "fileUpload";

    public static String generateFileKey(String extension) {
        String key = FileUploadUtils.generateFileKey();
        return StringUtils.isEmpty((String)extension) ? key : String.format("%s%s", key, extension);
    }

    public static String generateFileKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static ObjectStorageService objServiceTemp() throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(TEMP_AREA_NAME);
        if (!exist) {
            Bucket bucket = new Bucket(TEMP_AREA_NAME);
            bucket.setExpireTime(345600000);
            bucket.setDesc("NR\u4e0a\u4f20\u7ec4\u4ef6\u4e34\u65f6\u533a");
            bucketService.createBucket(bucket);
            bucketService.close();
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(TEMP_AREA_NAME);
        return objService;
    }

    public static ObjectInfo fileUpload(String fileName, InputStream input) {
        ObjectStorageService objectStorageService = null;
        try {
            String currentUser = FileUploadUtils.resolveCurrentUserName();
            ObjectInfo info = new ObjectInfo();
            String fileKey = FileUploadUtils.generateFileKey();
            String extension = FileUploadUtils.tryParseExtension(fileName);
            if (StringUtils.isNotEmpty((String)FileUploadUtils.tryParseExtension(fileName))) {
                info.setExtension(extension);
            }
            info.setKey(fileKey);
            info.setName(fileName);
            info.setOwner(currentUser);
            objectStorageService = FileUploadUtils.objServiceTemp();
            objectStorageService.upload(fileKey, input, info);
            ObjectInfo objectInfo = info;
            return objectInfo;
        }
        catch (Exception e) {
            throw new FileOssException("faild to save file." + e.getMessage(), e);
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

    public static ObjectInfo fileUploadByKey(String fileName, String fileKey, String groupKey, InputStream input) {
        try {
            String currentUser = FileUploadUtils.resolveCurrentUserName();
            ObjectInfo info = new ObjectInfo();
            info.setKey(fileKey);
            info.setName(fileName);
            String extension = FileUploadUtils.tryParseExtension(fileName);
            if (StringUtils.isNotEmpty((String)FileUploadUtils.tryParseExtension(fileName))) {
                info.setExtension(extension);
            }
            info.setOwner(currentUser);
            if (groupKey != null) {
                info.getExtProp().put(GROUPKEY, groupKey);
            }
            long size = input.available();
            info.setSize(size);
            String md5 = DigestUtils.md5DigestAsHex(input);
            info.setMd5(md5);
            input.reset();
            FileUploadUtils.objServiceTemp().upload(fileKey, input, info);
            return info;
        }
        catch (Exception e) {
            throw new FileOssException("faild to save file." + e.getMessage(), e);
        }
    }

    public static void fileDownLoad(String fileKey, OutputStream outputStream) {
        boolean b = false;
        try {
            b = FileUploadUtils.objServiceTemp().existObject(fileKey);
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
        if (!b) {
            throw new FileOssException("file is not exist.");
        }
        try (InputStream download = FileUploadUtils.objServiceTemp().download(fileKey);){
            if (download != null) {
                FileUploadUtils.writeInput2Output(outputStream, download);
            }
        }
        catch (Exception e) {
            throw new FileOssException("failed to down load file." + e.getMessage(), e);
        }
    }

    /*
     * Exception decompiling
     */
    public static byte[] getFileBytes(String fileKey) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public static ObjectInfo getFileInfo(String fileKey) {
        ObjectInfo objectInfo = null;
        try {
            objectInfo = FileUploadUtils.objServiceTemp().getObjectInfo(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        return objectInfo;
    }

    public static List<ObjectInfo> getFileInfosByGroup(String groupKey) {
        ArrayList<ObjectInfo> objectByProp = new ArrayList();
        try {
            objectByProp = FileUploadUtils.objServiceTemp().findObjectByProp(GROUPKEY, groupKey, true);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        return objectByProp;
    }

    public static void fileDelete(String fileKey) {
        try {
            boolean b = FileUploadUtils.objServiceTemp().existObject(fileKey);
            if (b) {
                FileUploadUtils.objServiceTemp().deleteObject(fileKey);
            }
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
    }

    public static boolean fileExist(String fileKey) {
        boolean b = false;
        try {
            b = FileUploadUtils.objServiceTemp().existObject(fileKey);
        }
        catch (ObjectStorageException e1) {
            logger.error(e1.getMessage(), e1);
        }
        return b;
    }

    public static void fileDeleteByGroup(String groupKey) {
        List<ObjectInfo> fileInfosByGroup = FileUploadUtils.getFileInfosByGroup(groupKey);
        for (ObjectInfo objectInfo : fileInfosByGroup) {
            FileUploadUtils.fileDelete(objectInfo.getKey());
        }
    }

    public static String joinKey(String key, String directory) {
        if (key == null || "".equals(key)) {
            throw new IllegalArgumentException("key");
        }
        return directory == null || "".equals(directory) ? key : directory + "\\" + key;
    }

    private static void writeInput2Output(OutputStream os, InputStream is) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
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

    public static String resolveCurrentUserName() {
        ContextUser operator = NpContextHolder.getContext().getUser();
        return operator == null ? null : operator.getName();
    }

    public static String getUploadFileLocation(String fileName) {
        try {
            PathUtils.validatePathManipulation((String)fileName);
            File pathFile = new File(FILE_UPLOAD_LOCATION);
            if (!pathFile.exists() && !pathFile.isDirectory()) {
                pathFile.mkdirs();
            }
            return FILE_UPLOAD_LOCATION + File.separator + fileName;
        }
        catch (SecurityContentException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    public static ObjectStorageService objService() throws ObjectStorageException {
        BucketService bucketService = ObjectStorageManager.getInstance().createBucketService();
        boolean exist = bucketService.existBucket(TEMP_AREA_NAME);
        if (!exist) {
            Bucket bucket = new Bucket(TEMP_AREA_NAME);
            bucketService.createBucket(bucket);
            bucketService.close();
        }
        ObjectStorageService objService = ObjectStorageManager.getInstance().createObjectService(TEMP_AREA_NAME);
        return objService;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getFileTypeByte(MultipartFile file) {
        byte[] b = new byte[20];
        try (InputStream inputStream = file.getInputStream();){
            inputStream.read(b, 0, 20);
            String string = FileUploadUtils.bytesToHexString(b);
            return string;
        }
        catch (IOException e) {
            logger.error("\u8bfb\u53d6\u6587\u4ef6\u5b57\u8282\u5f02\u5e38\uff1a" + e.getMessage());
            return null;
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; ++i) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toUpperCase();
    }

    public static List<String> getOptionItemKeys() {
        ArrayList<String> optionKeys = new ArrayList<String>();
        optionKeys.add("BLACK_LIST_AND_WHITE_LIST");
        optionKeys.add("BLACK_LIST_INFO");
        optionKeys.add("WHITE_LIST_INFO");
        optionKeys.add("FILE_UPLOAD_MAX_SIZE");
        return optionKeys;
    }
}

