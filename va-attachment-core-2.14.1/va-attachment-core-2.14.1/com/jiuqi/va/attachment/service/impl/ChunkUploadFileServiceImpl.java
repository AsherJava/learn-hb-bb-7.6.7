/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.oss.feign.client.VaOssChunkUploadFeignClient
 *  com.jiuqi.va.oss.feign.domain.VaChunkUploadObjectInfo
 *  com.jiuqi.va.oss.feign.domain.VaChunkUploadVO
 *  org.springframework.web.multipart.MultipartFile
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.common.FileUtil;
import com.jiuqi.va.attachment.dao.VaAttachmentModeDao;
import com.jiuqi.va.attachment.dao.VaAttachmentSchemeDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.service.ChunkUploadFileService;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.oss.feign.client.VaOssChunkUploadFeignClient;
import com.jiuqi.va.oss.feign.domain.VaChunkUploadObjectInfo;
import com.jiuqi.va.oss.feign.domain.VaChunkUploadVO;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ChunkUploadFileServiceImpl
implements ChunkUploadFileService {
    private static final Logger logger = LoggerFactory.getLogger(ChunkUploadFileServiceImpl.class);
    @Autowired
    private VaOssChunkUploadFeignClient vaOssChunkUploadFeignClient;
    @Autowired
    private VaAttachmentModeDao vaAttachmentModeDao;
    @Autowired
    private VaAttachmentSchemeDao vaAttachmentSchemeDao;
    @Autowired
    private AttachmentBizHelpService bizHelpService;

    @Override
    public R init(VaChunkUploadObjectInfo vaChunkUploadObjectInfo) {
        if (!StringUtils.hasText(vaChunkUploadObjectInfo.getBucketName())) {
            return R.error((String)"bucket\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(vaChunkUploadObjectInfo.getName())) {
            return R.error((String)"\u6587\u4ef6\u540d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        String modename = vaChunkUploadObjectInfo.getExtInfo().get("modename").toString();
        String quotecode = vaChunkUploadObjectInfo.getExtInfo().get("quotecode").toString();
        R r = this.bizHelpService.checkQtcode(quotecode);
        if (r != null) {
            return r;
        }
        AttachmentModeDO attachmentModeDO = new AttachmentModeDO();
        attachmentModeDO.setName(modename);
        AttachmentModeDTO attMode = this.vaAttachmentModeDao.getAttMode(attachmentModeDO);
        R fileCheckRs = this.bizHelpService.checkFile(attMode, vaChunkUploadObjectInfo.getName(), Long.parseLong(vaChunkUploadObjectInfo.getExtInfo().get("fileSize").toString()));
        if (fileCheckRs.getCode() != 0) {
            return fileCheckRs;
        }
        AttachmentSchemeDO attachmentSchemeDO = new AttachmentSchemeDO();
        attachmentSchemeDO.setName(attMode.getSchemename());
        Integer degree = ((AttachmentSchemeDO)this.vaAttachmentSchemeDao.selectOne(attachmentSchemeDO)).getDegree();
        String year = quotecode.substring(0, 4);
        String month = quotecode.substring(4, 6);
        StringBuilder filePath = new StringBuilder();
        filePath.append("biz_attachment_");
        if (degree == 1) {
            filePath.append(year).append("_");
        } else if (degree == 2) {
            filePath.append(year).append("_").append(month).append("_");
        }
        String fileNameNew = FileUtil.renameToUUID(vaChunkUploadObjectInfo.getName());
        VaChunkUploadObjectInfo param = new VaChunkUploadObjectInfo();
        param.setBucketName(vaChunkUploadObjectInfo.getBucketName());
        param.setOwner(ShiroUtil.getUser().getUsername());
        param.setName(vaChunkUploadObjectInfo.getName());
        param.setKey(filePath + fileNameNew);
        return this.vaOssChunkUploadFeignClient.init(param).put("key", (Object)param.getKey());
    }

    @Override
    public R upload(MultipartFile file, VaChunkUploadVO vaChunkUploadVO) {
        if (!StringUtils.hasText(vaChunkUploadVO.getBucketName())) {
            return R.error((String)"bucket\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(vaChunkUploadVO.getKey())) {
            return R.error((String)"key\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (vaChunkUploadVO.getPartNum() == 0) {
            return R.error((String)"partNum\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (vaChunkUploadVO.getTotalNum() == 0) {
            return R.error((String)"totalNum\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (vaChunkUploadVO.getPartSize() == 0L) {
            return R.error((String)"partSize\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (vaChunkUploadVO.getByteOffset() == 0L) {
            return R.error((String)"byteOffset\u4e0d\u80fd\u4e3a\u7a7a");
        }
        R upload = this.vaOssChunkUploadFeignClient.upload(file, vaChunkUploadVO);
        if (upload.get((Object)"data") == null) {
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.chunk.upload.error", new Object[0]));
        }
        return upload;
    }

    @Override
    public R finishUpload(VaChunkUploadObjectInfo vaChunkUploadObjectInfo) {
        Object quotecode = vaChunkUploadObjectInfo.getExtInfo("quotecode");
        if (quotecode == null) {
            return R.error((String)"\u9644\u4ef6\u5f15\u7528\u7801\u4e0d\u80fd\u4e3a\u7a7a");
        }
        R er = this.bizHelpService.checkQtcode(quotecode.toString());
        if (er != null) {
            return er;
        }
        if (!StringUtils.hasText(vaChunkUploadObjectInfo.getName())) {
            return R.error((String)"\u6587\u4ef6\u540d\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(vaChunkUploadObjectInfo.getKey())) {
            return R.error((String)"key\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (!StringUtils.hasText(vaChunkUploadObjectInfo.getUploadId())) {
            return R.error((String)"uploadId\u4e0d\u80fd\u4e3a\u7a7a");
        }
        if (vaChunkUploadObjectInfo.getExtInfo().get("byteOffset") == null) {
            return R.error((String)"\u6587\u4ef6\u5927\u5c0f\u4e0d\u80fd\u4e3a\u7a7a");
        }
        R r = this.vaOssChunkUploadFeignClient.finishUpload(vaChunkUploadObjectInfo);
        if (r.getCode() == 0) {
            AttachmentBizDTO bizAttachmentDTO = new AttachmentBizDTO();
            bizAttachmentDTO.setFilesize(BigDecimal.valueOf(Long.parseLong(vaChunkUploadObjectInfo.getExtInfo().get("byteOffset").toString()) / 1024L));
            bizAttachmentDTO.setName(vaChunkUploadObjectInfo.getName());
            bizAttachmentDTO.setFilepath(vaChunkUploadObjectInfo.getKey());
            bizAttachmentDTO.setSuffix(quotecode.toString().substring(0, 6));
            bizAttachmentDTO.setFileIds((String)vaChunkUploadObjectInfo.getExtInfo("fileIds"));
            bizAttachmentDTO.setFileNameList((List)vaChunkUploadObjectInfo.getExtInfo("fileNameList"));
            bizAttachmentDTO.setId(this.bizHelpService.getFileId(vaChunkUploadObjectInfo.getName(), bizAttachmentDTO));
            bizAttachmentDTO.setCreatetime(new Date());
            bizAttachmentDTO.setQuotecode(quotecode.toString());
            bizAttachmentDTO.setStatus(Integer.valueOf(0));
            bizAttachmentDTO.setCreateuser(ShiroUtil.getUser().getId());
            bizAttachmentDTO.setModename((String)vaChunkUploadObjectInfo.getExtInfo("modename"));
            bizAttachmentDTO.setSchemename((String)vaChunkUploadObjectInfo.getExtInfo("schemename"));
            bizAttachmentDTO.setBizcode((String)vaChunkUploadObjectInfo.getExtInfo("bizcode"));
            bizAttachmentDTO.setBiztype((String)vaChunkUploadObjectInfo.getExtInfo("biztype"));
            bizAttachmentDTO.setExtdata((String)vaChunkUploadObjectInfo.getExtInfo("extdata"));
            if (this.bizHelpService.add(vaChunkUploadObjectInfo.getName(), bizAttachmentDTO) > 0) {
                return R.ok();
            }
        }
        return R.error();
    }
}

