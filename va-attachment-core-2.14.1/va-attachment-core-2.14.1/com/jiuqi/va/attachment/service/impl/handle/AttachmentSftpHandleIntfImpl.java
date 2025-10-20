/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jcraft.jsch.ChannelSftp
 *  com.jiuqi.va.attachment.config.ConfigItem
 *  com.jiuqi.va.attachment.config.ElementType
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.attachment.domain.exception.VaAttachmentException
 *  com.jiuqi.va.attachment.entity.sftp.SftpConfig
 *  com.jiuqi.va.attachment.utils.sftp.SftpUtils
 *  com.jiuqi.va.domain.common.AesCipherUtil
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl.handle;

import com.jcraft.jsch.ChannelSftp;
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
import com.jiuqi.va.attachment.entity.sftp.SftpConfig;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.attachment.utils.VaAttachmentIOUtils;
import com.jiuqi.va.attachment.utils.VaAttachmentZipUtils;
import com.jiuqi.va.attachment.utils.sftp.SftpUtils;
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
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentSftpHandleIntfImpl
implements AttachmentHandleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentSftpHandleIntfImpl.class);
    @Autowired
    private AttachmentBizHelpService bizHelpService;
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;

    public String getStoreTitle() {
        return "SFTP";
    }

    public int getStoremode() {
        return AttachmentConst.SFTP;
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
            flag = FTPFileUtil.uploadFileToSftp(file, schemeEntity);
        }
        catch (IOException e) {
            LOGGER.error(" \u6587\u4ef6 SFTP \u4e0a\u4f20\u5931\u8d25", e);
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.file.sftp.error", new Object[0]));
        }
        if (flag && this.bizHelpService.add(fileName, bizAttachmentDTO) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /*
     * Exception decompiling
     */
    public boolean copyFile(AttachmentBizDTO param, SchemeEntity schemeEntity, String newQuoteCode) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJar(BatchJarDecompiler.java:77)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.decompileJars(BatchJarDecompiler.java:47)
         *     at com.openquartz.jardemo2.BatchJarDecompiler.main(BatchJarDecompiler.java:116)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public R remove(AttachmentBizDTO attachmentBizDTO) {
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDTO.getSchemename());
        schemeEntity.setFilePath(attachmentBizDTO.getFilepath());
        boolean flag = false;
        flag = FTPFileUtil.deleteFileFromSftp(schemeEntity);
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
        FTPFileUtil.downloadFileFromSftp(schemeEntity, (AttachmentBizDO)attachmentBizDO);
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
        return FTPFileUtil.getFileFromSftp(schemeEntity, (AttachmentBizDO)attachmentBizDO);
    }

    public void zipDownLoad(AttachmentBizDTO param, ZipOutputStream zipOutputStream, String zipPath) {
        SchemeEntity schemeEntity = (SchemeEntity)param.getExtInfo("schemeEntity");
        schemeEntity.setFileName(param.getName());
        schemeEntity.setFilePath(param.getFilepath());
        AttachmentSftpHandleIntfImpl.getSftpInputStream(schemeEntity, zipOutputStream, zipPath);
    }

    private static void getSftpInputStream(SchemeEntity schemeEntity, ZipOutputStream zipOutputStream, String zipPath) {
        String filePath = schemeEntity.getFilePath();
        filePath = new String(filePath.getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
        String workPath = filePath.substring(0, filePath.lastIndexOf("/"));
        SftpConfig sftpConfig = VaAttachmentZipUtils.getSftpConfig(schemeEntity, workPath);
        ChannelSftp channelSftp = null;
        try {
            channelSftp = SftpUtils.getChannelSftp((SftpConfig)sftpConfig);
            try (InputStream bis = channelSftp.get(filePath.substring(filePath.lastIndexOf("/") + 1));){
                VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipPath, bis);
            }
        }
        catch (Exception e) {
            LOGGER.error("sftp\u4e0b\u8f7d\u9644\u4ef6\u5931\u8d25", e);
            throw new VaAttachmentException(e.getMessage(), (Throwable)e);
        }
        finally {
            SftpUtils.returnChannelSftp((ChannelSftp)channelSftp, (SftpConfig)sftpConfig);
        }
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

