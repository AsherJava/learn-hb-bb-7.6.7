/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.security.PathUtils
 *  com.jiuqi.bi.security.SecurityContentException
 *  com.jiuqi.bi.util.type.GUID
 *  org.apache.poi.util.IOUtils
 */
package com.jiuqi.bi.oss.storage.disk;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.encrypt.ObjectEncryptManager;
import com.jiuqi.bi.oss.storage.MultipartObjectUploader;
import com.jiuqi.bi.oss.storage.MultipartUploadResult;
import com.jiuqi.bi.oss.storage.ObjectDataInputStream;
import com.jiuqi.bi.oss.storage.ObjectSliceInfo;
import com.jiuqi.bi.oss.storage.PartEtag;
import com.jiuqi.bi.oss.storage.StorageUtils;
import com.jiuqi.bi.oss.storage.disk.DiskFileUtils;
import com.jiuqi.bi.security.PathUtils;
import com.jiuqi.bi.security.SecurityContentException;
import com.jiuqi.bi.util.type.GUID;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.Arrays;
import java.util.stream.Stream;
import org.apache.poi.util.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiskMultipartObjectUploader
extends MultipartObjectUploader {
    private static final Logger logger = LoggerFactory.getLogger(DiskMultipartObjectUploader.class);
    private File root;
    private boolean encrypt;
    private int partationSize;
    private File cacheDir;

    public DiskMultipartObjectUploader(File root, boolean encrypt, int partationSize, Bucket bucket) throws ObjectStorageException {
        super(bucket);
        this.root = root;
        this.partationSize = partationSize;
        this.encrypt = encrypt;
    }

    @Override
    public String initUpload(ObjectInfo info) throws ObjectStorageException {
        DiskFileUtils.validateObjectKey(info.getKey());
        return GUID.newFullGUID();
    }

    @Override
    public PartEtag uploadPart(String uploadId, String key, InputStream input, ObjectSliceInfo sliceInfo) throws ObjectStorageException {
        this.prepareTargetDir(uploadId, key);
        File f = new File(this.cacheDir, "part" + sliceInfo.getPartNum());
        try (FileOutputStream bos = new FileOutputStream(f);){
            StorageUtils.loadStreamToOutputStream(this.bucket, uploadId, input, null, null, bos);
        }
        catch (IOException e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
        return new PartEtag(sliceInfo.getPartNum());
    }

    @Override
    public MultipartUploadResult finishUpload(String uploadId, String key) throws ObjectStorageException {
        this.prepareTargetDir(uploadId, key);
        try {
            PathUtils.validatePathManipulation((String)this.cacheDir.getParent());
        }
        catch (SecurityContentException e) {
            throw new ObjectStorageException(e.getLocalizedMessage(), e);
        }
        File target = new File(this.cacheDir.getParent(), key);
        return this.mergeMultiparts(target, key);
    }

    @Override
    public void abortUploadPart(String uploadId, String key) throws ObjectStorageException {
        this.prepareTargetDir(uploadId, key);
        try (Stream<Path> tempFiles = Files.list(this.cacheDir.toPath());){
            tempFiles.forEach(path -> {
                try {
                    Files.deleteIfExists(path);
                }
                catch (IOException e) {
                    logger.warn("\u5220\u9664\u5206\u7247\u4e0a\u4f20\u4e34\u65f6\u6587\u4ef6\u5931\u8d25. path: {}", path);
                }
            });
            Files.deleteIfExists(this.cacheDir.toPath());
        }
        catch (IOException ignored) {
            logger.warn("\u5220\u9664\u5206\u7247\u4e0a\u4f20\u4e34\u65f6\u76ee\u5f55\u5931\u8d25. path: {}", (Object)this.cacheDir.toPath());
        }
    }

    @Override
    public boolean supportCheckSum() {
        return true;
    }

    private File prepareTargetDir(String uploadId, String key) throws ObjectStorageException {
        if (this.cacheDir != null) {
            return this.cacheDir;
        }
        try {
            PathUtils.validatePathManipulation((String)key);
        }
        catch (SecurityContentException e) {
            throw new ObjectStorageException(e.getMessage());
        }
        File path = DiskFileUtils.computePathByKey(this.root, this.partationSize, this.bucket.getName(), key, false);
        if (!path.exists()) {
            path.mkdirs();
        }
        this.cacheDir = new File(path, uploadId);
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdir();
        }
        return this.cacheDir;
    }

    private int extractNumber(String name) {
        String number = name.replaceAll("\\D+", "");
        return number.isEmpty() ? 0 : Integer.parseInt(number);
    }

    private MultipartUploadResult mergeMultiparts(File target, String key) throws ObjectStorageException {
        File[] files = this.cacheDir.listFiles();
        if (files == null) {
            throw new ObjectStorageException("\u83b7\u53d6\u5206\u7247\u6587\u4ef6\u5217\u8868\u5931\u8d25");
        }
        Arrays.sort(files, (o1, o2) -> {
            String name1 = o1.getName();
            String name2 = o2.getName();
            return this.extractNumber(name1) - this.extractNumber(name2);
        });
        String name = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptName() : null;
        String eKey = this.encrypt ? ObjectEncryptManager.getInstance().getDefaultEncryptKey() : null;
        MultipartUploadResult result = new MultipartUploadResult();
        result.setPartNum(files.length);
        try {
            Path mergeTargetTempFile = Files.createTempFile("oss-merging-target", ".tmp", new FileAttribute[0]);
            for (File f : files) {
                try (OutputStream outputStream = Files.newOutputStream(mergeTargetTempFile, StandardOpenOption.APPEND);
                     FileInputStream inputStream = new FileInputStream(f);){
                    IOUtils.copy((InputStream)inputStream, (OutputStream)outputStream);
                    outputStream.flush();
                }
            }
            File[] fileArray = null;
            try (ObjectDataInputStream objectDataInputStream = new ObjectDataInputStream(Files.newInputStream(mergeTargetTempFile, StandardOpenOption.DELETE_ON_CLOSE));){
                File tmpFile = StorageUtils.putStreamToTemporary(this.bucket, key, objectDataInputStream, name, eKey);
                Files.move(tmpFile.toPath(), target.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Files.deleteIfExists(tmpFile.toPath());
                result.setMd5(objectDataInputStream.getMd5());
                result.setTotalSize(objectDataInputStream.getSize());
            }
            catch (Throwable object) {
                fileArray = object;
                throw object;
            }
            MultipartUploadResult multipartUploadResult = result;
            return multipartUploadResult;
        }
        catch (IOException e) {
            throw new ObjectStorageException(e.getMessage(), e);
        }
        finally {
            try {
                for (File file : files) {
                    Files.deleteIfExists(file.toPath());
                }
                Files.deleteIfExists(this.cacheDir.toPath());
            }
            catch (IOException ignored) {
                logger.warn("\u5220\u9664\u5206\u7247\u4e0a\u4f20\u4e34\u65f6\u76ee\u5f55\u5931\u8d25. path: {}", (Object)this.cacheDir.toPath());
            }
        }
    }
}

