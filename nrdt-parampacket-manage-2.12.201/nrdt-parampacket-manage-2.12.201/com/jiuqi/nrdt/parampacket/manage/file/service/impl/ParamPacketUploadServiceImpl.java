/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.nr.file.FileInfo
 *  com.jiuqi.nr.file.FileService
 */
package com.jiuqi.nrdt.parampacket.manage.file.service.impl;

import com.jiuqi.nr.file.FileInfo;
import com.jiuqi.nr.file.FileService;
import com.jiuqi.nrdt.parampacket.manage.file.service.ParamPacketUploadService;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParamPacketUploadServiceImpl
implements ParamPacketUploadService {
    @Autowired
    private FileService fileService;

    @Override
    public FileInfo uploadByGroup(String fileName, String groupKey, InputStream fileContent) throws IOException {
        FileInfo fileInfo = this.fileService.area("PARAMTEMP").uploadByGroup(fileName, groupKey, fileContent);
        return fileInfo;
    }

    @Override
    public FileInfo upload(String fileName, String extension, InputStream fileContent) throws IOException {
        FileInfo fileInfo = this.fileService.area("PARAMTEMP").upload(fileName, extension, fileContent);
        return fileInfo;
    }

    @Override
    public byte[] download(String fileKey) {
        byte[] bytes = this.fileService.area("PARAMTEMP").download(fileKey);
        return bytes;
    }

    @Override
    public void download(String fileKey, OutputStream outputStream) {
        this.fileService.area("PARAMTEMP").download(fileKey, outputStream);
    }
}

