/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  org.apache.commons.io.IOUtils
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl.handle;

import com.jiuqi.va.attachment.common.AttachmentConst;
import com.jiuqi.va.attachment.common.FileUtil;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.attachment.utils.VaAttachmentIOUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.feign.util.RequestContextUtil;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipOutputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentServerHandleIntfImpl
implements AttachmentHandleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentServerHandleIntfImpl.class);
    @Autowired
    private AttachmentBizHelpService bizHelpService;
    @Value(value="${upload.path}")
    private String localpath;
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;

    public String getStoreTitle() {
        return "\u670d\u52a1\u5668\u76ee\u5f55";
    }

    public int getStoremode() {
        return AttachmentConst.SERVER;
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
        String path = schemeEntity.getFilePath();
        String schemename = schemeEntity.getSchemename();
        String fileNameNew = FileUtil.renameToUUID(fileName);
        String year = qcode.substring(0, 4);
        String month = qcode.substring(4, 6);
        StringBuilder filePath = new StringBuilder();
        if (StringUtils.hasText(path)) {
            filePath.append("/".equals(path) ? "" : path);
            filePath.append("/");
        }
        filePath.append("/" + ShiroUtil.getTenantName());
        filePath.append("/biz_attachment/");
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
        try {
            FileUtil.uploadFile(file, this.localpath + filePath, fileNameNew);
            if (this.bizHelpService.add(fileName, bizAttachmentDTO) > 0) {
                return R.ok();
            }
        }
        catch (Exception e) {
            LOGGER.error(" \u6587\u4ef6 SERVER \u4e0a\u4f20\u5931\u8d25" + e);
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.file.server.error", new Object[0]));
        }
        return R.error();
    }

    public boolean copyFile(AttachmentBizDTO param, SchemeEntity schemeEntity, String newQuoteCode) {
        String oldpath = this.localpath + param.getFilepath();
        UUID fileId = UUID.randomUUID();
        String fileName = param.getName();
        String newFileName = fileId + "." + fileName.substring(fileName.lastIndexOf(".") + 1);
        String newpath = this.localpath + param.getFilepath().substring(0, param.getFilepath().lastIndexOf("/") + 1) + newFileName;
        File sourceFile = new File(oldpath);
        File destinationFile = new File(newpath);
        try (FileInputStream inputStream = new FileInputStream(sourceFile);
             FileOutputStream outputStream = new FileOutputStream(destinationFile);){
            IOUtils.copy((InputStream)inputStream, (OutputStream)outputStream);
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            return false;
        }
        param.setId(fileId);
        param.setFilepath(param.getFilepath().substring(0, param.getFilepath().lastIndexOf("/") + 1) + newFileName);
        param.setQuotecode(newQuoteCode);
        param.setSuffix(newQuoteCode.split("-")[0]);
        return this.bizAttachmentDao.add((AttachmentBizDO)param) > 0;
    }

    public R remove(AttachmentBizDTO attachmentBizDTO) {
        Boolean flag = false;
        if (attachmentBizDTO != null && attachmentBizDTO.getFilepath() != null) {
            try {
                flag = FileUtil.deleteFile(this.localpath + attachmentBizDTO.getFilepath());
            }
            catch (Exception e) {
                LOGGER.error(" \u6587\u4ef6 SERVER \u5220\u9664\u5931\u8d25" + e);
                return R.error();
            }
        }
        if (flag.booleanValue()) {
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
        OutputStream os = null;
        String path = attachmentBizDO.getFilepath();
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(this.localpath + path)));){
            RequestContextUtil.setResponseCharacterEncoding((String)"UTF-8");
            if (param.getForceDownload() == null || param.getForceDownload().booleanValue()) {
                try {
                    RequestContextUtil.setResponseContentType((String)"application/x-download");
                    RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(attachmentBizDO.getName(), "UTF-8")));
                }
                catch (Exception e) {
                    LOGGER.error(" \u6587\u4ef6 SERVER \u4e0b\u8f7d\u5931\u8d25", e);
                }
            }
            os = RequestContextUtil.getOutputStream();
            int cont = 0;
            while ((cont = bis.read()) != -1) {
                os.write(cont);
            }
        }
        catch (Exception e) {
            LOGGER.error(" \u6587\u4ef6 SERVER \u4e0b\u8f7d\u5931\u8d25", e);
        }
    }

    /*
     * Exception decompiling
     */
    public byte[] getFile(AttachmentBizDTO param) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
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

    public void zipDownLoad(AttachmentBizDTO param, ZipOutputStream zipOutputStream, String zipPath) {
        String path = param.getFilepath();
        File file = new File(this.localpath + path);
        try (InputStream inputStream = Files.newInputStream(file.toPath(), new OpenOption[0]);){
            VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipPath, inputStream);
        }
        catch (IOException e) {
            LOGGER.error("\u6587\u4ef6\u4e0b\u8f7d\u5931\u8d25", e);
            throw new RuntimeException(e);
        }
    }
}

