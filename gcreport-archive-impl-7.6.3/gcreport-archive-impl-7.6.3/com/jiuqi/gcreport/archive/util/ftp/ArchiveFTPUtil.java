/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.common.base.BusinessRuntimeException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.apache.commons.net.ftp.FTPClient
 *  org.apache.commons.net.ftp.FTPFile
 */
package com.jiuqi.gcreport.archive.util.ftp;

import com.jiuqi.common.base.BusinessRuntimeException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.archive.common.UploadStatus;
import com.jiuqi.gcreport.archive.util.ftp.FtpClientPool;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiveFTPUtil {
    public static final Logger logger = LoggerFactory.getLogger(ArchiveFTPUtil.class);
    static String LOCAL_CHARSET = "UTF-8";
    static String SERVER_CHARSET = "ISO-8859-1";
    private static FtpClientPool ftpClientPool = (FtpClientPool)SpringContextUtils.getBean(FtpClientPool.class);

    public static UploadStatus upload(InputStream stream, String remoteFileName, int localSize) {
        ArchiveFTPUtil ftpUtil = new ArchiveFTPUtil();
        FTPClient ftpClient = null;
        try {
            ftpClient = ftpClientPool.borrowClient();
            UploadStatus uploadStatus = ArchiveFTPUtil.upload(ftpClient, stream, remoteFileName, localSize);
            return uploadStatus;
        }
        catch (Exception e) {
            throw new BusinessRuntimeException((Throwable)e);
        }
        finally {
            ftpClientPool.returnFtpClient(ftpClient);
        }
    }

    private static UploadStatus upload(FTPClient ftpClient, InputStream inputStream, String remote, int localSize) throws IOException {
        FTPFile[] files;
        UploadStatus result = null;
        String remoteFileName = remote;
        if (remote.contains("/")) {
            remoteFileName = remote.substring(remote.lastIndexOf("/") + 1);
            if (ArchiveFTPUtil.CreateDirecroty(remote, ftpClient) == UploadStatus.CREATE_DIRECTORY_FAIL) {
                return UploadStatus.CREATE_DIRECTORY_FAIL;
            }
        }
        if ((files = ftpClient.listFiles(new String(remoteFileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET))).length == 1) {
            long remoteSize = files[0].getSize();
            if (remoteSize == (long)localSize) {
                return UploadStatus.FILE_EXITS;
            }
            if (!ftpClient.deleteFile(remoteFileName)) {
                return UploadStatus.DELETE_REMOTE_FAILD;
            }
            result = ArchiveFTPUtil.uploadFile(remoteFileName, inputStream, ftpClient, 0L);
        } else {
            result = ArchiveFTPUtil.uploadFile(remoteFileName, inputStream, ftpClient, 0L);
        }
        return result;
    }

    public static UploadStatus CreateDirecroty(String remote, FTPClient ftpClient) throws IOException {
        UploadStatus status;
        block2: {
            status = UploadStatus.CREATE_DIRECTORY_SUCCESS;
            String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
            if (directory.equalsIgnoreCase("/") || ftpClient.changeWorkingDirectory(new String(directory.getBytes(LOCAL_CHARSET), SERVER_CHARSET))) break block2;
            int start = 0;
            int end = 0;
            start = directory.startsWith("/") ? 1 : 0;
            end = directory.indexOf("/", start);
            do {
                String subDirectory;
                if (ftpClient.changeWorkingDirectory(subDirectory = new String(remote.substring(start, end).getBytes(LOCAL_CHARSET), SERVER_CHARSET))) continue;
                if (ftpClient.makeDirectory(subDirectory)) {
                    ftpClient.changeWorkingDirectory(subDirectory);
                    continue;
                }
                logger.error("\u521b\u5efa\u76ee\u5f55[{}]\u5931\u8d25", (Object)subDirectory);
                return UploadStatus.CREATE_DIRECTORY_FAIL;
            } while ((end = directory.indexOf("/", start = end + 1)) > start);
        }
        return status;
    }

    private static UploadStatus uploadFile(String remoteFile, InputStream inputStream, FTPClient ftpClient, long remoteSize) throws IOException {
        int c;
        OutputStream out = ftpClient.appendFileStream(new String(remoteFile.getBytes(LOCAL_CHARSET), SERVER_CHARSET));
        if (remoteSize > 0L) {
            ftpClient.setRestartOffset(remoteSize);
            inputStream.skip(remoteSize);
        }
        byte[] bytes = new byte[1024];
        while ((c = inputStream.read(bytes)) != -1) {
            out.write(bytes, 0, c);
        }
        logger.info("[{}]\u6587\u4ef6\u4e0a\u4f20\u6210\u529f\uff01", (Object)remoteFile);
        out.flush();
        inputStream.close();
        out.close();
        boolean result = ftpClient.completePendingCommand();
        UploadStatus status = remoteSize > 0L ? (result ? UploadStatus.UPLOAD_FROM_BREAK_SUCCESS : UploadStatus.UPLOAD_FROM_BREAK_FAILED) : (result ? UploadStatus.UPLOAD_NEW_FILE_SUCCESS : UploadStatus.UPLOAD_NEW_FILE_FAILED);
        return status;
    }
}

