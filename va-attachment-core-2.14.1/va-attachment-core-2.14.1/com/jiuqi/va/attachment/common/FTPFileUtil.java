/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jsch.Channel
 *  com.jcraft.jsch.ChannelSftp
 *  com.jcraft.jsch.JSch
 *  com.jcraft.jsch.JSchException
 *  com.jcraft.jsch.Session
 *  com.jcraft.jsch.SftpATTRS
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.apache.commons.net.ftp.FTPClient
 *  org.apache.commons.net.ftp.FTPReply
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.common;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpATTRS;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

public class FTPFileUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(FTPFileUtil.class);

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean uploadFileToFtp(MultipartFile file, SchemeEntity fileEntity) throws Exception {
        FTPClient ftpClient = FTPFileUtil.getFtpClient(fileEntity);
        if (ftpClient == null) {
            LOGGER.error("FTP\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25: \u83b7\u53d6\u8fde\u63a5\u5f02\u5e38");
            return false;
        }
        String fileName = fileEntity.getFileName();
        String filePath = fileEntity.getFilePath();
        InputStream inputStream = file.getInputStream();
        try {
            int n;
            FTPClient ftpClientNew;
            block34: {
                boolean flag;
                if (filePath != null && !"".equals(filePath.trim()) && !ftpClient.changeWorkingDirectory(filePath)) {
                    String[] pathes;
                    for (String onepath : pathes = filePath.split("/")) {
                        if (onepath == null || "".equals(onepath.trim()) || ftpClient.changeWorkingDirectory(onepath = new String(onepath.getBytes("UTF-8"), "UTF-8"))) continue;
                        if (!ftpClient.makeDirectory(onepath)) {
                            LOGGER.info("\u521b\u5efa\u76ee\u5f55[" + onepath + "]\u5931\u8d25\uff0c\u5f53\u524d\u76ee\u5f55\uff1a" + ftpClient.printWorkingDirectory());
                            throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.create.path.error", onepath));
                        }
                        if (ftpClient.changeWorkingDirectory(onepath)) continue;
                        LOGGER.info("\u5207\u6362\u76ee\u5f55[" + onepath + "]\u5931\u8d25\uff0c\u5f53\u524d\u76ee\u5f55\uff1a" + ftpClient.printWorkingDirectory());
                        throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.switch.path.error", onepath));
                    }
                }
                if (!(flag = ftpClient.storeFile(new String(fileName.getBytes("UTF-8"), "iso-8859-1"), inputStream))) {
                    ftpClient.logout();
                    boolean bl = false;
                    return bl;
                }
                ftpClient.logout();
                ftpClientNew = FTPFileUtil.getFtpClient(fileEntity);
                if (ftpClientNew == null) {
                    LOGGER.error("FTP\u590d\u67e5\u6587\u4ef6\u5b58\u5728\u5931\u8d25: \u83b7\u53d6\u8fde\u63a5\u5f02\u5e38");
                    boolean e = false;
                    return e;
                }
                try {
                    ftpClientNew.changeWorkingDirectory(filePath);
                    Object[] strings = ftpClientNew.listNames(fileName);
                    if (!ObjectUtils.isEmpty(strings)) break block34;
                    LOGGER.error("\u9644\u4ef6\u4e0a\u4f20\u540eFTP\u670d\u52a1\u5668\u67e5\u8be2\u4e0d\u5b58\u5728: " + filePath + fileName);
                    ftpClientNew.logout();
                    n = 0;
                }
                catch (Throwable throwable) {
                    try {
                        if (ftpClientNew.isConnected()) {
                            ftpClientNew.disconnect();
                        }
                        throw throwable;
                    }
                    catch (Exception e) {
                        LOGGER.error("FTP\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25:", e);
                        boolean bl = false;
                        return bl;
                    }
                }
                if (ftpClientNew.isConnected()) {
                    ftpClientNew.disconnect();
                }
                return n != 0;
            }
            ftpClientNew.logout();
            n = 1;
            if (ftpClientNew.isConnected()) {
                ftpClientNew.disconnect();
            }
            return n != 0;
        }
        finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
                inputStream.close();
            }
            catch (IOException e) {
                LOGGER.error("\u91ca\u653eFTP\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void downloadFileFromFtp(SchemeEntity fileEntity, AttachmentBizDO attachmentBizDO) {
        FTPClient ftpClient = FTPFileUtil.getFtpClient(fileEntity);
        if (ftpClient == null) {
            LOGGER.error("FTP\u4e0b\u8f7d\u6587\u4ef6\u5931\u8d25: \u83b7\u53d6\u8fde\u63a5\u5f02\u5e38");
            return;
        }
        InputStream bis = null;
        OutputStream os = null;
        String fileName = fileEntity.getFileName();
        String filePath = fileEntity.getFilePath();
        try {
            RequestContextUtil.setResponseContentType((String)"application/x-download");
            try {
                RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8''" + URLEncoder.encode(new String(fileName.getBytes("UTF-8"), "iso-8859-1"), "iso-8859-1")));
            }
            catch (Exception e) {
                LOGGER.error("\u8bbe\u7f6e\u54cd\u5e94\u5934\u5931\u8d25", e);
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.setting.response.header.error", new Object[0]));
            }
            filePath = new String(filePath.getBytes("UTF-8"), "UTF-8");
            String workPath = filePath.substring(0, filePath.lastIndexOf("/"));
            if (!ftpClient.changeWorkingDirectory(workPath)) {
                LOGGER.info("\u5207\u6362\u76ee\u5f55[" + workPath + "]\u5931\u8d25\uff0c\u5f53\u524d\u76ee\u5f55\uff1a" + ftpClient.printWorkingDirectory());
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.switch.path.error", workPath));
            }
            bis = ftpClient.retrieveFileStream(filePath.substring(filePath.lastIndexOf("/") + 1));
            os = RequestContextUtil.getOutputStream();
            StreamUtils.copy(bis, os);
            ftpClient.logout();
        }
        catch (Exception e) {
            LOGGER.error("\u8bbe\u7f6e\u54cd\u5e94\u5934\u5931\u8d25", e);
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.close();
                }
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
            catch (IOException e) {
                LOGGER.error("\u91ca\u653eFTP\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] getFileFromFtp(SchemeEntity fileEntity, AttachmentBizDO attachmentBizDO) {
        FTPClient ftpClient = FTPFileUtil.getFtpClient(fileEntity);
        if (ftpClient == null) {
            LOGGER.error("FTP\u4e0b\u8f7d\u6587\u4ef6\u5931\u8d25: \u83b7\u53d6\u8fde\u63a5\u5f02\u5e38");
            return null;
        }
        InputStream bis = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String filePath = fileEntity.getFilePath();
        try {
            filePath = new String(filePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            String workPath = filePath.substring(0, filePath.lastIndexOf("/"));
            if (!ftpClient.changeWorkingDirectory(workPath)) {
                LOGGER.info("\u5207\u6362\u76ee\u5f55[" + workPath + "]\u5931\u8d25\uff0c\u5f53\u524d\u76ee\u5f55\uff1a" + ftpClient.printWorkingDirectory());
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.switch.path.error", workPath));
            }
            bis = ftpClient.retrieveFileStream(filePath.substring(filePath.lastIndexOf("/") + 1));
            StreamUtils.copy(bis, (OutputStream)os);
            ftpClient.logout();
            byte[] byArray = os.toByteArray();
            return byArray;
        }
        catch (Exception e) {
            LOGGER.error("\u8bbe\u7f6e\u54cd\u5e94\u5934\u5931\u8d25", e);
            byte[] byArray = null;
            return byArray;
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                os.close();
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
            catch (IOException e) {
                LOGGER.error("\u91ca\u653eFTP\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean deleteFileFromFtp(SchemeEntity fileEntity) {
        FTPClient ftpClient = FTPFileUtil.getFtpClient(fileEntity);
        if (ftpClient == null) {
            LOGGER.error("FTP\u5220\u9664\u6587\u4ef6\u5931\u8d25: \u83b7\u53d6\u8fde\u63a5\u5f02\u5e38");
            return false;
        }
        String filePath = fileEntity.getFilePath();
        try {
            int flag = ftpClient.dele(filePath);
            ftpClient.logout();
            if (flag == 250) {
                boolean bl = true;
                return bl;
            }
            LOGGER.error("FTP\u5220\u9664\u6587\u4ef6\u5931\u8d25flag:" + flag);
            boolean bl = false;
            return bl;
        }
        catch (Exception e) {
            LOGGER.error("FTP\u5220\u9664\u6587\u4ef6\u5931\u8d25", e);
            boolean bl = false;
            return bl;
        }
        finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.disconnect();
                }
            }
            catch (IOException e) {
                LOGGER.error("\u91ca\u653eFTP\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean uploadFileToSftp(MultipartFile file, SchemeEntity fileEntity) throws IOException {
        String fileName = fileEntity.getFileName();
        String filePath = fileEntity.getFilePath();
        InputStream inputStream = file.getInputStream();
        Session sshSession = null;
        Channel channel = null;
        ChannelSftp channelSftp = null;
        try {
            sshSession = FTPFileUtil.getSftpClient(fileEntity);
            channel = sshSession.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            if (filePath != null && !"".equals(filePath.trim())) {
                if (FTPFileUtil.isDirExist(filePath, channelSftp)) {
                    channelSftp.cd(filePath);
                } else {
                    String[] pathArry;
                    for (String path : pathArry = filePath.split("/")) {
                        if (path.equals("")) continue;
                        if (FTPFileUtil.isDirExist(path.toString(), channelSftp)) {
                            channelSftp.cd(path.toString());
                            continue;
                        }
                        channelSftp.mkdir(path.toString());
                        channelSftp.cd(path.toString());
                    }
                }
            }
            channelSftp.put(inputStream, fileName);
            boolean pathArry = true;
            return pathArry;
        }
        catch (Exception e) {
            LOGGER.error("\u4e0a\u4f20\u9644\u4ef6\u5230sftp\u5931\u8d25", e);
            boolean bl = false;
            return bl;
        }
        finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (sshSession != null) {
                sshSession.disconnect();
            }
        }
    }

    public static boolean isDirExist(String directory, ChannelSftp channelSftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        }
        catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
            return isDirExistFlag;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void downloadFileFromSftp(SchemeEntity fileEntity, AttachmentBizDO attachmentBizDO) {
        InputStream bis = null;
        OutputStream os = null;
        String fileName = fileEntity.getFileName();
        String filePath = fileEntity.getFilePath();
        Channel channel = null;
        ChannelSftp channelSftp = null;
        Session sshSession = null;
        try {
            sshSession = FTPFileUtil.getSftpClient(fileEntity);
            channel = sshSession.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            RequestContextUtil.setResponseContentType((String)"application/x-download");
            RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
            try {
                RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(fileName, "UTF-8")));
            }
            catch (Exception e) {
                LOGGER.error("sftp\u8bbe\u7f6e\u5934\u4fe1\u606f\u5931\u8d25", e);
            }
            filePath = new String(filePath.getBytes("UTF-8"), "UTF-8");
            String workPath = filePath.substring(0, filePath.lastIndexOf("/"));
            try {
                channelSftp.cd(workPath);
            }
            catch (Exception e) {
                LOGGER.info("\u5207\u6362\u76ee\u5f55[" + workPath + "]\u5931\u8d25\uff0c\u5f53\u524d\u76ee\u5f55\uff1a" + channelSftp.pwd() + "\u5bb6\u76ee\u5f55" + channelSftp.getHome());
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.switch.path.error", workPath));
            }
            bis = channelSftp.get(filePath.substring(filePath.lastIndexOf("/") + 1));
            os = RequestContextUtil.getOutputStream();
            StreamUtils.copy(bis, os);
        }
        catch (Exception e) {
            LOGGER.error("sftp\u4e0b\u8f7d\u9644\u4ef6\u5931\u8d25", e);
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.close();
                }
                if (channelSftp != null && channelSftp.isConnected()) {
                    channelSftp.disconnect();
                }
                if (sshSession != null) {
                    sshSession.disconnect();
                }
            }
            catch (IOException e) {
                LOGGER.error("sftp\u91ca\u653e\u8fde\u63a5\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static byte[] getFileFromSftp(SchemeEntity fileEntity, AttachmentBizDO attachmentBizDO) {
        InputStream bis = null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        String filePath = fileEntity.getFilePath();
        Channel channel = null;
        ChannelSftp channelSftp = null;
        Session sshSession = null;
        try {
            sshSession = FTPFileUtil.getSftpClient(fileEntity);
            channel = sshSession.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            filePath = new String(filePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            String workPath = filePath.substring(0, filePath.lastIndexOf("/"));
            try {
                channelSftp.cd(workPath);
            }
            catch (Exception e) {
                LOGGER.info("\u5207\u6362\u76ee\u5f55[" + workPath + "]\u5931\u8d25\uff0c\u5f53\u524d\u76ee\u5f55\uff1a" + channelSftp.pwd() + "\u5bb6\u76ee\u5f55" + channelSftp.getHome());
                throw new RuntimeException(VaAttachmentI18nUtil.getMessage("va.attachment.switch.path.error", workPath));
            }
            bis = channelSftp.get(filePath.substring(filePath.lastIndexOf("/") + 1));
            StreamUtils.copy(bis, (OutputStream)os);
            byte[] byArray = os.toByteArray();
            return byArray;
        }
        catch (Exception e) {
            LOGGER.error("sftp\u4e0b\u8f7d\u9644\u4ef6\u5931\u8d25", e);
            byte[] byArray = null;
            return byArray;
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                os.close();
                if (channelSftp != null && channelSftp.isConnected()) {
                    channelSftp.disconnect();
                }
                if (sshSession != null) {
                    sshSession.disconnect();
                }
            }
            catch (IOException e) {
                LOGGER.error("sftp\u91ca\u653e\u8fde\u63a5\u5931\u8d25", e);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static boolean deleteFileFromSftp(SchemeEntity fileEntity) {
        String filePath = fileEntity.getFilePath();
        Session sshSession = null;
        ChannelSftp channelSftp = null;
        try {
            sshSession = FTPFileUtil.getSftpClient(fileEntity);
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            channelSftp.rm(filePath);
            boolean bl = true;
            return bl;
        }
        catch (Exception e) {
            LOGGER.error("sftp\u5220\u9664\u6587\u4ef6\u5931\u8d25", e);
            boolean bl = false;
            return bl;
        }
        finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (sshSession != null) {
                sshSession.disconnect();
            }
        }
    }

    public static FTPClient getFtpClient(SchemeEntity fileEntity) {
        String config = fileEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        int port = Integer.valueOf(schemeConfig.get("port").toString());
        String address = schemeConfig.get("attaddress").toString();
        String username = schemeConfig.get("username").toString();
        String pwd = schemeConfig.get("pwd").toString();
        FTPClient ftpClient = new FTPClient();
        ftpClient.setRemoteVerificationEnabled(true);
        ftpClient.setControlEncoding("UTF-8");
        ftpClient.setConnectTimeout(30000);
        ftpClient.setCharset(Charset.forName("UTF-8"));
        try {
            ftpClient.connect(address, port);
            ftpClient.login(username, pwd);
            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion((int)replyCode)) {
                ftpClient.disconnect();
                LOGGER.error("FTP\u8fde\u63a5\u5f02\u5e38");
                return null;
            }
            ftpClient.setFileType(2);
            ftpClient.enterLocalPassiveMode();
            return ftpClient;
        }
        catch (Exception e) {
            LOGGER.error("FTP\u8fde\u63a5\u5f02\u5e38", e);
            return null;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static R connectFtp(SchemeEntity fileEntity) {
        String config = fileEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        if (schemeConfig == null) {
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.config.not.set", new Object[0]));
        }
        if (!(schemeConfig.containsKey("port") && schemeConfig.containsKey("attaddress") && schemeConfig.containsKey("username") && schemeConfig.containsKey("pwd"))) {
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.config.not.incomplete", new Object[0]));
        }
        int port = Integer.valueOf(schemeConfig.get("port").toString());
        String address = schemeConfig.get("attaddress").toString();
        String username = schemeConfig.get("username").toString();
        String pwd = schemeConfig.get("pwd").toString();
        FTPClient ftpClient = new FTPClient();
        ftpClient.setRemoteVerificationEnabled(false);
        try {
            ftpClient.setControlEncoding("UTF-8");
            ftpClient.setCharset(Charset.forName("UTF-8"));
            ftpClient.setConnectTimeout(5000);
            ftpClient.connect(address, port);
            ftpClient.setFileType(2);
            ftpClient.enterLocalPassiveMode();
            ftpClient.setFileTransferMode(10);
            ftpClient.login(username, pwd);
            if (ftpClient.getReplyCode() == 230) {
                R r = R.ok((String)VaAttachmentI18nUtil.getMessage("va.attachment.connect.success", new Object[0]));
                return r;
            }
            R r = R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.login.error", new Object[0]));
            return r;
        }
        catch (Exception e) {
            LOGGER.error("\u6d4b\u8bd5\u8fde\u63a5FTP\u5931\u8d25", e);
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            try {
                if (ftpClient != null) {
                    if (ftpClient.getReplyCode() == 230) {
                        ftpClient.logout();
                    }
                    ftpClient.disconnect();
                }
            }
            catch (IOException e) {
                LOGGER.error("\u6d4b\u8bd5\u8fde\u63a5FTP\u91ca\u653e\u5931\u8d25", e);
            }
        }
    }

    public static Session getSftpClient(SchemeEntity fileEntity) throws JSchException {
        String config = fileEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        int port = Integer.valueOf(schemeConfig.get("port").toString());
        String address = schemeConfig.get("attaddress").toString();
        String username = schemeConfig.get("username").toString();
        String pwd = schemeConfig.get("pwd").toString();
        JSch jsch = new JSch();
        Session sshSession = null;
        sshSession = jsch.getSession(username, address, port);
        sshSession.setPassword(pwd);
        Properties properties = new Properties();
        properties.put("StrictHostKeyChecking", "no");
        sshSession.setConfig(properties);
        sshSession.setTimeout(5000);
        sshSession.connect();
        return sshSession;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static R connectSftp(SchemeEntity fileEntity) {
        String config = fileEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        if (schemeConfig == null) {
            return R.error((String)"\u672a\u8bbe\u7f6e\u9644\u4ef6\u65b9\u6848\u914d\u7f6e\u4fe1\u606f");
        }
        if (!(schemeConfig.containsKey("port") && schemeConfig.containsKey("attaddress") && schemeConfig.containsKey("username") && schemeConfig.containsKey("pwd"))) {
            return R.error((String)"\u9644\u4ef6\u65b9\u6848\u8fde\u63a5\u4fe1\u606f\u4e0d\u5b8c\u6574");
        }
        int port = Integer.valueOf(schemeConfig.get("port").toString());
        String address = schemeConfig.get("attaddress").toString();
        String username = schemeConfig.get("username").toString();
        String pwd = schemeConfig.get("pwd").toString();
        JSch jsch = new JSch();
        Session sshSession = null;
        ChannelSftp channelSftp = null;
        try {
            sshSession = jsch.getSession(username, address, port);
            sshSession.setPassword(pwd);
            Properties properties = new Properties();
            properties.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(properties);
            sshSession.setTimeout(5000);
            sshSession.connect();
            Channel channel = sshSession.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp)channel;
            R r = R.ok((String)VaAttachmentI18nUtil.getMessage("va.attachment.connect.success", new Object[0]));
            return r;
        }
        catch (Exception e) {
            LOGGER.error("\u6d4b\u8bd5\u8fde\u63a5sftp\u5931\u8d25", e);
            R r = R.error((String)e.getMessage());
            return r;
        }
        finally {
            if (channelSftp != null && channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
            if (sshSession != null) {
                sshSession.disconnect();
            }
        }
    }
}

