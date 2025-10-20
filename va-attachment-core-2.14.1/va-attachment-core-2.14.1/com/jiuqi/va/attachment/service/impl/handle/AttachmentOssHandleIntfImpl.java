/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.config.ConfigElement
 *  com.jiuqi.va.attachment.config.ConfigItem
 *  com.jiuqi.va.attachment.config.ElementType
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.attachment.domain.exception.VaAttachmentException
 *  com.jiuqi.va.attachment.entity.AttachmentMultipartFile
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.PageVO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.feign.util.RequestContextUtil
 *  com.jiuqi.va.oss.feign.client.VaOssFeignClient
 *  com.jiuqi.va.oss.feign.domain.VaOssDTO
 *  feign.Response
 *  feign.Response$Body
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl.handle;

import com.jiuqi.va.attachment.common.AttachmentConst;
import com.jiuqi.va.attachment.common.FileUtil;
import com.jiuqi.va.attachment.config.ConfigElement;
import com.jiuqi.va.attachment.config.ConfigItem;
import com.jiuqi.va.attachment.config.ElementType;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.domain.exception.VaAttachmentException;
import com.jiuqi.va.attachment.entity.AttachmentMultipartFile;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.utils.VaAttachmentIOUtils;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.PageVO;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.feign.util.RequestContextUtil;
import com.jiuqi.va.oss.feign.client.VaOssFeignClient;
import com.jiuqi.va.oss.feign.domain.VaOssDTO;
import feign.Response;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.zip.ZipOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentOssHandleIntfImpl
implements AttachmentHandleIntf {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentOssHandleIntfImpl.class);
    @Autowired
    private AttachmentBizHelpService bizHelpService;
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;
    @Autowired
    private VaOssFeignClient vaOssFeignClient;

    public String getStoreTitle() {
        return "OSS";
    }

    public int getStoremode() {
        return AttachmentConst.OSS;
    }

    public R upload(MultipartFile file, AttachmentBizDTO bizAttachmentDTO, SchemeEntity schemeEntity, AttachmentModeDTO attmode) {
        String key;
        R upload;
        long fileSize;
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        String bucketname = schemeConfig.get("storagebucket").toString();
        String qcode = bizAttachmentDTO.getQuotecode();
        String fileName = file.getOriginalFilename();
        R fileCheckRs = this.bizHelpService.checkFile(attmode, fileName, fileSize = file.getSize());
        if (fileCheckRs.getCode() != 0) {
            return fileCheckRs;
        }
        String schemename = schemeEntity.getSchemename();
        String fileNameNew = FileUtil.renameToUUID(fileName);
        String year = qcode.substring(0, 4);
        String month = qcode.substring(4, 6);
        Integer degree = schemeEntity.getDegree();
        StringBuilder filePath = new StringBuilder();
        filePath.append("biz_attachment_");
        if (degree == 1) {
            filePath.append(year).append("_");
        } else if (degree == 2) {
            filePath.append(year).append("_").append(month).append("_");
        }
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
        if ((upload = this.vaOssFeignClient.upload(file, bucketname, key = filePath + fileNameNew)).getCode() == 1) {
            LOGGER.error("OSS\u4e0a\u4f20\u9644\u4ef6\u5931\u8d25" + upload.getMsg());
            return upload;
        }
        if (this.bizHelpService.add(fileName, bizAttachmentDTO) > 0) {
            return R.ok();
        }
        return R.error();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean copyFile(AttachmentBizDTO param, SchemeEntity schemeEntity, String newQuoteCode) {
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        String bucketname = schemeConfig.get("storagebucket").toString();
        String oldPath = param.getFilepath();
        UUID uuid = UUID.randomUUID();
        VaOssDTO vaOssDTO = new VaOssDTO();
        vaOssDTO.setKey(oldPath);
        vaOssDTO.setBucketName(bucketname);
        Response download = this.vaOssFeignClient.download(vaOssDTO);
        InputStream inputStream = null;
        try {
            inputStream = download.body().asInputStream();
            byte[] fileBytes = StreamUtils.copyToByteArray(inputStream);
            AttachmentMultipartFile multipartFile = new AttachmentMultipartFile(param.getName(), param.getName(), null, fileBytes);
            String newPath = oldPath.substring(0, oldPath.lastIndexOf("_")) + uuid + param.getName().substring(param.getName().lastIndexOf("."));
            R upload = this.vaOssFeignClient.upload((MultipartFile)multipartFile, bucketname, newPath);
            if (upload.getCode() == 0) {
                param.setId(uuid);
                param.setFilepath(newPath);
                param.setQuotecode(newQuoteCode);
                param.setSuffix(newQuoteCode.split("-")[0]);
                boolean bl = this.bizAttachmentDao.add((AttachmentBizDO)param) > 0;
                return bl;
            }
            LOGGER.error("OSS\u5b58\u50a8\u9644\u4ef6\u8fdb\u884c\u590d\u5236\u5931\u8d25" + upload.getMsg());
            boolean bl = false;
            return bl;
        }
        catch (IOException e) {
            LOGGER.error(e.getMessage(), e);
            boolean bl = false;
            return bl;
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                }
                catch (IOException e) {
                    LOGGER.error(e.getMessage(), e);
                }
            }
        }
    }

    public R remove(AttachmentBizDTO attachmentBizDTO) {
        Boolean flag;
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDTO.getSchemename());
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        String bucketname = schemeConfig.get("storagebucket").toString();
        schemeEntity.setFilePath(attachmentBizDTO.getFilepath());
        String qcode = attachmentBizDTO.getQuotecode();
        if (schemeEntity.getDegree() == 1) {
            schemeEntity.setSuffix(qcode.substring(0, 4));
        } else if (schemeEntity.getDegree() == 2) {
            schemeEntity.setSuffix(qcode.substring(0, 6));
        }
        AttachmentBizDO attachmentBizDO = new AttachmentBizDO();
        attachmentBizDO.setQuotecode(qcode);
        attachmentBizDO.setSuffix(qcode.substring(0, 6));
        schemeEntity.setKey(attachmentBizDTO.getId().toString());
        String filePath = schemeEntity.getFilePath();
        VaOssDTO vaOssDTO = new VaOssDTO();
        vaOssDTO.setBucketName(bucketname);
        vaOssDTO.setKey(filePath);
        R remove = this.vaOssFeignClient.remove(vaOssDTO);
        if (remove.getCode() == 0 && (flag = (Boolean)remove.get((Object)"flag")).booleanValue()) {
            return R.ok();
        }
        return R.error();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void download(AttachmentBizDTO param) {
        String qcode = param.getQuotecode();
        param.setSuffix(qcode.substring(0, 6));
        AttachmentBizDTO attachmentBizDO = this.bizAttachmentDao.get((AttachmentBizDO)param);
        SchemeEntity schemeEntity = this.bizHelpService.getMessageFromScheme(attachmentBizDO.getSchemename());
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        String bucketname = schemeConfig.get("storagebucket").toString();
        VaOssDTO vaOssDTO = new VaOssDTO();
        vaOssDTO.setBucketName(bucketname);
        vaOssDTO.setKey(attachmentBizDO.getFilepath());
        vaOssDTO.setFileName(attachmentBizDO.getName());
        Response download = this.vaOssFeignClient.download(vaOssDTO);
        InputStream bis = null;
        OutputStream os = null;
        try {
            bis = download.body().asInputStream();
            RequestContextUtil.setResponseContentType((String)"application/x-download");
            try {
                RequestContextUtil.setResponseHeader((String)"Content-Disposition", (String)("attachment;filename*=utf-8'zh_cn'" + URLEncoder.encode(new String(vaOssDTO.getFileName().getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1), "iso-8859-1")));
            }
            catch (Exception e) {
                LOGGER.error(" OSS \u4e0b\u8f7d\u5931\u8d25", e);
            }
            os = RequestContextUtil.getOutputStream();
            int cont = 0;
            while ((cont = bis.read()) != -1) {
                os.write(cont);
            }
        }
        catch (IOException e) {
            LOGGER.error(" OSS \u4e0b\u8f7d\u5931\u8d25\uff1a", e);
        }
        finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                if (os != null) {
                    os.close();
                }
            }
            catch (IOException e) {
                LOGGER.error(" OSS \u4e0b\u8f7d\u5173\u95ed\u6d41\u5931\u8d25", e);
            }
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
        SchemeEntity schemeEntity = (SchemeEntity)param.getExtInfo("schemeEntity");
        String config = schemeEntity.getSchemeConfig();
        Map schemeConfig = JSONUtil.parseMap((String)config);
        String bucketName = schemeConfig.get("storagebucket").toString();
        VaOssDTO vaOssDTO = new VaOssDTO();
        vaOssDTO.setBucketName(bucketName);
        vaOssDTO.setKey(param.getFilepath());
        vaOssDTO.setFileName(param.getName());
        try (Response download = this.vaOssFeignClient.download(vaOssDTO);){
            Response.Body body = download.body();
            if (Objects.isNull(body)) {
                LOGGER.error("OSS \u4e0b\u8f7d\u5931\u8d25\uff1abody\u4e3a\u7a7a");
                throw new VaAttachmentException("OSS \u4e0b\u8f7d\u5931\u8d25\uff1abody\u4e3a\u7a7a");
            }
            try (InputStream bis = body.asInputStream();){
                VaAttachmentIOUtils.writeFileToZip(zipOutputStream, zipPath, bis);
            }
            catch (IOException e) {
                LOGGER.error("OSS \u4e0b\u8f7d\u5931\u8d25\uff1a", e);
                throw new VaAttachmentException((Exception)e);
            }
        }
    }

    public List<ConfigItem> getSchemeConfigItems() {
        ArrayList<ConfigItem> configItems = new ArrayList<ConfigItem>();
        ConfigItem storagebucket = new ConfigItem();
        storagebucket.setName("storagebucket");
        storagebucket.setTitle("\u5bf9\u8c61/\u6587\u4ef6");
        storagebucket.setElementType(ElementType.LIST);
        storagebucket.setRequired(true);
        try {
            PageVO pageVO = this.vaOssFeignClient.listBucket(true);
            List buckets = pageVO.getRows();
            if (buckets != null && !buckets.isEmpty()) {
                ArrayList<ConfigElement> options = new ArrayList<ConfigElement>();
                for (Map bucket : buckets) {
                    ConfigElement element = new ConfigElement();
                    element.setName(bucket.get("id").toString());
                    element.setTitle(bucket.get("multipartUploader").toString());
                    options.add(element);
                }
                storagebucket.setOptions(options);
            }
        }
        catch (Exception e) {
            LOGGER.info("\u672a\u83b7\u53d6\u5230oss\u5b58\u50a8bucket\u5217\u8868");
        }
        configItems.add(storagebucket);
        ConfigItem multipartFile = new ConfigItem();
        multipartFile.setName("chunkupload");
        multipartFile.setTitle("\u652f\u6301\u5206\u7247");
        multipartFile.setElementType(ElementType.BOOLEAN);
        configItems.add(multipartFile);
        return configItems;
    }
}

