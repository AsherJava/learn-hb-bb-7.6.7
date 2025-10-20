/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.jiuqi.va.attachment.domain.AttachmentBizDO
 *  com.jiuqi.va.attachment.domain.AttachmentBizDTO
 *  com.jiuqi.va.attachment.domain.AttachmentBizRefDO
 *  org.springframework.dao.DataIntegrityViolationException
 */
package com.jiuqi.va.attachment.service.impl;

import com.jiuqi.va.attachment.dao.VaAttachmentBizDao;
import com.jiuqi.va.attachment.dao.VaAttachmentBizRefDao;
import com.jiuqi.va.attachment.domain.AttachmentBizDO;
import com.jiuqi.va.attachment.domain.AttachmentBizDTO;
import com.jiuqi.va.attachment.domain.AttachmentBizRefDO;
import com.jiuqi.va.attachment.service.VaAttachmentBizRefService;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class VaAttachmentBizRefServiceImpl
implements VaAttachmentBizRefService {
    private static final Logger LOGGER = LoggerFactory.getLogger(VaAttachmentBizRefServiceImpl.class);
    @Autowired
    private VaAttachmentBizRefDao vaAttachmentBizRefDao;
    @Autowired
    private VaAttachmentBizDao bizAttachmentDao;

    @Override
    public boolean refCopyFile(AttachmentBizDTO param, String newQuoteCode) {
        boolean b;
        UUID fileId = UUID.randomUUID();
        if (param.getSourceid() == null) {
            param.setSourceid(param.getId());
        }
        param.setId(fileId);
        param.setQuotecode(newQuoteCode);
        param.setSuffix(newQuoteCode.split("-")[0]);
        boolean bl = b = this.bizAttachmentDao.add((AttachmentBizDO)param) > 0;
        if (b) {
            AttachmentBizRefDO attachmentBizRefDO = new AttachmentBizRefDO();
            attachmentBizRefDO.setId(param.getSourceid());
            attachmentBizRefDO.setRefcount(Integer.valueOf(1));
            try {
                int insert = this.vaAttachmentBizRefDao.insert(attachmentBizRefDO);
                if (insert >= 1) {
                    return true;
                }
                LOGGER.error("\u9644\u4ef6\u5f15\u7528\u5173\u7cfb\u8868\u63d2\u5165\u5931\u8d25");
                return false;
            }
            catch (DataIntegrityViolationException e) {
                int count = this.vaAttachmentBizRefDao.addRefCount(attachmentBizRefDO);
                if (count >= 1) {
                    return true;
                }
                LOGGER.error("\u9644\u4ef6\u5f15\u7528\u5173\u7cfb\u8868\u66f4\u65b0\u5931\u8d25");
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean refMoveFile(UUID sourceId) {
        if (sourceId == null) {
            return true;
        }
        AttachmentBizRefDO attachmentBizRefDO = new AttachmentBizRefDO();
        attachmentBizRefDO.setId(sourceId);
        List count = this.vaAttachmentBizRefDao.select(attachmentBizRefDO);
        if (CollectionUtils.isEmpty(count)) {
            return true;
        }
        AttachmentBizRefDO attachmentBizRefDO1 = (AttachmentBizRefDO)count.get(0);
        if (attachmentBizRefDO1.getRefcount() == 1) {
            int delete = this.vaAttachmentBizRefDao.delete(attachmentBizRefDO);
            if (delete >= 1) {
                return true;
            }
            LOGGER.error("\u9644\u4ef6\u5f15\u7528\u5173\u7cfb\u8868\u5220\u9664\u5931\u8d25");
            return false;
        }
        int count1 = this.vaAttachmentBizRefDao.deleteRefCount(attachmentBizRefDO);
        if (count1 >= 1) {
            return true;
        }
        LOGGER.error("\u9644\u4ef6\u5f15\u7528\u5173\u7cfb\u8868\u66f4\u65b0\u5931\u8d25");
        return false;
    }

    @Override
    public boolean checkRef(UUID uuid) {
        if (uuid == null) {
            return false;
        }
        AttachmentBizRefDO attachmentBizRefDO = new AttachmentBizRefDO();
        attachmentBizRefDO.setId(uuid);
        List select = this.vaAttachmentBizRefDao.select(attachmentBizRefDO);
        if (CollectionUtils.isEmpty(select)) {
            return false;
        }
        return ((AttachmentBizRefDO)select.get(0)).getRefcount() >= 1;
    }
}

