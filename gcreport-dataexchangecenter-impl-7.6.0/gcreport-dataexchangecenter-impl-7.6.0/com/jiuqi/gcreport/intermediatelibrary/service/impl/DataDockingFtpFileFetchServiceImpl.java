/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  org.apache.commons.net.ftp.FTPClient
 *  org.springframework.mock.web.MockMultipartFile
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.gcreport.intermediatelibrary.service.impl;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.gcreport.intermediatelibrary.service.DataDockingFileFetchService;
import com.jiuqi.gcreport.intermediatelibrary.utils.FTPConfig;
import com.jiuqi.gcreport.intermediatelibrary.utils.FtpUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class DataDockingFtpFileFetchServiceImpl
implements DataDockingFileFetchService {
    private static final Logger logger = LoggerFactory.getLogger(DataDockingFtpFileFetchServiceImpl.class);
    @Value(value="${gc.datadocking.ftp.host:}")
    private String host;
    @Value(value="${gc.datadocking.ftp.port:}")
    private Integer port;
    @Value(value="${gc.datadocking.ftp.username:}")
    private String username;
    @Value(value="${gc.datadocking.ftp.password:}")
    private String password;

    @Override
    public MultipartFile[] fetchFile(String value) {
        String[] paths = value.split(",");
        MultipartFile[] multipartFiles = new MultipartFile[paths.length];
        FTPConfig ftpConfig = new FTPConfig();
        ftpConfig.setHost(this.host);
        ftpConfig.setPort(this.port);
        ftpConfig.setUsername(this.username);
        ftpConfig.setPassword(this.password);
        FTPClient ftpConnection = FtpUtils.getFtpConnection(ftpConfig);
        try {
            for (int i = 0; i < paths.length; ++i) {
                String path = paths[i];
                int index = path.lastIndexOf("/");
                String pathName = path.substring(index + 1);
                logger.info("\u9644\u4ef6\u4e0b\u8f7d\u8def\u5f84\uff1a{}", (Object)path);
                path = new String(path.getBytes("UTF-8"), "ISO-8859-1");
                InputStream inputStream = ftpConnection.retrieveFileStream(path);
                if (Objects.isNull(inputStream)) {
                    throw new BusinessRuntimeException("\u672a\u627e\u5230\u6587\u4ef6\uff1a" + path);
                }
                MockMultipartFile mockMultipartFile = new MockMultipartFile("file", pathName, "application/msword", inputStream);
                multipartFiles[i] = mockMultipartFile;
            }
        }
        catch (IOException e) {
            throw new BusinessRuntimeException("\u9644\u4ef6\u4e0b\u8f7d\u5931\u8d25\uff0c\u8bf7\u68c0\u67e5ftp\u670d\u52a1\u5668\u662f\u5426\u5b58\u5728\u6b64\u9644\u4ef6\uff1a" + value, (Throwable)e);
        }
        finally {
            if (ftpConnection.isConnected()) {
                try {
                    ftpConnection.disconnect();
                }
                catch (IOException iOException) {}
            }
        }
        return multipartFiles;
    }
}

