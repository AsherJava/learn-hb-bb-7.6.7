/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.config.ConfigItem
 *  com.jiuqi.va.attachment.config.ElementType
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.attachment.domain.exception.VaAttachmentException
 *  com.jiuqi.va.attachment.entity.ftp.FtpConfig
 *  com.jiuqi.va.attachment.utils.ftp.FtpUtils
 *  com.jiuqi.va.domain.common.AesCipherUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  org.apache.commons.net.ftp.FTPClient
 *  org.apache.commons.net.ftp.FTPFile
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl.handle;

import com.jiuqi.va.attachment.common.AttachmentConst;
import com.jiuqi.va.attachment.common.FTPFileUtil;
import com.jiuqi.va.attachment.common.FileUtil;
import com.jiuqi.va.attachment.config.ConfigItem;
import com.jiuqi.va.attachment.config.ElementType;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.domain.exception.VaAttachmentException;
import com.jiuqi.va.attachment.entity.ftp.FtpConfig;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.attachment.utils.VaAttachmentIOUtils;
import com.jiuqi.va.attachment.utils.VaAttachmentZipUtils;
import com.jiuqi.va.attachment.utils.ftp.FtpUtils;
import com.jiuqi.va.domain.common.AesCipherUtil;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipOutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentFtpHandleIntfImpl
implements AttachmentHandleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentFtpHandleIntfImpl.class);
    @Autowired
    private AttachmentBizHelpService bizHelpService;
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;

    public String getStoreTitle() {
        return "FTP";
    }

    public int getStoremode() {
        return AttachmentConst.FTP;
    }

    public R upload(MultipartFile file, AttachmentBizDTO bizAttachmentDTO, SchemeEntity schemeEntity, AttachmentModeDTO attmode) {
        long fileSize;
        String qcode = bizAttachmentDTO.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return R.error();
        }
        String fileName = file.getOriginalFilename();
        R fileCheckRs = this.bizHelpService.checkFile(attmode, fileName, fileSize = file.getSize());
        if (fileCheckRs.getCode() != 0) {
            return fileCheckRs;
        }
        Integer degree = schemeEntity.getDegree();
        String schemename = schemeEntity.getSchemename();
        String fileNameNew = FileUtil.renameToUUID(fileName);
        String year = qcode.substring(0, 4);
        String month = qcode.substring(4, 6);
        StringBuilder filePath = new StringBuilder();
        filePath.append("biz_attachment/");
        Map config = JSONUtil.parseMap((String)schemeEntity.getSchemeConfig());
        if (!ObjectUtils.isEmpty(config) && !ObjectUtils.isEmpty(config.get("attpath"))) {
            String attpath = config.get("attpath").toString();
            if (attpath.startsWith("/")) {
                attpath = attpath.substring(1);
            }
            if (attpath.endsWith("/")) {
                attpath = attpath.substring(0, attpath.length() - 1);
            }
            if (StringUtils.hasText(attpath)) {
                filePath.append(attpath).append("/");
            }
        }
        if (degree == 1) {
            filePath.append(year).append("/");
        } else if (degree == 2) {
            filePath.append(year).append("/").append(month).append("/");
        }
        schemeEntity.setFileName(fileNameNew);
        schemeEntity.setFilePath(filePath.toString());
        BigDecimal bd = new BigDecimal(file.getSize() / 1024L);
        bizAttachmentDTO.setFilesize(bd);
        bizAttachmentDTO.setName(fileName);
        bizAttachmentDTO.setFilepath(filePath + fileNameNew);
        bizAttachmentDTO.setSuffix(qcode.substring(0, 6));
        bizAttachmentDTO.setId(this.bizHelpService.getFileId(fileName, bizAttachmentDTO));
        if (bizAttachmentDTO.getCreatetime() == null) {
            bizAttachmentDTO.setCreatetime(new Date());
        }
        bizAttachmentDTO.setSchemename(schemename);
        if (bizAttachmentDTO.getStatus() == null) {
            bizAttachmentDTO.setStatus(Integer.valueOf(1));
        }
        boolean flag = false;
        try {
            flag = FTPFileUtil.uploadFileToFtp(file, schemeEntity);
        }
        catch (Exception e) {
            LOGGER.error(" \u4e0a\u4f20\u6587\u4ef6\u5230FTP\u5931\u8d25\uff1a" + e.getMessage(), e);
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.upload.error", new Object[0]));
        }
        if (flag && this.bizHelpService.add(fileName, bizAttachmentDTO) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean copyFile(AttachmentBizDTO param, SchemeEntity schemeEntity, String newQuoteCode) {
        FTPClient ftpClient = FTPFileUtil.getFtpClient(schemeEntity);
        if (ftpClient == null) {
            LOGGER.error("FTP\u4e0a\u4f20\u6587\u4ef6\u5931\u8d25: \u83b7\u53d6\u8fde\u63a5\u5f02\u5e38");
            return false;
        }
        FTPClient ftpClient1 = null;
        String oldPath = param.getFilepath();
        InputStream inputStream = null;
        try {
            FTPFile[] files = ftpClient.listFiles(oldPath);
            if (files != null && files.length > 0) {
                inputStream = ftpClient.retrieveFileStream(oldPath);
                ftpClient1 = FTPFileUtil.getFtpClient(schemeEntity);
                UUID fileId = UUID.randomUUID();
                String newPath = oldPath.substring(0, oldPath.lastIndexOf("/") + 1) + fileId + oldPath.substring(oldPath.lastIndexOf("."));
                boolean b = ftpClient1.storeFile(newPath, inputStream);
                if (b) {
                    param.setId(fileId);
                    param.setFilepath(newPath);
                    param.setQuotecode(newQuoteCode);
                    param.setSuffix(newQuoteCode.split("-")[0]);
                    boolean bl = this.bizAttachmentDao.add((AttachmentBizDO)param) > 0;
                    return bl;
                }
            } else {
                LOGGER.error("\u6e90\u6587\u4ef6\u4e0d\u5b58\u5728: " + param.getQuotecode() + ":" + param.getId());
            }
            boolean bl = false;
            return bl;
        }
        catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            boolean bl = false;
            return bl;
        }
        finally {
            try {
                ftpClient.logout();
            }
            catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
            if (ftpClient1 != null) {
                try {
                    ftpClient1.logout();
                }
                catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    public R remove(AttachmentBizDTO attachmentBizDTO) {
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDTO.getSchemename());
        schemeEntity.setFilePath(attachmentBizDTO.getFilepath());
        boolean flag = FTPFileUtil.deleteFileFromFtp(schemeEntity);
        if (flag) {
            return R.ok();
        }
        return R.error();
    }

    public void download(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return;
        }
        param.setSuffix(qcode.substring(0, 6));
        AttachmentBizDTO attachmentBizDO = this.bizHelpService.get((AttachmentBizDO)param);
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDO.getSchemename());
        schemeEntity.setFileName(attachmentBizDO.getName());
        schemeEntity.setFilePath(attachmentBizDO.getFilepath());
        try {
            FTPFileUtil.downloadFileFromFtp(schemeEntity, (AttachmentBizDO)attachmentBizDO);
        }
        catch (Exception e) {
            LOGGER.error(" \u4e0b\u8f7dFTP\u9644\u4ef6\u5931\u8d25\uff1a", e);
            return;
        }
    }

    public byte[] getFile(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        R r = this.bizHelpService.checkQtcode(qcode);
        if (r != null) {
            return null;
        }
        param.setSuffix(qcode.substring(0, 6));
        AttachmentBizDTO attachmentBizDO = this.bizHelpService.get((AttachmentBizDO)param);
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDO.getSchemename());
        schemeEntity.setFileName(attachmentBizDO.getName());
        schemeEntity.setFilePath(attachmentBizDO.getFilepath());
        try {
            return FTPFileUtil.getFileFromFtp(schemeEntity, (AttachmentBizDO)attachmentBizDO);
        }
        catch (Exception e) {
            LOGGER.error(" \u4e0b\u8f7dFTP\u9644\u4ef6\u5931\u8d25\uff1a", e);
            return null;
        }
    }

    public void zipDownLoad(AttachmentBizDTO param, ZipOutputStream zipOutputStream, String zipPath) {
        SchemeEntity schemeEntity = (SchemeEntity)param.getExtInfo("schemeEntity");
        schemeEntity.setFileName(param.getName());
        schemeEntity.setFilePath(param.getFilepath());
        AttachmentFtpHandleIntfImpl.zipDownLoadByFtp(schemeEntity, zipOutputStream, zipPath);
    }

    private static void zipDownLoadByFtp(SchemeEntity schemeEntity, ZipOutputStream zipOutputStream, String zipPath) {
        FtpConfig ftpConfig;
        FTPClient ftpClient;
        block18: {
            ftpClient = null;
            ftpConfig = null;
            try {
                String filePath = schemeEntity.getFilePath();
                String workPath = filePath.substring(0, filePath.lastIndexOf("/"));
                ftpConfig = VaAttachmentZipUtils.getFtpConfig(schemeEntity, workPath);
                ftpClient = FtpUtils.getClient((FtpConfig)ftpConfig);
                filePath = new String(filePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
                try (InputStream is = ftpClient.retrieveFileStream(filePath.substring(filePath.lastIndexOf("/") + 1));){
                    VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipPath, is);
                }
                if (ftpClient.completePendingCommand()) {
                    LOGGER.debug("\u6587\u4ef6\u4e0b\u8f7d\u5b8c\u6210: " + filePath);
                    break block18;
                }
                LOGGER.debug("\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25: " + filePath);
            }
            catch (Exception e) {
                try {
                    LOGGER.error(e.getMessage(), e);
                    throw new VaAttachmentException(e);
                }
                catch (Throwable throwable) {
                    FtpUtils.returnClient(ftpConfig, ftpClient);
                    throw throwable;
                }
            }
        }
        FtpUtils.returnClient((FtpConfig)ftpConfig, (FTPClient)ftpClient);
    }

    public List<ConfigItem> getSchemeConfigItems() {
        ArrayList<ConfigItem> configItems = new ArrayList<ConfigItem>();
        ConfigItem address = new ConfigItem();
        address.setName("attaddress");
        address.setTitle("\u670d\u52a1\u5730\u5740");
        address.setElementType(ElementType.TEXT);
        address.setRequired(true);
        configItems.add(address);
        ConfigItem port = new ConfigItem();
        port.setName("port");
        port.setTitle("\u670d\u52a1\u7aef\u53e3");
        port.setElementType(ElementType.TEXT);
        port.setRequired(true);
        configItems.add(port);
        ConfigItem username = new ConfigItem();
        username.setName("username");
        username.setTitle("\u7528\u6237\u540d");
        username.setElementType(ElementType.TEXT);
        username.setRequired(true);
        configItems.add(username);
        ConfigItem pwd = new ConfigItem();
        pwd.setName("pwd");
        pwd.setTitle("\u5bc6\u7801");
        pwd.setElementType(ElementType.PASSWORD);
        pwd.setRequired(true);
        configItems.add(pwd);
        return configItems;
    }

    public void processSchemeConfig(AttachmentSchemeDO attachmentSchemeDO) {
        if (attachmentSchemeDO == null || !StringUtils.hasText(attachmentSchemeDO.getConfig())) {
            return;
        }
        Map config = JSONUtil.parseMap((String)attachmentSchemeDO.getConfig());
        if (config != null && config.get("pwd") != null && StringUtils.hasText(config.get("pwd").toString())) {
            config.put("pwd", AesCipherUtil.encode((String)config.get("pwd").toString()));
            attachmentSchemeDO.setConfig(JSONUtil.toJSONString((Object)config));
        }
    }

    public void parseSchemeConfig(AttachmentSchemeDO attachmentSchemeDO) {
        if (attachmentSchemeDO == null || !StringUtils.hasText(attachmentSchemeDO.getConfig())) {
            return;
        }
        Map config = JSONUtil.parseMap((String)attachmentSchemeDO.getConfig());
        if (config != null && config.get("pwd") != null && StringUtils.hasText(config.get("pwd").toString())) {
            config.put("pwd", AesCipherUtil.decode((String)config.get("pwd").toString()));
            attachmentSchemeDO.setConfig(JSONUtil.toJSONString((Object)config));
        }
    }

    public boolean testConnectFlag() {
        return true;
    }
}

