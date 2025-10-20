/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jsch.ChannelSftp
 *  com.jcraft.jsch.SftpException
 *  com.jiuqi.common.base.util.SpringContextUtils
 *  org.apache.commons.io.IOUtils
 */
package com.jiuqi.gcreport.archive.util.sftp;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import com.jiuqi.common.base.util.SpringContextUtils;
import com.jiuqi.gcreport.archive.common.UploadStatus;
import com.jiuqi.gcreport.archive.util.sftp.ArchiveSFTPClientPool;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ArchiveSFTPUtil {
    private static ArchiveSFTPClientPool archiveSFTPClientPool = (ArchiveSFTPClientPool)SpringContextUtils.getBean(ArchiveSFTPClientPool.class);
    private static String permission = "755";
    private static Logger log = LoggerFactory.getLogger(ArchiveSFTPClientPool.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static UploadStatus upload(String remote, InputStream inputStream) {
        ChannelSftp sftp = archiveSFTPClientPool.borrowObject();
        Object result = null;
        String remoteFileName = remote;
        if (remote.contains("/")) {
            remoteFileName = remote.substring(0, remote.lastIndexOf("/") + 1);
        }
        try {
            if (!ArchiveSFTPUtil.dirIsExist(remoteFileName)) {
                remoteFileName = ArchiveSFTPUtil.generateValidPath(remoteFileName, sftp);
            }
        }
        catch (Exception e) {
            log.error("\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\u5931\u8d25remoteFileName:" + remoteFileName, e);
            try {
                inputStream.close();
            }
            catch (IOException ioException) {
                log.error("SFTP\u4e0a\u4f20\u6587\u4ef6\u6d41\u5173\u95ed\u5f02\u5e38", ioException);
            }
            archiveSFTPClientPool.returnObject(sftp);
            return UploadStatus.CREATE_DIRECTORY_FAIL;
        }
        try {
            sftp.put(inputStream, remote, 1);
            UploadStatus e = UploadStatus.UPLOAD_FROM_BREAK_SUCCESS;
            return e;
        }
        catch (SftpException e) {
            log.error("SFTP\u4e0a\u4f20\u6587\u4ef6\u51fa\u9519", e);
            UploadStatus uploadStatus = UploadStatus.UPLOAD_NEW_FILE_FAILED;
            return uploadStatus;
        }
        finally {
            try {
                inputStream.close();
            }
            catch (IOException e) {
                log.error("SFTP\u4e0a\u4f20\u6587\u4ef6\u6d41\u5173\u95ed\u5f02\u5e38", e);
            }
            archiveSFTPClientPool.returnObject(sftp);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean dirIsExist(String url) {
        ChannelSftp sftp = archiveSFTPClientPool.borrowObject();
        try {
            if (ArchiveSFTPUtil.isDirectory(url)) {
                sftp.cd(url);
                String pwd = sftp.pwd();
                boolean bl = pwd.equals(url) || pwd.concat("/").equals(url);
                return bl;
            }
            boolean pwd = false;
            return pwd;
        }
        catch (SftpException e) {
            log.error("SFTP\u8bfb\u53d6\u6587\u4ef6\u5939\u51fa\u9519", e);
        }
        finally {
            archiveSFTPClientPool.returnObject(sftp);
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean isDirectory(String path) {
        ChannelSftp sftp = archiveSFTPClientPool.borrowObject();
        try {
            sftp.cd(path);
            boolean bl = true;
            return bl;
        }
        catch (SftpException e) {
            boolean bl = false;
            return bl;
        }
        finally {
            archiveSFTPClientPool.returnObject(sftp);
        }
    }

    private static void mkdirs(ChannelSftp sftp, String[] dirs, String tempPath, int length, int index) {
        if (++index >= length) {
            return;
        }
        tempPath = tempPath + "/" + dirs[index];
        try {
            sftp.cd(tempPath);
            if (index < length) {
                ArchiveSFTPUtil.mkdirs(sftp, dirs, tempPath, length, index);
            }
        }
        catch (SftpException ex) {
            try {
                sftp.mkdir(tempPath);
                sftp.cd(tempPath);
            }
            catch (SftpException e) {
                log.error("\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\u5931\u8d25\u6216\u8005\u6587\u4ef6\u5939\u5df2\u7ecf\u5b58\u5728\uff0c\u6587\u4ef6\u76ee\u5f55\uff1a" + tempPath);
            }
            ArchiveSFTPUtil.mkdirs(sftp, dirs, tempPath, length, index);
        }
        log.info("\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\uff0c\u6587\u4ef6\u76ee\u5f55\uff1a" + tempPath);
    }

    private static String generateValidPath(String newPath, ChannelSftp sftp) {
        ArchiveSFTPUtil.mkdirs(sftp, newPath.split("/"), "", newPath.split("/").length, 0);
        log.info("\u521b\u5efa\u6587\u4ef6\u76ee\u5f55\u6210\u529f\uff0c\u6587\u4ef6\u76ee\u5f55\uff1a" + newPath);
        return newPath;
    }

    public static void unzip(InputStream zipInputStream, File outputDir) {
        try (ZipInputStream zis = new ZipInputStream(zipInputStream);){
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                File newFile = new File(outputDir, entry.getName());
                if (entry.isDirectory()) {
                    newFile.mkdirs();
                } else {
                    new File(newFile.getParent()).mkdirs();
                    try (FileOutputStream fos = new FileOutputStream(newFile);){
                        IOUtils.copy((InputStream)zis, (OutputStream)fos);
                    }
                }
                zis.closeEntry();
            }
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

