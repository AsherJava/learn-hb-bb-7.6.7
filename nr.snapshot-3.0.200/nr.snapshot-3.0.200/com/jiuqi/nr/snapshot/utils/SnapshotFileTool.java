/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.Bucket
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageEngine
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  com.jiuqi.bi.oss.ObjectStorageService
 *  com.jiuqi.np.core.context.ContextUser
 *  com.jiuqi.np.core.context.NpContextHolder
 */
package com.jiuqi.nr.snapshot.utils;

import com.jiuqi.bi.oss.Bucket;
import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageEngine;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.bi.oss.ObjectStorageService;
import com.jiuqi.np.core.context.ContextUser;
import com.jiuqi.np.core.context.NpContextHolder;
import com.jiuqi.nr.snapshot.exception.SnapshotFileDeleteException;
import com.jiuqi.nr.snapshot.exception.SnapshotFileDownloadException;
import com.jiuqi.nr.snapshot.exception.SnapshotFileNotFoundException;
import com.jiuqi.nr.snapshot.exception.SnapshotFileUploadException;
import com.jiuqi.nr.snapshot.message.SnapshotFileInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SnapshotFileTool {
    private static final Logger logger = LoggerFactory.getLogger(SnapshotFileTool.class);
    private ObjectStorageService objectStorageService = null;
    private static final String DATA_VER_AREA = "DataVer";

    public SnapshotFileTool() {
        try {
            ObjectStorageEngine objectStorageEngine = ObjectStorageEngine.newInstance();
            Bucket bucket = objectStorageEngine.getBucket(DATA_VER_AREA);
            if (null == bucket) {
                bucket = new Bucket(DATA_VER_AREA);
                bucket.setDesc("\u5973\u5a32\u62a5\u8868-\u5feb\u7167\u529f\u80fd-\u5b58\u653e\u5feb\u7167\u6587\u4ef6\u8d44\u6e90");
                bucket.setLinkWhenExist(true);
                objectStorageEngine.createBucket(bucket);
            }
            this.objectStorageService = objectStorageEngine.createObjectService(DATA_VER_AREA);
        }
        catch (ObjectStorageException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
    }

    public SnapshotFileInfo upload(InputStream file) throws IOException {
        SnapshotFileInfo snapshotFileInfo = null;
        try {
            String fileKey = this.ossUpload(file);
            ObjectInfo objectInfo = this.objectStorageService.getObjectInfo(fileKey);
            snapshotFileInfo = new SnapshotFileInfo(fileKey, DATA_VER_AREA, objectInfo.getExtension(), objectInfo.getSize(), objectInfo.getOwner(), new Date());
        }
        catch (ObjectStorageException e) {
            logger.error("\u51fa\u9519\u539f\u56e0\uff1a" + e.getMessage(), e);
        }
        catch (SnapshotFileUploadException e) {
            throw new SnapshotFileUploadException("File Upload Error.", e);
        }
        return snapshotFileInfo;
    }

    public void download(String fileKey, OutputStream outputStream) throws IOException {
        boolean existFile = false;
        try {
            existFile = this.objectStorageService.existObject(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        if (!existFile) {
            throw new SnapshotFileNotFoundException(fileKey);
        }
        try {
            InputStream download = this.objectStorageService.download(fileKey);
            this.writeInputToOutput(download, outputStream);
        }
        catch (Exception e) {
            throw new SnapshotFileDownloadException("File Download Error.", e);
        }
    }

    public void delete(String fileKey) throws IOException {
        boolean existFile = false;
        try {
            existFile = this.objectStorageService.existObject(fileKey);
        }
        catch (ObjectStorageException e) {
            logger.error(e.getMessage(), e);
        }
        if (!existFile) {
            throw new SnapshotFileNotFoundException(fileKey);
        }
        try {
            this.objectStorageService.deleteObject(fileKey);
        }
        catch (ObjectStorageException e) {
            throw new SnapshotFileDeleteException("File Delete Error.", e);
        }
    }

    private String ossUpload(InputStream file) throws IOException {
        try {
            String fileKey = this.generateFileKey();
            String currentUser = this.resolveCurrentUserName();
            ObjectInfo info = new ObjectInfo();
            info.setKey(fileKey);
            info.setOwner(currentUser);
            long size = file.available();
            info.setSize(size);
            this.objectStorageService.upload(fileKey, file, info);
            return fileKey;
        }
        catch (Exception e) {
            throw new SnapshotFileUploadException("File Upload Error.", e);
        }
    }

    private String tryParseExtension(String fileName) {
        if (fileName == null) {
            return null;
        }
        int extensionPos = fileName.lastIndexOf(".");
        if (extensionPos <= 0) {
            return null;
        }
        return fileName.substring(extensionPos);
    }

    private String resolveCurrentUserName() {
        ContextUser operator = NpContextHolder.getContext().getUser();
        return operator == null ? null : operator.getName();
    }

    private String generateFileKey() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public void writeInputToOutput(InputStream is, OutputStream os) throws IOException {
        int len;
        int size = 1024;
        byte[] data = new byte[size];
        while ((len = is.read(data, 0, size)) != -1) {
            os.write(data, 0, len);
        }
    }

    public void close() throws ObjectStorageException {
        if (null != this.objectStorageService) {
            this.objectStorageService.close();
        }
    }
}

