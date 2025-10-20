/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.config.CopyFileCache
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentHandleIntf
 *  com.jiuqi.va.attachment.domain.AttachmentModeDO
 *  com.jiuqi.va.attachment.domain.AttachmentModeDTO
 *  com.jiuqi.va.attachment.domain.AttachmentSchemeDO
 *  com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO
 *  com.jiuqi.va.attachment.domain.CopyProgressDO
 *  com.jiuqi.va.attachment.domain.SchemeEntity
 *  com.jiuqi.va.domain.common.AesCipherUtil
 *  com.jiuqi.va.domain.common.EnvConfig
 *  com.jiuqi.va.domain.common.JSONUtil
 *  com.jiuqi.va.domain.common.R
 *  com.jiuqi.va.domain.common.RedisLockUtil
 *  com.jiuqi.va.domain.common.ShiroUtil
 *  com.jiuqi.va.mapper.common.ApplicationContextRegister
 *  org.springframework.data.redis.core.StringRedisTemplate
 *  org.springframework.transaction.annotation.Transactional
 */
package com.jiuqi.va.attachment.service.impl.help;

import com.jiuqi.va.attachment.config.CopyFileCache;
import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.dao.VaAttachmentSchemeDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentHandleIntf;
import com.jiuqi.va.attachment.domain.AttachmentModeDO;
import com.jiuqi.va.attachment.domain.AttachmentModeDTO;
import com.jiuqi.va.attachment.domain.AttachmentSchemeDO;
import com.jiuqi.va.attachment.domain.BizAttachmentConfirmDTO;
import com.jiuqi.va.attachment.domain.CopyProgressDO;
import com.jiuqi.va.attachment.domain.SchemeEntity;
import com.jiuqi.va.attachment.service.AttachmentBizConfirmService;
import com.jiuqi.va.attachment.service.AttachmentBizHelpService;
import com.jiuqi.va.attachment.service.AttachmentBizService;
import com.jiuqi.va.attachment.service.AttachmentModeService;
import com.jiuqi.va.attachment.service.VaAttachmentBizRefService;
import com.jiuqi.va.attachment.storage.AttachmentBizStorage;
import com.jiuqi.va.attachment.utils.VaAttachmentI18nUtil;
import com.jiuqi.va.domain.common.AesCipherUtil;
import com.jiuqi.va.domain.common.EnvConfig;
import com.jiuqi.va.domain.common.JSONUtil;
import com.jiuqi.va.domain.common.R;
import com.jiuqi.va.domain.common.RedisLockUtil;
import com.jiuqi.va.domain.common.ShiroUtil;
import com.jiuqi.va.mapper.common.ApplicationContextRegister;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

@Service
public class AttachmentBizHelpServiceImpl
implements AttachmentBizHelpService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachmentBizHelpServiceImpl.class);
    private static final String ATT_COPY_KEY_PREFIX = "ATT_COPY_";
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;
    @Autowired
    private VaAttachmentSchemeDao attachmentSchemeDao;
    @Autowired
    private VaAttachmentBizDao vaAttachmentBizDao;
    @Autowired(required=false)
    private StringRedisTemplate redisTemplate;
    @Autowired
    private VaAttachmentBizRefService vaAttachmentBizRefService;
    @Autowired
    private AttachmentBizConfirmService attachmentBizConfirmService;

    @Override
    public R checkQtcode(String qcode) {
        if (!StringUtils.hasText(qcode)) {
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.quotecode.empty", new Object[0]));
        }
        if (qcode.length() != 43) {
            return R.error((String)VaAttachmentI18nUtil.getMessage("va.attachment.quotecode.format.error", new Object[0]));
        }
        try {
            Integer.parseInt(qcode.substring(0, 6));
        }
        catch (Exception e) {
            return R.error((String)"\u53c2\u6570\u975e\u6cd5");
        }
        return null;
    }

    @Override
    public R checkFile(AttachmentModeDTO attmode, String fileName, long fileSize) {
        Double attSize;
        if (attmode == null) {
            return R.ok();
        }
        String attType = attmode.getAtttype();
        if (attType != null) {
            attType = attType.trim().toLowerCase();
        }
        if (attType != null && StringUtils.hasText(attType)) {
            String fileNameType = fileName.substring(fileName.lastIndexOf(".") + 1);
            List<String> atttypeArr = Arrays.asList(attType.split(","));
            if (!atttypeArr.contains(fileNameType.toLowerCase())) {
                return R.error((String)(VaAttachmentI18nUtil.getMessage("va.attachment.upload.type.error", new Object[0]) + attType));
            }
        }
        if ((attSize = attmode.getAttsize()) != null && (double)fileSize > attSize * 1000.0) {
            return R.error((String)(VaAttachmentI18nUtil.getMessage("va.attachment.upload.size.error", new Object[0]) + attSize + "KB"));
        }
        return R.ok();
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public int add(String originalFilename, AttachmentBizDTO attachment) {
        AttachmentBizStorage.init(attachment.getTenantName(), attachment.getSuffix());
        this.sortAttachment(originalFilename, attachment);
        int add = this.bizAttachmentDao.add((AttachmentBizDO)attachment);
        if (add > 0) {
            BizAttachmentConfirmDTO attachmentConfirm = new BizAttachmentConfirmDTO();
            attachmentConfirm.setQuotecode(attachment.getQuotecode());
            attachmentConfirm.setBiztype(attachment.getBiztype());
            attachmentConfirm.setBizcode(attachment.getBizcode());
            attachmentConfirm.setExtdata(attachment.getExtdata());
            boolean insert = this.attachmentBizConfirmService.insert(attachmentConfirm);
            if (!insert) {
                LOGGER.error("\u6dfb\u52a0\u9644\u4ef6\u786e\u8ba4\u8868\u5931\u8d25:{}:{}:{}:{}", attachment.getQuotecode(), attachment.getBiztype(), attachment.getBizcode(), attachment.getExtdata());
                throw new RuntimeException("\u6dfb\u52a0\u9644\u4ef6\u786e\u8ba4\u8868\u5931\u8d25");
            }
        }
        return add;
    }

    @Override
    public AttachmentBizDTO get(AttachmentBizDO param) {
        return this.bizAttachmentDao.get(param);
    }

    @Override
    public SchemeEntity getMessageFromScheme(String scheme) {
        AttachmentSchemeDO attschemeParam = new AttachmentSchemeDO();
        attschemeParam.setName(scheme);
        AttachmentSchemeDO attscheme = (AttachmentSchemeDO)this.attachmentSchemeDao.selectOne(attschemeParam);
        if (attscheme == null) {
            return new SchemeEntity();
        }
        if (attscheme.getStoremode() != null) {
            switch (attscheme.getStoremode()) {
                case 0: 
                case 1: 
                case 4: {
                    this.parseSchemeConfig(attscheme);
                    break;
                }
            }
        }
        SchemeEntity schemeEntity = new SchemeEntity();
        schemeEntity.setStoremode(attscheme.getStoremode());
        schemeEntity.setDegree(attscheme.getDegree());
        schemeEntity.setSchemeConfig(attscheme.getConfig());
        return schemeEntity;
    }

    @Override
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

    @Override
    public void sortAttachment(String originalFilename, AttachmentBizDTO bizAttachmentDTO) {
        block5: {
            List fileNameList;
            block6: {
                List afterList;
                AttachmentBizDO query = new AttachmentBizDO();
                String qcode = bizAttachmentDTO.getQuotecode();
                query.setQuotecode(qcode);
                query.setSuffix(qcode.substring(0, 6));
                if (!StringUtils.hasText(bizAttachmentDTO.getFileIds())) {
                    return;
                }
                List<String> fileIdList = Arrays.asList(bizAttachmentDTO.getFileIds().split(","));
                List<AttachmentBizDTO> beforeList = this.vaAttachmentBizDao.list(query);
                Integer lastOrdinal = CollectionUtils.isEmpty(beforeList) ? Integer.valueOf(0) : Integer.valueOf((afterList = beforeList.stream().filter(o -> !fileIdList.contains(o.getId().toString())).collect(Collectors.toList())).size() == 0 ? 0 : (((AttachmentBizDTO)afterList.get(afterList.size() - 1)).getOrdinal() == null ? 0 : ((AttachmentBizDTO)afterList.get(afterList.size() - 1)).getOrdinal()));
                fileNameList = bizAttachmentDTO.getFileNameList();
                if (CollectionUtils.isEmpty(fileNameList)) break block5;
                if (lastOrdinal == 0) break block6;
                if (fileNameList.size() < 2) {
                    bizAttachmentDTO.setOrdinal(Integer.valueOf(lastOrdinal + 1));
                } else {
                    for (int i = 0; i < fileNameList.size(); ++i) {
                        if (!originalFilename.equals(fileNameList.get(i))) continue;
                        bizAttachmentDTO.setOrdinal(Integer.valueOf(lastOrdinal + 1 + i));
                        break block5;
                    }
                }
                break block5;
            }
            if (fileNameList.size() <= 1) break block5;
            for (int i = 0; i < fileNameList.size(); ++i) {
                if (!originalFilename.equals(fileNameList.get(i))) continue;
                bizAttachmentDTO.setOrdinal(Integer.valueOf(1 + i));
                break;
            }
        }
    }

    @Override
    public UUID getFileId(String originalFilename, AttachmentBizDTO bizAttachmentDTO) {
        List fileNameList = bizAttachmentDTO.getFileNameList();
        if (!CollectionUtils.isEmpty(fileNameList) && fileNameList.size() > 1) {
            List<String> fileIdList = Arrays.asList(bizAttachmentDTO.getFileIds().split(","));
            for (int i = 0; i < fileNameList.size(); ++i) {
                if (!originalFilename.equals(fileNameList.get(i))) continue;
                return UUID.fromString(fileIdList.get(i));
            }
        }
        return UUID.randomUUID();
    }

    @Override
    @Async
    public void asyncCopyFile(Map<String, String> quoteCodes, List<AttachmentHandleIntf> attachmentHandleIntfList, List<AttachmentBizDTO> list, String key) {
        CopyProgressDO copyProgressDO = new CopyProgressDO();
        copyProgressDO.setTotal(list.size());
        block4: for (int i = 0; i < list.size(); ++i) {
            AttachmentBizDTO attachmentBizDTO = list.get(i);
            AttachmentModeDO modeDO = new AttachmentModeDO();
            modeDO.setName(attachmentBizDTO.getModename());
            AttachmentModeService attachmentModeService = (AttachmentModeService)ApplicationContextRegister.getBean(AttachmentModeService.class);
            AttachmentModeDTO modeDTO = attachmentModeService.get(modeDO);
            AttachmentBizService attachmentBizService = (AttachmentBizService)ApplicationContextRegister.getBean(AttachmentBizService.class);
            SchemeEntity schemeEntity = attachmentBizService.getMessageFromMode(null, modeDTO);
            Integer storemode = schemeEntity.getStoremode();
            for (AttachmentHandleIntf attachment : attachmentHandleIntfList) {
                boolean flag;
                if (attachment.getStoremode() != storemode.intValue()) continue;
                LOGGER.info("\u8fdb\u884c\u9644\u4ef6\u590d\u5236\uff0c\u7c7b\u578b\uff1a" + attachment.getStoreTitle() + ", \u9644\u4ef6\u540d\u79f0\uff1a" + attachmentBizDTO.getName());
                long l = System.currentTimeMillis();
                try {
                    attachmentBizDTO.setStatus(Integer.valueOf(0));
                    flag = attachment.copyFile(attachmentBizDTO, schemeEntity, quoteCodes.get(attachmentBizDTO.getQuotecode()));
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    flag = false;
                }
                if (!flag) {
                    LOGGER.error("\u9644\u4ef6\u590d\u5236\u5931\u8d25: id = " + attachmentBizDTO.getId() + ", quoteCode = " + attachmentBizDTO.getQuotecode());
                } else {
                    LOGGER.info("\u9644\u4ef6\u590d\u5236\u5b8c\u6210\uff0c\u7528\u65f6\uff1a" + (System.currentTimeMillis() - l));
                }
                copyProgressDO.setCur(i + 1);
                this.syncCopyFileProgress(key, copyProgressDO);
                continue block4;
            }
        }
        if (!EnvConfig.getRedisEnable()) {
            try {
                Thread.sleep(60000L);
                CopyFileCache.progress.remove(key);
            }
            catch (InterruptedException e) {
                LOGGER.error(e.getMessage(), e);
                Thread.currentThread().interrupt();
            }
        }
    }

    @Override
    @Transactional(rollbackFor={Exception.class})
    public void copyFileRef(Map<String, String> quoteCodes, AttachmentBizDTO attParam) {
        String quotecode = attParam.getQuotecode();
        AttachmentBizDO query = new AttachmentBizDO();
        query.setQuotecode(quotecode);
        query.setSuffix(quotecode.split("-")[0]);
        if (!AttachmentBizStorage.hasTable(ShiroUtil.getTenantName(), query.getSuffix())) {
            return;
        }
        R execute = RedisLockUtil.execute(() -> {
            List<AttachmentBizDTO> list = this.bizAttachmentDao.list(query);
            for (AttachmentBizDTO attachmentBizDTO : list) {
                boolean flag;
                if (CopyFileCache.isDelete((String)attachmentBizDTO.getId().toString())) {
                    LOGGER.error("\u6587\u4ef6\u6b63\u5728\u88ab\u5220\u9664" + attachmentBizDTO.getId().toString());
                    throw new RuntimeException("\u6587\u4ef6\u6b63\u5728\u88ab\u5220\u9664");
                }
                LOGGER.info("\u8fdb\u884c\u9644\u4ef6\u590d\u5236\uff0c\u7c7b\u578b\uff1a" + attachmentBizDTO.getModename() + ", \u9644\u4ef6\u540d\u79f0\uff1a" + attachmentBizDTO.getName());
                try {
                    if (attParam.getStatus() != null) {
                        attachmentBizDTO.setStatus(attParam.getStatus());
                    } else {
                        attachmentBizDTO.setStatus(Integer.valueOf(0));
                    }
                    if (!attParam.isDeepCopy()) {
                        attachmentBizDTO.setCreatetime(new Date());
                        attachmentBizDTO.setCreateuser(ShiroUtil.getUser().getId());
                    }
                    flag = this.vaAttachmentBizRefService.refCopyFile(attachmentBizDTO, (String)quoteCodes.get(attachmentBizDTO.getQuotecode()));
                }
                catch (Exception e) {
                    LOGGER.error(e.getMessage(), e);
                    throw new RuntimeException("\u6587\u4ef6\u590d\u5236\u5931\u8d25");
                }
                if (flag) continue;
                LOGGER.error("\u9644\u4ef6\u590d\u5236\u5931\u8d25: id = " + attachmentBizDTO.getId() + ", quoteCode = " + attachmentBizDTO.getQuotecode());
                throw new RuntimeException("\u6587\u4ef6\u590d\u5236\u5931\u8d25");
            }
        }, (String)(ATT_COPY_KEY_PREFIX + quotecode), (long)10000L, (boolean)true);
        if (execute.getCode() != 0) {
            LOGGER.error(execute.getMsg());
            throw new RuntimeException(execute.getMsg());
        }
    }

    @Override
    public void syncCopyFileProgress(String key, CopyProgressDO copyProgressDO) {
        if (EnvConfig.getRedisEnable()) {
            this.redisTemplate.opsForValue().set((Object)key, (Object)JSONUtil.toJSONString((Object)copyProgressDO), 1L, TimeUnit.MINUTES);
        } else {
            CopyFileCache.progress.put(key, copyProgressDO);
        }
    }
}

