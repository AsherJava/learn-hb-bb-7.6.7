/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  com.jiuqi.bi.oss.ObjectStorageException
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.service.impl;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.bi.oss.ObjectStorageException;
import com.jiuqi.nr.fileupload.exception.FileOssException;
import com.jiuqi.nr.fileupload.service.FileUploadOssService;
import com.jiuqi.nr.fileupload.util.FileUploadUtils;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileUploadOssServiceImpl
implements FileUploadOssService {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public ObjectInfo uploadFileToTemp(MultipartFile file) throws IOException, FileOssException {
        try (InputStream fileContent = file.getInputStream();){
            ObjectInfo objectInfo;
            ObjectInfo objectInfo2 = objectInfo = FileUploadUtils.fileUpload(file.getOriginalFilename(), fileContent);
            return objectInfo2;
        }
    }

    @Override
    public ObjectInfo uploadFileStreamToTemp(String fileName, InputStream inputStream) throws IOException, FileOssException {
        ObjectInfo objectInfo = FileUploadUtils.fileUpload(fileName, inputStream);
        return objectInfo;
    }

    @Override
    public void downloadFileFormTemp(String fileInfoKey, OutputStream fileOutputStream) throws IOException, FileOssException {
        FileUploadUtils.fileDownLoad(fileInfoKey, fileOutputStream);
    }

    @Override
    public byte[] downloadFileByteFormTemp(String fileInfoKey) throws IOException, FileOssException {
        return FileUploadUtils.getFileBytes(fileInfoKey);
    }

    @Override
    public ObjectInfo getInfo(String fileKey) {
        ObjectInfo objectInfo = null;
        try {
            objectInfo = FileUploadUtils.objService().getObjectInfo(fileKey);
        }
        catch (ObjectStorageException e) {
            e.getStackTrace();
        }
        return objectInfo;
    }
}

