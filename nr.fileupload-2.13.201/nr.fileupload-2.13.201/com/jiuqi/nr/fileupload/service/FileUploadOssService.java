/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.bi.oss.ObjectInfo
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.nr.fileupload.service;

import com.jiuqi.bi.oss.ObjectInfo;
import com.jiuqi.nr.fileupload.exception.FileOssException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.web.multipart.MultipartFile;

public interface FileUploadOssService {
    public ObjectInfo uploadFileToTemp(MultipartFile var1) throws IOException, FileOssException;

    public ObjectInfo uploadFileStreamToTemp(String var1, InputStream var2) throws IOException, FileOssException;

    public void downloadFileFormTemp(String var1, OutputStream var2) throws IOException, FileOssException;

    public byte[] downloadFileByteFormTemp(String var1) throws IOException, FileOssException;

    public ObjectInfo getInfo(String var1);
}

